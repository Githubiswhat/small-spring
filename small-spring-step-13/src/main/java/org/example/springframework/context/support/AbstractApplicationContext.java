package org.example.springframework.context.support;

import org.example.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.example.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.context.ApplicationListener;
import org.example.springframework.context.ConfigurableApplicationContext;
import org.example.springframework.context.event.ApplicationEventMulticaster;
import org.example.springframework.context.event.ContextRefreshedEvent;
import org.example.springframework.context.event.SimpleApplicationEventMulticaster;
import org.example.springframework.core.io.DefatultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefatultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTIPLE_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

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
        Map<String, ApplicationListener> applicationListeners = getBeansByType(ApplicationListener.class);
        for (ApplicationListener listener : applicationListeners.values()) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTIPLE_BEAN_NAME, applicationEventMulticaster);
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    private void publishEvent(ContextRefreshedEvent event) {
       applicationEventMulticaster.multicastEvent(event);
    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();


    protected abstract void refreshBeanFactory();

    private void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansByType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    private void invokeBeanFactoryPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessMap = beanFactory.getBeansByType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor benFactoryPostProcess : beanFactoryPostProcessMap.values()) {
            benFactoryPostProcess.postProcessBeanFactory(beanFactory);
        }
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
