package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    HashMap<String, Object> singletonBeanFactory = new HashMap<>();

    @Override
    public Object getSingletonBean(String name) {
        return singletonBeanFactory.get(name);
    }

    @Override
    public void addSingletonBean(String name, Object bean) {
        singletonBeanFactory.put(name, bean);
    }
}
