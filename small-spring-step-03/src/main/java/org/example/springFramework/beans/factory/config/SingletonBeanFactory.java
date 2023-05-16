package org.example.springFramework.beans.factory.config;

public interface SingletonBeanFactory {
    Object getSingletonBean(String name);

    void addSingletonBean(String name, Object bean);

}
