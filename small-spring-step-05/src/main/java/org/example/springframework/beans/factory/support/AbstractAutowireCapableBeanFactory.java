package org.example.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import org.example.springframework.beans.PropertyValue;
import org.example.springframework.beans.PropertyValues;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    private Instantiation instantiation = new CglibSubclassInstantiation();

    @Override
    public Object createBean(String name, BeanDefinition beanDefinition, Object[] args) {
        Object bean;
        bean = createBeanInstant(name, beanDefinition, args);
        applyProperties(name, beanDefinition);
        addSingleton(name, bean);
        return bean;
    }

    private void applyProperties(String beanName, BeanDefinition beanDefinition) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getClassName());
            }
            BeanUtil.setFieldValue(beanName, name, value);
        }
    }

    private Object createBeanInstant(String name, BeanDefinition beanDefinition, Object[] args) {
        Class beanClass = beanDefinition.getBeanClass();
        Constructor[] declaredConstructors = beanClass.getDeclaredConstructors();
        Constructor constructor = null;
        for (Constructor declaredConstructor : declaredConstructors) {
            if (args != null && declaredConstructor.getParameterTypes().length == args.length) {
                constructor = declaredConstructor;
                break;
            }
        }
        return getInstantiation().instantiate(beanDefinition, name, constructor, args);
    }

    public Instantiation getInstantiation() {
        return instantiation;
    }

    public void setInstantiation(Instantiation instantiation) {
        this.instantiation = instantiation;
    }
}
