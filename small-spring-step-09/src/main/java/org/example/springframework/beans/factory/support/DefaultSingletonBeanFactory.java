package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.BeanException;
import org.example.springframework.beans.factory.DisposableBan;
import org.example.springframework.beans.factory.config.SingletonDeanRegistry;

import java.util.HashMap;

public class DefaultSingletonBeanFactory implements SingletonDeanRegistry {

    public static Object NULL_OBJECT = new Object();
    HashMap<String, Object> singletonObjects = new HashMap<>();
    HashMap<String, DisposableBan> disposableBeanMap = new HashMap<>();

    @Override
    public void registerSingleton(String name, Object bean) {
        singletonObjects.put(name, bean);
    }

    @Override
    public Object getSingleton(String name) {
        return singletonObjects.get(name);
    }

    public void addDisposableBan(String name, DisposableBan disposableBean) {
        disposableBeanMap.put(name, disposableBean);
    }

    public void destroySingletons() {
        disposableBeanMap.forEach((beanName, disposableBan) -> {
            try {
                disposableBan.destroy();
            } catch (Exception e) {
                throw new BeanException("destroy bean failed " + beanName);
            }
        });
    }
}
