package org.example.springFramework.beans.factory;

import org.example.springFramework.beans.BeansException;

public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object[] args) throws BeansException;
}
