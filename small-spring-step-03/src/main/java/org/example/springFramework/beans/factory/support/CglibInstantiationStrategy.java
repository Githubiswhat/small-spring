package org.example.springFramework.beans.factory.support;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.example.springFramework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public class CglibInstantiationStrategy implements InstantiationStrategy {

    public Object instantiate(BeanDefinition beanDefinition, String className, Constructor ctor, Object[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if (null == ctor) {
            return enhancer.create();
        }
        return enhancer.create(ctor.getParameterTypes(), args);
    }
}
