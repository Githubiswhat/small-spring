package org.example.springframework.beans.factory.config;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    BeanDefinition getBeanDefinition(String name);

    boolean containBeanDefinition(String name);

    String[] getBeanDefinitionNames();

}
