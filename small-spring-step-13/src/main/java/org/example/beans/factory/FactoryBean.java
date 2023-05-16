package org.example.beans.factory;

public interface FactoryBean {

    Object getObject();

    Class<?> getClassType();

    boolean isSingleton();

}
