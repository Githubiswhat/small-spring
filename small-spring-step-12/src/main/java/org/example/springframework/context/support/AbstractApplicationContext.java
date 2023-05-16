package org.example.springframework.context.support;

import org.example.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.example.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.context.ApplicationContextAware;
import org.example.springframework.context.ApplicationListener;
import org.example.springframework.context.ConfigurableApplicationContext;
import org.example.springframework.context.event.ApplicationEventMultiCaster;
import org.example.springframework.context.event.ContextRefreshedEvent;
import org.example.springframework.context.event.SimpleApplicationEventMultiCaster;
import org.example.springframework.core.io.DefatultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefatultResourceLoader implements ConfigurableApplicationContext {

    private final static String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMultiCaster";

    private ApplicationEventMultiCaster applicationEventMultiCaster;

    @Override
    public void refresh() throws Exception {
        refreshBeanFactory();

        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        beanFactory.addBeanPostProcessor(new ApplicationContextAwareBeanPostProcessor(this));

        invokeBeanFactoryPostProcessors();

        registerBeanPostProcessors();

        initApplicationListenerEventMulticaster();

        registerListener();

        beanFactory.preInstantiateSingleton();

        finishRefresh();
    }

    private void registerListener() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        Map<String, ApplicationListener> applicationListeners = beanFactory.getBeansByType(ApplicationListener.class);
        for (ApplicationListener applicationListener : applicationListeners.values()) {
            applicationEventMultiCaster.addApplicationEventListener(applicationListener);
        }
    }

    private void finishRefresh() {
        applicationEventMultiCaster.multicaster(new ContextRefreshedEvent(this));
    }

    private void initApplicationListenerEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMultiCaster = new SimpleApplicationEventMultiCaster(this);
        beanFactory.registerSingletonBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMultiCaster);
    }

    private void registerBeanPostProcessors() {
        Map<String, BeanPostProcessor> beanPostProcessors = getBeansByType(BeanPostProcessor.class);
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    private void invokeBeanFactoryPostProcessors() {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessors = getBeansByType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(getBeanFactory());
        }
    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    protected abstract void refreshBeanFactory();

    @Override
    public void registerShutdownHook() {
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
    public <T> T getBean(String name, Class<T> requestedType) {
        return getBeanFactory().getBean(name, requestedType);
    }

    @Override
    public <T> Map<String, T> getBeansByType(Class<?> type) {
        return getBeanFactory().getBeansByType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void close() {
        getBeanFactory().destroySingletons();
    }
}
