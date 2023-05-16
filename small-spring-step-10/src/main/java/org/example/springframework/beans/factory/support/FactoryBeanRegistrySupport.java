package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.FactoryBean;

import java.util.concurrent.ConcurrentHashMap;

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    private ConcurrentHashMap<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getBeanObjectFromCache(String name) {
        Object object = factoryBeanObjectCache.get(name);
        return object == NULL_OBJECT ? null : object;
    }

    protected Object getBeanObjectFromFactoryBean(FactoryBean factoryBean, String name) {
        if (factoryBean.isSingleton()) {
            Object object = factoryBeanObjectCache.get(name);
            if (object == null) {
                object = doGetBeanObjectFromFactoryBean(factoryBean, name);
                factoryBeanObjectCache.put(name, (object == null ? NULL_OBJECT : object));
            }
            return object == NULL_OBJECT ? null : object;
        } else {
            return doGetBeanObjectFromFactoryBean(factoryBean, name);
        }
    }


    private Object doGetBeanObjectFromFactoryBean(FactoryBean factoryBean, String name) {
        try {
            Object object = factoryBean.getObject();
            return object;
        } catch (Exception e) {
            throw new BeansException("get bean from factory bean failed " + name);
        }
    }

}
