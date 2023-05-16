package org.example.springframework.beans.factory.config;

public interface SingletonBeanRegistry {

    Object getSingletonBean(String name);

    void addSingletonBean(String name, Object bean);
}
