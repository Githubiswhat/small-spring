package org.example.springframework.beans.factory;

public interface FactoryBean {

    Object getObject();

    Class<?> getClassType();

    boolean isSingleton();

}
