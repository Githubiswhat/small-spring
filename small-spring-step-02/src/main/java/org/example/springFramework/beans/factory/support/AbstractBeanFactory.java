package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.factory.BeanFactory;
import org.example.springFramework.beans.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }

    public abstract Object createBean(String name, BeanDefinition beanDefinition);

    public abstract BeanDefinition getBeanDefinition(String name);
}
