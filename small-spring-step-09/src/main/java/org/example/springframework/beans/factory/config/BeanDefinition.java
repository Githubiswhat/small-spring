package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.PropertyValues;
import org.example.springframework.beans.factory.support.ConfigurableBeanFactory;

public class BeanDefinition {

    private String SCORE_SINGLETON = ConfigurableBeanFactory.SCORE_SINGLETON;

    private String SCORE_PROTOTYPE = ConfigurableBeanFactory.SCORE_PROTOTYPE;

    private Class beanClass;

    private PropertyValues propertyValues;

    private String destroyMethod;

    private String initMethod;

    private boolean isSingleton;

    private boolean isPrototype;

    private String score = SCORE_SINGLETON;


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

    public String getDestroyMethod() {
        return destroyMethod;
    }

    public void setDestroyMethod(String destroyMethod) {
        this.destroyMethod = destroyMethod;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
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
        this.isSingleton = SCORE_SINGLETON.equals(score);
        this.isPrototype = SCORE_PROTOTYPE.equals(score);
    }
}
