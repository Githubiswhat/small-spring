package org.example.springframework.aop;


import org.aopalliance.intercept.MethodInterceptor;

public class AdvisedSupport {

    private TargetSource targetSource;

    private MethodMatcher methodMatcher;

    private MethodInterceptor methodInterceptor;

    private boolean proxyByDefault = false;

    public AdvisedSupport() {
    }

    public AdvisedSupport(TargetSource targetSource, MethodMatcher methodMatcher, MethodInterceptor methodInterceptor) {
        this.targetSource = targetSource;
        this.methodMatcher = methodMatcher;
        this.methodInterceptor = methodInterceptor;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public boolean isProxyByDefault() {
        return proxyByDefault;
    }

    public void setProxyByDefault(boolean proxyByDefault) {
        this.proxyByDefault = proxyByDefault;
    }
}
