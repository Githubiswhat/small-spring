package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.factory.BeanFactory;
import org.example.springFramework.beans.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanFactory implements BeanFactory {

    @Override
    public Object getBean(String name) {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) {
        return doGetBean(name, args);
    }

    private Object doGetBean(String name, Object[] args) {
        Object bean = getSingletonBean(name);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition, args);
    }

    public abstract Object createBean(String name, BeanDefinition beanDefinition, Object[] args);

    public abstract BeanDefinition getBeanDefinition(String name);
}
