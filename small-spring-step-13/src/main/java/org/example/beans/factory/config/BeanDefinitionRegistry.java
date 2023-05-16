package org.example.beans.factory.config;

public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String name);

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    boolean containsBeanDefinition(String name);

    String[] getBeanDefinitionNames();

}
