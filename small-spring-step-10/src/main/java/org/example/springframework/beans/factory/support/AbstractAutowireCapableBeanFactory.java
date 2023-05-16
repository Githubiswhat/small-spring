package org.example.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.PropertyValue;
import org.example.springframework.beans.PropertyValues;
import org.example.springframework.beans.factory.*;
import org.example.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubClassInstantiationStrategy();

    @Override
    protected Object createBean(BeanDefinition beanDefinition, String name, Object[] args) {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, name, args);
            applyPropertyValues(bean, beanDefinition);
            bean = initializeBean(beanDefinition, name, bean);
        } catch (Exception e) {
            throw new BeansException("bean initialize failed " + name);
        }

        registerDisposableBeanIfNecessary(beanDefinition, bean, name);

        if (beanDefinition.isSingleton()) {
            registerSingleton(name, bean);
        }

        return bean;
    }

    private void registerDisposableBeanIfNecessary(BeanDefinition beanDefinition, Object bean, String name) {
        if (beanDefinition.isPrototype()) {
            return;
        }
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethod())) {
            registerDisposable(name, new DisposableBeanAdapter(bean, name, beanDefinition));
        }
    }

    private Object initializeBean(BeanDefinition beanDefinition, String name, Object bean) {
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

        Object wrapperBean = applyBeanPostProcessorBeforeInitializers(bean, name);

        try {
            invokeInitializeMethod(wrapperBean, beanDefinition, name);
        } catch (Exception e) {
            throw new BeansException("invoke init method failed " + name);
        }
        wrapperBean = applyBeanPostProcessorAfterInitializers(bean, name);
        return wrapperBean;
    }

    private void invokeInitializeMethod(Object bean, BeanDefinition beanDefinition, String name) throws Exception {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).initialize(bean, name);
        }

        if (StrUtil.isNotEmpty(beanDefinition.getInitMethod())) {
            Method method = beanDefinition.getClass().getMethod(beanDefinition.getInitMethod());
            if (method == null) {
                throw new BeansException("no such init method " + beanDefinition.getInitMethod());
            }
            method.invoke(bean);
        }
    }


    private void applyPropertyValues(Object bean, BeanDefinition beanDefinition) {
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

    @Override
    public Object applyBeanPostProcessorBeforeInitializers(Object existBean, String beanName) {
        Object result = existBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitializers(Object existBean, String beanName) {
        Object result = existBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
