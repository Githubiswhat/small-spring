package org.example.springframework.context.support;

import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.context.ApplicationContext;
import org.example.springframework.context.ApplicationContextAware;

public class ApplicationContextAwarePostProcessor implements BeanPostProcessor {

    private ApplicationContext applicationContext;

    public ApplicationContextAwarePostProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
