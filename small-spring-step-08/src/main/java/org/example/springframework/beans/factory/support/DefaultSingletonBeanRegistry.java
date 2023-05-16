package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.DisposableBean;
import org.example.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private HashMap<String, Object> singletonBeanFactory = new HashMap<>();

    private Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    @Override
    public void registerSingletonBean(String name, Object bean) {
        singletonBeanFactory.put(name, bean);
    }

    @Override
    public Object getSingletonBean(String name) {
        return singletonBeanFactory.get(name);
    }

    protected void addDisposableBean(String name, DisposableBean bean) {
        disposableBeanMap.put(name, bean);
    }

    @Override
    public void destroySingletons() {
        Set<String> keySet = disposableBeanMap.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = 0; i < disposableBeanNames.length; i++) {
            DisposableBean disposingBean = disposableBeanMap.remove(disposableBeanNames[i]);
            try {
                disposingBean.destroy();
            } catch (Exception e) {
                throw new BeansException("destroy bean " + disposableBeanNames[i].toString() + " failed", e);
            }
        }
    }
}
