package org.example.springframework.beans.factory.support;

import org.example.springframework.core.io.Resource;

import java.io.IOException;

public interface BeanDefinitionReader {

    void loadBeanDefinition(String location);

    void loadBeanDefinition(String... locations);

    void loadBeanDefinition(Resource resource) throws IOException;

    void loadBeanDefinition(Resource... resources);

}
