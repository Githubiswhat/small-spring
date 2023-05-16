package org.example.springframework.beans.factory;

public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessorBeforeInitialize(Object bean, String beanName);

    Object applyBeanPostProcessorAfterInitialize(Object bean, String beanName);


}
