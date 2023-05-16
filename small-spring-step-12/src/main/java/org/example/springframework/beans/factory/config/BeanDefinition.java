package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.PropertyValues;

public class BeanDefinition {

    private final String SCORE_SINGLETON = ConfigurableBeanFactory.SCORE_SINGLETON;

    private final String SCORE_PROTOTYPE = ConfigurableBeanFactory.SCORE_PROTOTYPE;

    private Class beanClass;

    private PropertyValues propertyValues;

    private String initMethod;

    private String destroyMethod;

    private String score = SCORE_SINGLETON;

    private boolean isSingleton = true;

    private boolean isPrototype = false;


    public BeanDefinition(Class beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues == null ? new PropertyValues() : propertyValues;
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

    public boolean isSingleton() {
        return isSingleton;
    }

    public boolean isPrototype() {
        return isPrototype;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
        this.isPrototype = SCORE_SINGLETON.equals(score);
        this.isSingleton = SCORE_PROTOTYPE.equals(score);
    }
}
