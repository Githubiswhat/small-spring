package org.example.springframework.beans.factory.support;

import org.example.springframework.core.io.DefaultResourceLoader;
import org.example.springframework.core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader == null ? new DefaultResourceLoader() : resourceLoader;
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, null);
    }

    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

}
