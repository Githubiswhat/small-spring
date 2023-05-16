package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;

public class DefaultSingletonRegistry implements SingletonBeanRegistry {

    HashMap<String, Object> singletonBeanFactory = new HashMap<String, Object>();

    public Object getSingletonBean(String name) {
        return singletonBeanFactory.get(name);
    }

    public void addSingletonBean(String name, Object bean) {
        singletonBeanFactory.put(name, bean);
    }
}
