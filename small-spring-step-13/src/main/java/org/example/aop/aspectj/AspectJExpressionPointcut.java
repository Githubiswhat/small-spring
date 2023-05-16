package org.example.aop.aspectj;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.example.aop.ClassFilter;
import org.example.aop.MethodMatcher;
import org.example.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AspectJExpressionPointcut implements ClassFilter, MethodMatcher, Pointcut {

    private final PointcutExpression pointcutExpression;

    private static final Set<PointcutPrimitive> primitives = new HashSet<>();

    static {
        primitives.add(PointcutPrimitive.EXECUTION);
    }

    public AspectJExpressionPointcut(String expression) {
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(primitives, Thread.currentThread().getContextClassLoader());
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return false;
    }

    @Override
    public boolean matches(Method method, Class<?> requestClass) {
        return false;
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
