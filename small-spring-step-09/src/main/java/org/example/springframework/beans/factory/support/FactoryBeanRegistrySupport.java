package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.FactoryBean;

import java.util.concurrent.ConcurrentHashMap;

public class FactoryBeanRegistrySupport extends DefaultSingletonBeanFactory {

    ConcurrentHashMap<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    Object getCachedObjectFromFactoryBean(String name) {
        Object cachedObject = this.factoryBeanObjectCache.get(name);
        return cachedObject == NULL_OBJECT ? null : cachedObject;
    }

    Object getObjectFromFactoryBean(FactoryBean factoryBean, String name) {
        if (factoryBean.isSingleton()) {
            Object object = factoryBeanObjectCache.get(name);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factoryBean);
                factoryBeanObjectCache.put(name, (object == null ? NULL_OBJECT : object));
            }
            return (object == NULL_OBJECT ? null : object);
        } else {
            return doGetObjectFromFactoryBean(factoryBean);
        }
    }

    private Object doGetObjectFromFactoryBean(FactoryBean factoryBean) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
