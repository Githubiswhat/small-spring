package org.example.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.PropertyValue;
import org.example.springframework.beans.PropertyValues;
import org.example.springframework.beans.factory.*;
import org.example.springframework.beans.factory.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubClassInstantiationStrategy();

    @Override
    protected Object createBean(BeanDefinition beanDefinition, String name, Object[] args) {
        Object bean;
        try {

            bean = resolveBeforeInstantiation(beanDefinition, name);
            if (bean != null) {
                return bean;
            }

            bean = createBeanInstance(beanDefinition, name, args);
            applyProperties(beanDefinition, bean);
            bean = initializeBean(beanDefinition, bean, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        registerDisposableBeanIfNecessary(bean, beanDefinition, name);

        if (beanDefinition.isSingleton()) {
            registerSingletonBean(name, bean);
        }

        return bean;
    }

    private Object resolveBeforeInstantiation(BeanDefinition beanDefinition, String name) {
        Object bean = applyBeanPostProcessBeforeInitialization(beanDefinition.getBeanClass(), name);
        if (bean != null) {
            applyBeanPostProcessAfterInitialization(bean, name);
        }
        return bean;
    }

    private Object applyBeanPostProcessBeforeInitialization(Class<?> clazz, String name) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiateBeanPostProcessor) {
                Object bean = ((InstantiateBeanPostProcessor) beanPostProcessor).postProcessBeanBeforeInstantiation(clazz, name);
                if (bean != null) {
                    return bean;
                }
            }
        }
        return null;
    }

    private void registerDisposableBeanIfNecessary(Object bean, BeanDefinition beanDefinition, String name) {
        if (beanDefinition.isPrototype()) {
            return;
        }
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethod())) {
            registerDisposableBean(name, new DisposableBeanAdapter(bean, name, beanDefinition));
        }
    }

    private Object initializeBean(BeanDefinition beanDefinition, Object bean, String name) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(name);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getDefaultClassLoader());
            }
        }

        Object wrapperBean = applyBeanPostProcessBeforeInitialization(bean, name);
        try {
            invokeInitializer(bean, wrapperBean, beanDefinition);
        } catch (BeanException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new BeansException("invoke init method failed", e);
        }
        wrapperBean = applyBeanPostProcessAfterInitialization(wrapperBean, name);
        return wrapperBean;
    }

    private void invokeInitializer(Object bean, Object wrapperBean, BeanDefinition beanDefinition) throws BeanException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (wrapperBean instanceof InitiatingBean) {
            ((InitiatingBean) wrapperBean).afterPropertiesSet();
        }

        if (StrUtil.isNotEmpty(beanDefinition.getInitMethod())) {
            Method method = beanDefinition.getBeanClass().getMethod(beanDefinition.getInitMethod());
            if (method == null) {
                throw new BeanException("no such init method");
            }
            method.invoke(wrapperBean);
        }
    }


    @Override
    public Object applyBeanPostProcessBeforeInitialization(Object existBean, String name) {
        Object result = existBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeanBeforeInitialize(result, name);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessAfterInitialization(Object existBean, String name) {
        Object result = existBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeanAfterInitialize(result, name);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    private void applyProperties(BeanDefinition beanDefinition, Object bean) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }
            BeanUtil.setFieldValue(bean, name, value);
        }
    }

    private Object createBeanInstance(BeanDefinition beanDefinition, String name, Object[] args) {
        Class beanClass = beanDefinition.getBeanClass();
        Constructor[] declaredConstructors = beanClass.getDeclaredConstructors();
        Constructor constructor = null;
        for (Constructor ctor : declaredConstructors) {
            if (args != null && ctor.getParameterTypes().length == args.length) {
                constructor = ctor;
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


}
