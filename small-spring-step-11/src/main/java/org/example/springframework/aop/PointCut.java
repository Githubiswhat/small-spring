package org.example.springframework.aop;

public interface PointCut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

}
