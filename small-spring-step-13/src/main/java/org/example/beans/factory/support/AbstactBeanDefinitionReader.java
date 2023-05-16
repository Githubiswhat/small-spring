package org.example.beans.factory.support;

import org.example.beans.factory.config.BeanDefinitionRegistry;
import org.example.core.io.DefatultResourceLoader;
import org.example.core.io.ResourceLoader;

import java.util.PriorityQueue;

public abstract class AbstactBeanDefinitionReader implements BeanDefinitionReader{

    private BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    public AbstactBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader == null ? new DefatultResourceLoader() : resourceLoader;
    }

    public AbstactBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, null);
    }

    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
