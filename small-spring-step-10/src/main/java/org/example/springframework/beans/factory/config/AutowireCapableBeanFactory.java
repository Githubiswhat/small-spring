package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessorBeforeInitializers(Object bean, String beanName);

    Object applyBeanPostProcessorAfterInitializers(Object bean, String beanName);

}
