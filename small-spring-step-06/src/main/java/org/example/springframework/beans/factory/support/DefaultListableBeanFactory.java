package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.example.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

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
    public boolean containBeanDefinition(String name) {
        return beanDefinitionHashMap.containsKey(name);
    }

    @Override
    public <T> Map<String, T> getBeanByType(Class<T> type) {
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
    public void preInstantiateSingletonBean() {
        beanDefinitionHashMap.keySet().forEach(this::getBean);
    }
}
