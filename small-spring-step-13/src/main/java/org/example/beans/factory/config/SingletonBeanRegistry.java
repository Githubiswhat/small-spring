package org.example.beans.factory.config;

public interface SingletonBeanRegistry {

    void registerSingleton(String name, Object bean);

    Object getSingleton(String name);


}
