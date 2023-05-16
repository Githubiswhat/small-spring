package org.example.springframework.beans.factory;

import org.example.springframework.beans.BeansException;

public interface BeanFactory {
    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object[] args) throws BeansException;

    <T> Object getBean(String name, Class<T> requiredType) throws BeansException;
}
