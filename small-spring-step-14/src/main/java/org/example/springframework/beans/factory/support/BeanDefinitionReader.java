package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.BeansException;
import org.example.springframework.core.io.Resource;
import org.example.springframework.core.io.ResourceLoader;

public interface BeanDefinitionReader {

    void loadBeanDefinition(String location) throws BeansException;

    void loadBeanDefinition(String... locations) throws BeansException;

    void loadBeanDefinition(Resource location) throws BeansException;

    void loadBeanDefinition(Resource... locations) throws BeansException;

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

}
