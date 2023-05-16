package org.example.springFramework;

import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {

    ConcurrentHashMap<String, BeanDefinition> beanFactory = new ConcurrentHashMap<String, BeanDefinition>();

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanFactory.put(name, beanDefinition);
    }

    public Object getBean(String name) {
        return beanFactory.get(name).getBean();
    }
}
