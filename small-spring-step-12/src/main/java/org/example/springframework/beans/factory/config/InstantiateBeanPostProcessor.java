package org.example.springframework.beans.factory.config;

public interface InstantiateBeanPostProcessor extends BeanPostProcessor {
    Object postProcessBeanBeforeInstantiation(Class<?> clazz, String name);

}
