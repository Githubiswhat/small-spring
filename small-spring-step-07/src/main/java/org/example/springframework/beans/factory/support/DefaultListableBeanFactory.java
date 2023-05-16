package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.example.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionHashMap = new HashMap<>();

    @Override
    public <T> Map<String, T> getBeansByType(Class<T> type) {
        HashMap<String, T> result = new HashMap<>();
        beanDefinitionHashMap.forEach((beanName, beanDefinition) -> {
            if (type.isAssignableFrom(beanDefinition.getBeanClass())) {
                result.put(beanName, (T) getBean(beanName));
            }
        });
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionHashMap.keySet().toArray(new String[0]);
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
    public void preInstantiateSingletonBean() {
        beanDefinitionHashMap.keySet().forEach(this::getBean);
    }


}







