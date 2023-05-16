package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.BeansException;
import org.example.springframework.core.io.Resource;

public interface BeanDefinitionReader {

    void loadBeanDefinition(String location) throws BeansException;

    void loadBeanDefinition(String... locations) throws BeansException;

    void loadBeanDefinition(Resource resource) throws BeansException;

    void loadBeanDefinition(Resource... resources) throws BeansException, ClassNotFoundException;

    ;
}
