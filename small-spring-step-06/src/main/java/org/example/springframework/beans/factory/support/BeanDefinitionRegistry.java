package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String name);

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    boolean containBeanDefinition(String name);

    String[] getBeanDefinitionNames();

}
