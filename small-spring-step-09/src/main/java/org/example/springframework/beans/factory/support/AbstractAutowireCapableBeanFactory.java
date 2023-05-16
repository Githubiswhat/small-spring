package org.example.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.example.springframework.beans.BeanException;
import org.example.springframework.beans.PropertyValue;
import org.example.springframework.beans.PropertyValues;
import org.example.springframework.beans.factory.*;
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
            applyProperties(bean, beanDefinition);
            bean = initializeBean(bean, name, beanDefinition);
        } catch (Exception e) {
            throw new BeanException("instantiate bean failed " + name);
        }

        registerDisposableBanIsNecessary(bean, name, beanDefinition);
        if (beanDefinition.isSingleton()) {
            registerSingleton(name, bean);
        }

        return bean;
    }

    private void registerDisposableBanIsNecessary(Object bean, String name, BeanDefinition beanDefinition) {
        if (beanDefinition.isPrototype()) {
            return;
        }

        if (bean instanceof DisposableBan || StrUtil.isNotEmpty(beanDefinition.getDestroyMethod())) {
            addDisposableBan(name, new DisposeBeanAdapter(bean, name, beanDefinition));
        }
    }

    private Object initializeBean(Object bean, String name, BeanDefinition beanDefinition) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(name);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getDefaultClassLoader());
            }
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
        }

        Object wrapperBean = applyBeanPostProcessorBeforeInitiate(bean, name);
        try {
            invokeInitiate(bean, name, beanDefinition);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        wrapperBean = applyBeanPostProcessorAfterInitiate(bean, name);
        return wrapperBean;
    }

    private void invokeInitiate(Object bean, String name, BeanDefinition beanDefinition) {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        if (StrUtil.isNotEmpty(beanDefinition.getInitMethod()) && !(bean instanceof InitializingBean)) {
            try {
                Method method = bean.getClass().getMethod(beanDefinition.getInitMethod());
                method.invoke(bean);
            } catch (Exception e) {
                throw new BeanException("no such method", e);
            }
        }
    }

    private void applyProperties(Object bean, BeanDefinition beanDefinition) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }
            BeanUtil.setFieldValue(bean, name, value);
        }
    }


    private Object createBeanInstance(BeanDefinition beanDefinition, String name, Object[] args) {
        Class beanClass = beanDefinition.getBeanClass();
        Constructor constructor = null;
        Constructor[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor ctor : declaredConstructors) {
            if (args != null && ctor.getParameterTypes().length == args.length) {
                constructor = ctor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, name, constructor, args);
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitiate(Object existBean, String beanName) {
        Object result = existBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {

            Object current = beanPostProcessor.postProcessorBeanBeforeInitialize(existBean, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitiate(Object existBean, String beanName) {
        Object result = existBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessorBeanAfterInitialize(existBean, beanName);
            if (current == null) {
                return result;
            }
            result = current;
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
