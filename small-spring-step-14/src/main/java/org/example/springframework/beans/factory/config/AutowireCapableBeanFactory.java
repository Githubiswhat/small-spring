package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    Object applyBeanPostProcessAfterInitialization(Object existingBean, String beanName) throws BeansException;

}
