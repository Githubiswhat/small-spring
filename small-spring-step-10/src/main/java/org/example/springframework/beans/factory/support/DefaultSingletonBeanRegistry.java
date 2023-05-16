package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.DisposableBean;
import org.example.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected static final Object NULL_OBJECT = new Object();
    HashMap<String, Object> singletonBeanFactory = new HashMap<>();
    HashMap<String, DisposableBean> disposableBeanHashMap = new HashMap<>();

    @Override
    public void registerSingleton(String name, Object bean) {
        singletonBeanFactory.put(name, bean);
    }

    @Override
    public Object getSingleton(String name) {
        return singletonBeanFactory.get(name);
    }

    public void registerDisposable(String name, DisposableBean bean) {
        disposableBeanHashMap.put(name, bean);
    }

    public void destroySingletons() {
        Set<String> keySet = disposableBeanHashMap.keySet();
        Object[] disposableBeans = keySet.toArray();

        for (Object object : disposableBeans) {
            DisposableBean disposeBean = disposableBeanHashMap.remove(object);
            try {
                disposeBean.destroy();
            } catch (Exception e) {
                throw new BeansException("destroy bean failed");
            }
        }
    }
}
