package org.example.springframework.beans.factory;

import com.sun.org.apache.xpath.internal.operations.Bool;

public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();

    Boolean isSingleton();

}


