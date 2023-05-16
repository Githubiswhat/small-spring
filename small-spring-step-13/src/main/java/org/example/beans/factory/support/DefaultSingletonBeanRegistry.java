package org.example.beans.factory.support;

import org.example.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private HashMap<String, Object> singletonObjects = new HashMap<>();
    @Override
    public void registerSingleton(String name, Object bean) {
        singletonObjects.put(name, bean);
    }

    @Override
    public Object getSingleton(String name) {
        return singletonObjects.get(name);
    }
}
