package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.BeanException;
import org.example.springFramework.beans.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    public Object createBean(String name, BeanDefinition beanDefinition) {

        Object bean;
        try {
            bean = beanDefinition.getBean().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new BeanException(e.getMessage());
        }
        addSingleton(name, bean);

        return bean;
    }
}
