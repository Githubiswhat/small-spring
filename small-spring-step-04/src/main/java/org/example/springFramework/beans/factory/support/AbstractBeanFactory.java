package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.BeansException;
import org.example.springFramework.beans.factory.BeanFactory;
import org.example.springFramework.beans.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonRegistry implements BeanFactory {
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    public Object getBean(String name, Object[] args) throws BeansException {
        return doGetBean(name, args);
    }

    private Object doGetBean(String name, Object[] args) {
        Object bean = getSingletonBean(name);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return creteBean(beanDefinition, name, args);
    }

    public abstract Object creteBean(BeanDefinition beanDefinition, String name, Object[] args);

    public abstract BeanDefinition getBeanDefinition(String name);

}
