package org.example.springframework.aop.framework.autoproxy;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.example.springframework.aop.*;
import org.example.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.example.springframework.aop.framework.ProxyFactory;
import org.example.springframework.beans.factory.BeanFactory;
import org.example.springframework.beans.factory.BeanFactoryAware;
import org.example.springframework.beans.factory.config.InstantiateBeanPostProcessor;
import org.example.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Map;

public class DefaultAdvisorAutoProxyCreator implements InstantiateBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;


    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeanBeforeInstantiation(Class<?> clazz, String name) {
        if (isInfrastructureClass(clazz)) {
            return null;
        }
        Map<String, AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansByType(AspectJExpressionPointcutAdvisor.class);
        for (AspectJExpressionPointcutAdvisor advisor : advisors.values()) {
            Pointcut pointcut = advisor.getPointcut();
            ClassFilter classFilter = pointcut.getClassFilter();
            if (!classFilter.matches(clazz)) {
                return null;
            }

            AdvisedSupport advisedSupport = new AdvisedSupport();
            TargetSource targetSource = null;
            try {
                targetSource = new TargetSource(clazz.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            return new ProxyFactory(advisedSupport).getProxy();
        }

        return null;
    }

    private boolean isInfrastructureClass(Class<?> clazz) {
        return Advice.class.isAssignableFrom(clazz) || Pointcut.class.isAssignableFrom(clazz) || Advisor.class.isAssignableFrom(clazz);
    }

    @Override
    public Object postProcessBeanBeforeInitialize(Object bean, String name) {
        return bean;
    }

    @Override
    public Object postProcessBeanAfterInitialize(Object bean, String name) {
        return bean;
    }
}
