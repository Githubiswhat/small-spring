package org.example.springframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.example.springframework.aop.MethodBeforeAdvice;


public class MethodBeforeAdvisorPointcut implements MethodInterceptor {

    private MethodBeforeAdvice advice;

    public MethodBeforeAdvisorPointcut() {
    }

    public MethodBeforeAdvisorPointcut(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.advice.before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return methodInvocation.proceed();
    }
}
