package org.example.springframework.context.support;

import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.context.ApplicationContext;
import org.example.springframework.context.ApplicationContextAware;

public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessorBeanBeforeInitialize(Object bean, String name) {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessorBeanAfterInitialize(Object bean, String name) {
        return bean;
    }
}
