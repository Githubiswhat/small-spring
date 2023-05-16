package org.example.springFramework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import org.example.springFramework.beans.BeansException;
import org.example.springFramework.beans.factory.PropertyValue;
import org.example.springFramework.beans.factory.PropertyValues;
import org.example.springFramework.beans.factory.config.BeanDefinition;
import org.example.springFramework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private Instantiation instantiation = new CglibSubClassInstantiation();

    @Override
    public Object creteBean(BeanDefinition beanDefinition, String name, Object[] args) {
        Object bean;
        bean = creteBeanInstantiation(beanDefinition, name, args);
        applyProperties(bean, name, beanDefinition);
        addSingletonBean(name, bean);
        return bean;
    }

    private void applyProperties(Object bean, String beanName, BeanDefinition beanDefinition) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        try {
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getClassName());
                }
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (BeansException e) {
            throw new BeansException("set Property error: " + beanName, e);
        }

    }

    private Object creteBeanInstantiation(BeanDefinition beanDefinition, String name, Object[] args) {
        Class clazz = beanDefinition.getBeanClass();
        Constructor constructor = null;
        Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor declaredConstructor : declaredConstructors) {
            if (args != null && declaredConstructor.getParameterTypes().length == args.length) {
                constructor = declaredConstructor;
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
