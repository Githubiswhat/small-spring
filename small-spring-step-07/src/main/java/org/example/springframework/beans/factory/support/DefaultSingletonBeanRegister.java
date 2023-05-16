package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSingletonBeanRegister implements SingletonBeanRegistry {

    private Map<String, Object> singletonBeanFactory = new HashMap<>();

    private Map<String, DisposableBean> disposableBeanHashMap = new HashMap<>();

    @Override
    public void registerSingleton(String name, Object bean) {
        singletonBeanFactory.put(name, bean);
    }

    @Override
    public Object getSingleton(String name) {
        return singletonBeanFactory.get(name);
    }

    public void registerDisposableBean(String name, DisposableBean disposableBean) {
        disposableBeanHashMap.put(name, disposableBean);
    }


    public void destroySingletons() {
        Set<String> keySets = disposableBeanHashMap.keySet();
        Object[] disposableBeanNames = keySets.toArray();
        for (int i = 0; i < disposableBeanNames.length; i++) {
            DisposableBean disposedBean = disposableBeanHashMap.remove(disposableBeanNames[i]);
            try {
                disposedBean.destroy();
            } catch (Exception e) {
                throw new BeansException("bean destroy failed", e);
            }
        }
    }

}
