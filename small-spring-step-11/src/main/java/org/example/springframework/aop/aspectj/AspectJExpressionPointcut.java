package org.example.springframework.aop.aspectj;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.example.springframework.aop.ClassFilter;
import org.example.springframework.aop.MethodMatcher;
import org.example.springframework.aop.PointCut;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AspectJExpressionPointcut implements ClassFilter, MethodMatcher, PointCut {

    private final static Set<PointcutPrimitive> SUPPORTED_POINTCUTS = new HashSet<PointcutPrimitive>();

    static {
        SUPPORTED_POINTCUTS.add(PointcutPrimitive.EXECUTION);
    }

    private final PointcutExpression pointcutExpression;


    public AspectJExpressionPointcut(String expression) {
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_POINTCUTS, this.getClass().getClassLoader());
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }


    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
