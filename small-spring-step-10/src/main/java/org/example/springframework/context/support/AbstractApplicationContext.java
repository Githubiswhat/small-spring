package org.example.springframework.context.support;

import org.example.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.example.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.context.ApplicationEvent;
import org.example.springframework.context.ApplicationListener;
import org.example.springframework.context.ConfigurableApplicationContext;
import org.example.springframework.context.event.ApplicationEventMultiCaster;
import org.example.springframework.context.event.ContextClosedEvent;
import org.example.springframework.context.event.ContextRefreshedEvent;
import org.example.springframework.context.event.SimpleApplicationEventMultiCaster;
import org.example.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    private String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMultiCaster";

    private ApplicationEventMultiCaster applicationEventMultiCaster;

    @Override
    public void refresh() throws Exception {

        refreshBeanFactory();

        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        beanFactory.addBeanPostProcessor(new ApplicationContextAwarePostProcessor(this));

        invokeBeanFactoryPostProcessor(beanFactory);

        registerBeanPostProcessor(beanFactory);

        initApplicationEventMultiCaster();

        registerApplicationEventListener();

        beanFactory.preInstantiateSingleton();

        finishRefresh();
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    private void registerApplicationEventListener() {
        Map<String, ApplicationListener> applicationListeners = getBeansByType(ApplicationListener.class);
        for (ApplicationListener applicationListener : applicationListeners.values()) {
            applicationEventMultiCaster.addListener(applicationListener);
        }
    }

    private void initApplicationEventMultiCaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMultiCaster = new SimpleApplicationEventMultiCaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMultiCaster);
    }

    private void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessors = getBeansByType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    private void invokeBeanFactoryPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessors = getBeansByType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    protected abstract void refreshBeanFactory();

    @Override
    public void close() {

        publishEvent(new ContextClosedEvent(this));

        getBeanFactory().destroySingletons();
    }

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
    public <T> T getBean(String name, Class<T> requiredType) {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansByType(Class<T> type) {
        return getBeanFactory().getBeansByType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMultiCaster.multiCasterEvent(event);
    }

}
