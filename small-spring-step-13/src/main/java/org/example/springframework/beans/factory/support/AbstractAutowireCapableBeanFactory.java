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
import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubClassInstantiationStrategy();

    @Override
    protected Object createBean(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Object bean = null;
        try {

            bean = resolveBeforeInstantiation(beanName, beanDefinition);
            if (bean != null) {
                return bean;
            }

            bean = createBeanInstance(beanDefinition, beanName, args);
            applyPropertyValues(bean, beanDefinition);
            bean = initiateBean(bean, beanDefinition, beanName);
        } catch (Exception e) {
            throw new BeanException("create bean failed " + beanName);
        }

        registerDisposableBeanIfNecessary(bean, beanDefinition, beanName);

        if (beanDefinition.isSingleton()){
            registerSingleton(beanName, bean);
        }

        return bean;
    }

    private Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessBeforeInitialization(beanDefinition.getBeanClass(), beanName);
        if (bean != null){
            bean = applyBeanPostProcessAfterInitialization(bean, beanName);
        }
        return bean;
    }

    protected Object applyBeanPostProcessBeforeInitialization(Class<?> beanClass, String beanName){
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (null != result) return result;
            }
        }
        return null;
    }

    private void registerDisposableBeanIfNecessary(Object bean, BeanDefinition beanDefinition, String beanName) {
        if (!beanDefinition.isSingleton()) return;

        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethod())){
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }


    private Object initiateBean(Object bean, BeanDefinition beanDefinition, String name) {
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

        Object wrapperBean = applyBeanPostProcessBeforeInitialization(bean, name);
        try {
            invokerInitMethods(name, wrapperBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + name + "] failed", e);
        }
        wrapperBean = applyBeanPostProcessAfterInitialization(wrapperBean, name);
        return wrapperBean;
    }

    private void invokerInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        if (bean instanceof InitializingBean){
            ((InitializingBean) bean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethod();
        if (StrUtil.isNotEmpty(initMethodName)){
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == initMethod){
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
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
    public Object applyBeanPostProcessBeforeInitialization(Object existBean, String beanName) throws BeanException {
        Object result = existBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()){
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessAfterInitialization(Object existBean, String beanName) throws BeanException {
        Object result = existBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()){
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) return result;
            result = current;
        }
        return result;
    }
}
