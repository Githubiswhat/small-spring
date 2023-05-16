package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.factory.config.BeanDefinition;

import java.util.HashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    HashMap<String, BeanDefinition> beanDefinitionHashMap = new HashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return beanDefinitionHashMap.get(name);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionHashMap.put(name, beanDefinition);
    }

}
