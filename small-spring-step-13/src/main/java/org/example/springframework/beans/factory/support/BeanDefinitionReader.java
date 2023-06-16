package org.example.springframework.beans.factory.support;

import org.example.springframework.core.io.Resource;

public interface BeanDefinitionReader {

    void loadBeanDefinition(String location);


    void loadBeanDefinition(String... locations);

    void loadBeanDefinition(Resource resource);


    void loadBeanDefinition(Resource... resources);

}
