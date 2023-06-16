package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.PropertyValues;


public class BeanDefinition {

    private static final  String SCORE_SINGLETON = ConfigurableBeanFactory.SCORE_SINGLETON;

    private static final String SCORE_PROTOTYPE = ConfigurableBeanFactory.SCORE_PROTOTYPE;


    private Class beanClass;

    private PropertyValues propertyValues;

    private String scope = SCORE_SINGLETON;

    private String initMethod;

    private String destroyMethod;

    private boolean isSingleton = false;

    private boolean isProtoType = true;

    public BeanDefinition(Class beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues == null ? new PropertyValues() : propertyValues;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.isSingleton = SCORE_SINGLETON.equals(scope);
        this.isProtoType = SCORE_PROTOTYPE.equals(scope);
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    public String getDestroyMethod() {
        return destroyMethod;
    }

    public void setDestroyMethod(String destroyMethod) {
        this.destroyMethod = destroyMethod;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public boolean isProtoType() {
        return isProtoType;
    }

    public String getScope() {
        return scope;
    }
}

