package org.example.springframework.beans.factory.config;

public interface SingletonBeanRegistry {

    void registerSingletonBean(String name, Object bean);

    Object getSingletonBean(String name);


}
