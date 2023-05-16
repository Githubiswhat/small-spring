package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.factory.config.SingletonBeanFactory;

import java.util.HashMap;

public class DefaultSingletonBeanFactory implements SingletonBeanFactory {

    HashMap<String, Object> singletonBeanFactory = new HashMap<>();

    @Override
    public Object getSingletonBean(String name) {
        Object bean;
        bean = singletonBeanFactory.get(name);
        return bean;
    }

    @Override
    public void addSingletonBean(String name, Object bean) {
        singletonBeanFactory.put(name, bean);
    }
}
