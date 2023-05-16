package org.example.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.PropertyValue;
import org.example.springframework.beans.PropertyValues;
import org.example.springframework.beans.factory.AutowireCapableBeanFactory;
import org.example.springframework.beans.factory.DisposableBean;
import org.example.springframework.beans.factory.InitializingBean;
import org.example.springframework.beans.factory.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubClassInstantiationStrategy();

    @Override
    protected Object createBean(BeanDefinition beanDefinition, String name, Object[] args) {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, name, args);
            applyProperties(beanDefinition, bean);
            bean = initializeBean(bean, name, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("bean instantiate failed", e);
        }

        registerDisposableBeanIfNecessary(bean, beanDefinition, name);

        registerSingletonBean(name, bean);
        return bean;
    }

    private void registerDisposableBeanIfNecessary(Object bean, BeanDefinition beanDefinition, String name) {
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethod())) {
            addDisposableBean(name, new DisposableBeanAdapter(bean, name, beanDefinition));
        }
    }

    private Object initializeBean(Object bean, String name, BeanDefinition beanDefinition) throws Exception {
        if (bean instanceof Aware) {
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(name);
            }
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getClassLoader());
            }
        }

        Object wrapperBean = applyBeanPostProcessorBeforeInitialize(bean, name);
        try {
            invokeInitializingMethod(wrapperBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("invoke init method failed ", e);
        }
        wrapperBean = applyBeanPostProcessorAfterInitialize(bean, name);
        return wrapperBean;
    }

    private void invokeInitializingMethod(Object wrapperBean, BeanDefinition beanDefinition) throws Exception {
        if (wrapperBean instanceof InitializingBean) {
            ((InitializingBean) wrapperBean).afterPropertiesSet();
        }

        if (StrUtil.isNotEmpty(beanDefinition.getInitMethod()) && !(wrapperBean instanceof InitializingBean)) {
            Method method = beanDefinition.getBeanClass().getMethod(beanDefinition.getInitMethod());
            if (method == null) {
                throw new BeansException("not such init method", e);
            }
            method.invoke(wrapperBean);
        }
    }

    private void applyProperties(BeanDefinition beanDefinition, Object bean) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getClassName());
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
        return getInstantiationStrategy().instantiate(beanDefinition, name, constructor, args);
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialize(Object existBean, String beanName) {
        Object result = existBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object bean = beanPostProcessor.postProcessBeanBeforeInitialize(existBean, beanName);
            if (bean == null) {
                return result;
            }
            result = bean;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialize(Object existBean, String beanName) {
        Object result = existBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object bean = beanPostProcessor.postProcessBeanAfterInitialize(existBean, beanName);
            if (bean == null) {
                return result;
            }
            result = bean;
        }
        return result;
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
