package org.example.springframework.beans.factory;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {

    <T> Map<String, T> getBeansByType(Class<?> type);

    String[] getBeanDefinitionNames();

}
