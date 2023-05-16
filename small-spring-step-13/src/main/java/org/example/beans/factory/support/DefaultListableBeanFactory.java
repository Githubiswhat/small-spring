package org.example.beans.factory.support;

import org.example.beans.factory.config.BeanDefinition;
import org.example.beans.factory.config.BeanDefinitionRegistry;

import java.util.HashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    private HashMap<String, BeanDefinition> beanDefinitionHashMap = new HashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return beanDefinitionHashMap.get(name);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionHashMap.put(name, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return beanDefinitionHashMap.containsKey(name);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionHashMap.keySet().toArray(new String[0]);
    }

    @Override
    public void destroySingleton() throws Exception {
        //todo
    }
}
