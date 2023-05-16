package org.example.springframework.beans.factory;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {

    <T> Map<String, T> getBeanByType(Class<T> type);

    String[] getBeanDefinitionNames();
}
