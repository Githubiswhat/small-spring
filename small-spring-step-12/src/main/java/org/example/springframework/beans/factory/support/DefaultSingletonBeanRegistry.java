package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.DisposableBean;
import org.example.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected static final Object NULL_OBJECT = new Object();
    private HashMap<String, Object> singletonBeanFactory = new HashMap<>();
    private HashMap<String, DisposableBean> disposableBeanHashMap = new HashMap<>();

    @Override
    public void registerSingletonBean(String name, Object bean) {
        singletonBeanFactory.put(name, bean);
    }

    public void registerDisposableBean(String name, DisposableBean bean) {
        disposableBeanHashMap.put(name, bean);
    }

    @Override
    public Object getSingletonBean(String name) {
        return singletonBeanFactory.get(name);
    }

    public void destroySingletons() {
        Set<String> keySet = disposableBeanHashMap.keySet();
        Object[] disposableBeans = keySet.toArray();

        for (Object object : disposableBeans) {
            DisposableBean disposableBean = disposableBeanHashMap.remove(object);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("destroy bean failed", e);
            }
        }
    }
}
