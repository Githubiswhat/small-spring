package org.example.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.PropertyValue;
import org.example.springframework.beans.PropertyValues;
import org.example.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubClassInstantiationStrategy();

    @Override
    public Object createBean(BeanDefinition beanDefinition, String name, Object[] args) {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, name, args);
            applyBeanProperties(beanDefinition, bean);
            bean = initializeBean(beanDefinition, name, bean);
        } catch (Exception e) {
            throw new BeansException("bean instantiate failed", e);
        }
        return bean;
    }

    private Object initializeBean(BeanDefinition beanDefinition, String name, Object bean) {
        Object wrapperBean = applyBeanPostProcessorBeforeInitial(bean, name);
        invokeInitialMethod(wrapperBean, name, beanDefinition);
        wrapperBean = applyBeanPostProcessorAfterInitial(wrapperBean, name);
        return wrapperBean;
    }

    private void invokeInitialMethod(Object wrapperBean, String name, BeanDefinition beanDefinition) {

    }


    private void applyBeanProperties(BeanDefinition beanDefinition, Object bean) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                String className = ((BeanReference) value).getClassName();
                value = getBean(className);
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

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitial(Object bean, String beanName) {
        Object existBean = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            Object result = beanPostProcessor.postProcessBeforeInitial(bean, beanName);
            if (result == null) return existBean;
            existBean = result;
        }
        return existBean;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitial(Object bean, String beanName) {
        Object existBean = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            Object result = beanPostProcessor.postProcessAfterInitial(bean, beanName);
            if (result == null) return existBean;
            existBean = result;
        }
        return existBean;
    }
}
