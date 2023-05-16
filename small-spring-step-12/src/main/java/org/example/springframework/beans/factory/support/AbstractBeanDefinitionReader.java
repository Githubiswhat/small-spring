package org.example.springframework.beans.factory.support;

import org.example.springframework.core.io.DefatultResourceLoader;
import org.example.springframework.core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private ResourceLoader resourceLoader;

    private BeanDefinitionRegistry registry;


    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(null, registry);
    }

    public AbstractBeanDefinitionReader(ResourceLoader resourceLoader, BeanDefinitionRegistry registry) {
        this.resourceLoader = resourceLoader == null ? new DefatultResourceLoader() : resourceLoader;
        this.registry = registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }
}
