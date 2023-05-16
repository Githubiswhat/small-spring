package org.example.springframework.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.example.springframework.aop.Pointcut;
import org.example.springframework.aop.PointcutAdvisor;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut;

    private Advice advice;

    private String expression;

    public AspectJExpressionPointcutAdvisor() {
    }

    public AspectJExpressionPointcutAdvisor(String expression) {
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        if (pointcut == null) {
            pointcut = new AspectJExpressionPointcut(expression);
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

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
