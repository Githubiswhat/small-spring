package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);

    String[] getBeanDefinitionNames();

}
