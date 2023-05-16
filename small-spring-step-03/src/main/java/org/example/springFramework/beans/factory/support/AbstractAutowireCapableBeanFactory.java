package org.example.springFramework.beans.factory.support;

import org.example.springFramework.beans.BeanException;
import org.example.springFramework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy cglibInstantiationStrategy = new CglibInstantiationStrategy();

    @Override
    public Object createBean(String name, BeanDefinition beanDefinition, Object[] args) {
        Object bean;
        try {
            bean = createBeanInstantiation(beanDefinition, name, args);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new BeanException("bean instantiation failed ", e);
        }
        addSingletonBean(name, bean);
        return bean;
    }

    private Object createBeanInstantiation(BeanDefinition beanDefinition, String name, Object[] args) throws IllegalAccessException, InstantiationException {
        Class clazz = beanDefinition.getBeanClass();
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            if (args != null && constructor.getParameterTypes().length == args.length) {
                return cglibInstantiationStrategy.instantiate(beanDefinition, name, constructor, args);
            }
        }
        return getCglibInstantiationStrategy().instantiate(beanDefinition, name, null, args);
    }

    public InstantiationStrategy getCglibInstantiationStrategy() {
        return cglibInstantiationStrategy;
    }

    public void setCglibInstantiationStrategy(InstantiationStrategy cglibInstantiationStrategy) {
        this.cglibInstantiationStrategy = cglibInstantiationStrategy;
    }
}
