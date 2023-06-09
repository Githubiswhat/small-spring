package org.example.springframework.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {

    boolean matches(Method method, Class<?> requestClass);

}
