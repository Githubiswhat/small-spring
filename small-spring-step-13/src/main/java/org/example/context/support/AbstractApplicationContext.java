package org.example.context.support;

import org.example.beans.factory.ConfigurableListableBeanFactory;
import org.example.context.ConfigurableApplicationContext;
import org.example.core.io.DefatultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefatultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws Exception {
        refreshBeanFactory();

        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        invokeBeanFactoryPostProcessor(beanFactory);

        registerBeanPostProcessor(beanFactory);

        initApplicationEventMulticaster();

        registerApplicationListener();

        beanFactory.preInstantiateSingletons();

        finishRefresh();
    }

    private void registerApplicationListener() {

    }

    private void initApplicationEventMulticaster() {
    }

    private void finishRefresh() {
    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();


    protected abstract void refreshBeanFactory();

    private void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {

    }

    private void invokeBeanFactoryPostProcessor(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    public void close() {
        getBeanFactory().destroySingletons();
    }

    @Override
    public void registerShutdownHooker() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object[] args) {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<?> requestedType) {
        return getBeanFactory().getBean(name, requestedType);
    }

    @Override
    public <T> Map<String, T> getBeansByType(Class<T> type) {
        return getBeanFactory().getBeansByType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
}
