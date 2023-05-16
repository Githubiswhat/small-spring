package org.example.springframework.beans.factory.support;

import org.example.springframework.core.io.DefaultResourceLoader;
import org.example.springframework.core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private ResourceLoader resourceLoader;

    private BeanDefinitionRegistry beanDefinitionRegistry;


    public AbstractBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this(null, beanDefinitionRegistry);
    }

    public AbstractBeanDefinitionReader(ResourceLoader resourceLoader, BeanDefinitionRegistry beanDefinitionRegistry) {
        this.resourceLoader = resourceLoader == null ? new DefaultResourceLoader() : resourceLoader;
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return beanDefinitionRegistry;
    }

    public void setBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }
}
