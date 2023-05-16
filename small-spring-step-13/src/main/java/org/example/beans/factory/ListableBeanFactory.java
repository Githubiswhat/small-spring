package org.example.beans.factory;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory{

    <T> Map<String, T> getBeansByType(Class<T> type);

    String[] getBeanDefinitionNames();

}
