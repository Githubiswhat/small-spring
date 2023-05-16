package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.BeansException;
import org.example.springFramework.beans.factory.config.BeanDefinition;

import java.util.HashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    HashMap<String, BeanDefinition> beanDefinitionHashMap = new HashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        BeanDefinition beanDefinition = beanDefinitionHashMap.get(name);
        if (beanDefinition == null) {
            throw new BeansException("get bean definition failed", e);
        }
        return beanDefinition;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionHashMap.put(name, beanDefinition);
    }
}
