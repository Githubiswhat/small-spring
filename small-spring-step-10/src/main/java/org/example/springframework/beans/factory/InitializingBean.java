package org.example.springframework.beans.factory;

public interface InitializingBean {

    void initialize(Object bean, String beanName) throws Exception;
}
