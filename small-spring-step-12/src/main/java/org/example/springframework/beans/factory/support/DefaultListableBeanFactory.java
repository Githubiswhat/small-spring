package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.example.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {


    HashMap<String, BeanDefinition> beanDefinitionHashMap = new HashMap<>();

    @Override
    public void preInstantiateSingleton() {
        beanDefinitionHashMap.keySet().forEach(this::getBean);
    }

    @Override
    public <T> Map<String, T> getBeansByType(Class<?> type) {
        HashMap<String, T> result = new HashMap<>();
        beanDefinitionHashMap.forEach((beanName, beanDefinition) -> {
            if (type.isAssignableFrom(beanDefinition.getBeanClass())) {
                result.put(beanName, (T) getBean(beanName));
            }
        });
        return result;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition definition) {
        beanDefinitionHashMap.put(name, definition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return beanDefinitionHashMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return beanDefinitionHashMap.containsKey(name);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionHashMap.keySet().toArray(new String[0]);
    }
}
