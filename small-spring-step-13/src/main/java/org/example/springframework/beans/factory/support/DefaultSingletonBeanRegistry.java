package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.DisposableBean;
import org.example.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {


    protected static final Object NULL_OBJECT = new Object();

    private Map<String, Object> singletonObjects = new HashMap<>();

    private  final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();


    @Override
    public void registerSingleton(String name, Object bean) {
        singletonObjects.put(name, bean);
    }

    @Override
    public Object getSingleton(String name) {
        return singletonObjects.get(name);
    }

    protected void registerDisposableBean(String  beanName, DisposableBean disposableBean) {
        disposableBeans.put(beanName, disposableBean);
    }

    public void destroySingletons(){
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0 ; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBen = disposableBeans.remove(beanName);
            try {
                disposableBen.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
