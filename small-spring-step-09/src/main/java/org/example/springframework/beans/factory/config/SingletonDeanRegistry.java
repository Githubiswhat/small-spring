package org.example.springframework.beans.factory.config;

public interface SingletonDeanRegistry {

    void registerSingleton(String name, Object bean);

    Object getSingleton(String name);

}
