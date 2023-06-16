package org.example.springframework.beans.factory.config;

import cn.hutool.core.bean.BeanException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException;

}
