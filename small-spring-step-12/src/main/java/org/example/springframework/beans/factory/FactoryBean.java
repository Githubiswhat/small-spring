package org.example.springframework.beans.factory;

public interface FactoryBean {

    Object getObject() throws Exception;

    Class<?> getObjectClassType();

    boolean isSingleInstance();

}
