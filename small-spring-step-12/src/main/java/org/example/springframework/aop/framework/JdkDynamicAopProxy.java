package org.example.springframework.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.example.springframework.aop.AdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }


    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), advisedSupport.getTargetSource().getTargetClass(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (advisedSupport.getMethodMatcher().matches(method, advisedSupport.getTargetSource().getTarget().getClass())) {
            MethodInterceptor methodInterceptor = advisedSupport.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(method, args, advisedSupport.getTargetSource().getTarget()));
        }
        return method.invoke(advisedSupport.getTargetSource().getTarget(), args);
    }
}
