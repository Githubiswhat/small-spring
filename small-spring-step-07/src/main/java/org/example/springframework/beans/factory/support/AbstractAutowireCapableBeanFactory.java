package org.example.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.PropertyValue;
import org.example.springframework.beans.PropertyValues;
import org.example.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {


    private InstantiationStrategy instantiationStrategy = new CglibSubClassInstantiationStrategy();

    @Override
    protected Object createBean(BeanDefinition beanDefinition, String name, Object[] args) {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, name, args);
            applyProperties(bean, beanDefinition, name);
            bean = initialBean(bean, beanDefinition, name);
        } catch (Exception e) {
            throw new BeansException("bean initial failed", e);
        }
        addDisposableBeanIfNecessary(bean, name, beanDefinition);
        registerSingleton(name, bean);

        return bean;
    }

    private void addDisposableBeanIfNecessary(Object bean, String name, BeanDefinition beanDefinition) {
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(name, new DisposableAdapter(name, bean, beanDefinition));
        }
    }

    private Object initialBean(Object bean, BeanDefinition beanDefinition, String name) {
        Object wrapperBean = applyPostProcessorBeforeInitialize(bean, name);
        try {
            initialingBean(wrapperBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("invocation of init method of bean failed", e);
        }
        wrapperBean = applyPostProcessorAfterInitialize(wrapperBean, name);
        return wrapperBean;
    }

    private void initialingBean(Object wrapperBean, BeanDefinition beanDefinition) throws Exception {
        if (wrapperBean instanceof InitializingBean) {
            ((InitializingBean) wrapperBean).afterPropertiesSet();
        }

        String initMethod = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethod) && !(wrapperBean instanceof InitializingBean)) {
            Method method = beanDefinition.getBeanClass().getMethod(initMethod);
            if (method == null) {
                throw new BeansException("don't have the init method: " + initMethod);
            }
            method.invoke(wrapperBean);
        }
    }

    private void applyProperties(Object bean, BeanDefinition beanDefinition, String beanName) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getBeanName());
            }
            BeanUtil.setFieldValue(bean, name, value);
        }
    }

    private Object createBeanInstance(BeanDefinition beanDefinition, String name, Object[] args) {
        Class beanClass = beanDefinition.getBeanClass();
        Constructor[] declaredConstructors = beanClass.getDeclaredConstructors();
        Constructor constructor = null;
        for (Constructor ctor : declaredConstructors) {
            if (args != null && ctor.getParameterTypes().length == args.length) {
                constructor = ctor;
                break;
            }
        }
        return getInstantiationStrategy().instantiates(beanDefinition, name, constructor, args);
    }

    @Override
    public Object applyPostProcessorBeforeInitialize(Object bean, String name) {
        Object existBean = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            bean = beanPostProcessor.postProcessBeforeInitialize(bean, name);
            if (bean == null) {
                return existBean;
            }
        }
        return bean;
    }

    @Override
    public Object applyPostProcessorAfterInitialize(Object bean, String name) {
        Object existBean = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            bean = beanPostProcessor.postProcessAfterInitialize(bean, name);
            if (bean == null) {
                return existBean;
            }
        }
        return bean;

    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
