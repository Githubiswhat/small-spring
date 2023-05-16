package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.FactoryBean;

import java.util.HashMap;
import java.util.Map;

public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    private Map<String, Object> factoryBeanObjectCache = new HashMap<>();

    public Object getCacheObjectFromFactoryBean(String name) {
        Object object = factoryBeanObjectCache.get(name);
        return object == NULL_OBJECT ? null : object;
    }


    public Object getObjectFromFactoryBean(String name, FactoryBean factoryBean) {
        if (factoryBean.isSingleInstance()) {
            Object object = getCacheObjectFromFactoryBean(name);
            if (object == null) {
                object = doGetObjectFromFactoryBean(name, factoryBean);
                factoryBeanObjectCache.put(name, (object == null ? NULL_OBJECT : object));
            }
            return object == NULL_OBJECT ? null : object;
        } else {
            return doGetObjectFromFactoryBean(name, factoryBean);
        }
    }

    private Object doGetObjectFromFactoryBean(String name, FactoryBean factoryBean) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
