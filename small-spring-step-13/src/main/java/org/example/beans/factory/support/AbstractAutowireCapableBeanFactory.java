package org.example.beans.factory.support;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.bean.BeanUtil;
import org.example.beans.PropertyValue;
import org.example.beans.PropertyValues;
import org.example.beans.factory.Aware;
import org.example.beans.factory.BeanClassLoaderAware;
import org.example.beans.factory.BeanFactoryAware;
import org.example.beans.factory.BeanNameAware;
import org.example.beans.factory.config.AutowireCapableBeanFactory;
import org.example.beans.factory.config.BeanDefinition;
import org.example.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubClassInstantiationStrategy();

    @Override
    protected Object createBean(BeanDefinition beanDefinition, String name, Object[] args) {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, name, args);
            applyPropertyValues(bean, beanDefinition);
            initiateBean(bean, beanDefinition, name);
        } catch (Exception e) {
            throw new BeanException("create bean failed " + name);
        }

        registerDisposableBeanIfNecessary(bean, beanDefinition, name);

        if (beanDefinition.isSingleton()){
            registerSingleton(name, bean);
        }

        return bean;
    }

    private void registerDisposableBeanIfNecessary(Object bean, BeanDefinition beanDefinition, String name) {

    }

    private void initiateBean(Object bean, BeanDefinition beanDefinition, String name) {
        if (bean instanceof Aware){
           if (bean instanceof BeanFactoryAware){
               ((BeanFactoryAware) bean).setBeanFactory(this);
           }

           if (bean instanceof BeanNameAware){
               ((BeanNameAware) bean).setBeanName(name);
           }

           if (bean instanceof BeanClassLoaderAware){
               ((BeanClassLoaderAware) bean).setBeanClassLoader(getDefaultClassLoader());
           }
        }

        //todo


    }

    private void applyPropertyValues(Object bean, BeanDefinition beanDefinition) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference){
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getBeanName());
            }
            BeanUtil.setFieldValue(bean, name, value);
        }
    }

    private Object createBeanInstance(BeanDefinition beanDefinition, String name, Object[] args) {
        Class beanClass = beanDefinition.getBeanClass();
        Constructor constructor = null;
        Constructor[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor declaredConstructor : declaredConstructors) {
            if (args != null && declaredConstructor.getParameterTypes().length == args.length){
                constructor = declaredConstructor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, name, constructor, args);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    public Object applyBeanPostProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return null;
    }

    @Override
    public Object applyBeanPostProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return null;
    }
}
