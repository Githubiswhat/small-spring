package org.example.springframework.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.example.springframework.aop.Pointcut;
import org.example.springframework.aop.PointcutAdvisor;

public class AspectJExpressionPointAdvisor implements PointcutAdvisor {

    private Pointcut pointcut;

    private Advice advice;

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
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
