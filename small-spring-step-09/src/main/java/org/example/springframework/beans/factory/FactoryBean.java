package org.example.springframework.beans.factory;

public interface FactoryBean<T> {

    T getObject() throws Exception;

    boolean isSingleton();

    Class<?> getObjectType();

}
