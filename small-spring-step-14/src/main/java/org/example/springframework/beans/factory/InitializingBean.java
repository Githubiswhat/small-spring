package org.example.springframework.beans.factory;

public interface InitializingBean {

    void afterPropertiesSet() throws Exception;

}
