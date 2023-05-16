package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.config.BeanDefinitionRegistry;
import org.example.springframework.core.io.DefaultResourceLoader;
import org.example.springframework.core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private ResourceLoader resourceLoader;

    private BeanDefinitionRegistry registry;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(null, registry);
    }

    public AbstractBeanDefinitionReader(ResourceLoader resourceLoader, BeanDefinitionRegistry registry) {
        this.resourceLoader = resourceLoader == null ? new DefaultResourceLoader() : resourceLoader;
        this.registry = registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }
}
