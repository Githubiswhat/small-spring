package org.example.springframework.context.support;

import org.example.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.example.springframework.context.ConfigurableApplicationContext;
import org.example.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object[] args) {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requestedType) {
        return getBeanFactory().getBean(name, requestedType);
    }

    @Override
    public <T> Map<String, T> getBeanByType(Class<T> type) {
        return getBeanFactory().getBeanByType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void refresh() {
        refreshBeanFactory();

        DefaultListableBeanFactory beanFactory = getBeanFactory();

        invokeBeanFactoryPostProcessor(beanFactory);

        registerBeanPostProcessor(beanFactory);

        beanFactory.preInstantiateSingletonBean();
    }

    private void registerBeanPostProcessor(DefaultListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = getBeanFactory().getBeanByType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(getBeanFactory());
        }
    }

    private void invokeBeanFactoryPostProcessor(DefaultListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = getBeanFactory().getBeanByType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            getBeanFactory().addBeanPostProcessor(beanPostProcessor);
        }
    }

    public abstract void refreshBeanFactory();

    public abstract DefaultListableBeanFactory getBeanFactory();

}
