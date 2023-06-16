package org.example.springframework.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.example.springframework.aop.Advisor;
import org.example.springframework.aop.Pointcut;
import org.example.springframework.aop.PointcutAdvisor;

public class AspectJExpressionPointAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut;

    private Advice advice;

    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (pointcut == null){
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }
}
