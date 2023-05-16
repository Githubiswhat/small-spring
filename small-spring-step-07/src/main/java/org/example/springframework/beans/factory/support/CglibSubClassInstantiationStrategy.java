package org.example.springframework.beans.factory.support;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.example.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public class CglibSubClassInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiates(BeanDefinition beanDefinition, String name, Constructor constructor, Object[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if (constructor == null) return enhancer.create();
        return enhancer.create(constructor.getParameterTypes(), args);
    }
}
