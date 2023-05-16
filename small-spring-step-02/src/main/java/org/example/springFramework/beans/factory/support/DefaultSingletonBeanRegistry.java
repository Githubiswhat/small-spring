package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    HashMap<String, Object> singletonFactory = new HashMap<>();

    @Override
    public Object getSingleton(String name) {
        return singletonFactory.get(name);
    }

    @Override
    public void addSingleton(String name, Object bean) {
        singletonFactory.put(name, bean);
    }
}
