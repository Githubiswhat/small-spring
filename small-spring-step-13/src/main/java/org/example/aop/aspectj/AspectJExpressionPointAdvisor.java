package org.example.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.example.aop.Pointcut;
import org.example.aop.PointcutAdvisor;

public class AspectJExpressionPointAdvisor implements PointcutAdvisor {

    private Pointcut pointcut;

    private final String expression;

    public AspectJExpressionPointAdvisor(String expression) {
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        if (pointcut == null){
            return new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return (Advice) this;
    }
}
