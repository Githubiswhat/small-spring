package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
