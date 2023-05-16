package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.BeansException;

public interface InitializingBean {
    void afterPropertiesSet() throws BeansException;
}
