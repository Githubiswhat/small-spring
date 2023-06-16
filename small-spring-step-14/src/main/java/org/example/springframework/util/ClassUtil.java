package org.example.springframework.util;

public class ClassUtil {

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Exception e) {

        }
        if (classLoader == null){
            classLoader = ClassUtil.class.getClassLoader();
        }
        return classLoader;
    }

    public static boolean isCglibProxyClass(Class<?> clazz){
        return clazz != null && isCglibProxyClassName(clazz.getName());
    }

    private static boolean isCglibProxyClassName(String className) {
        return (className != null && className.contains("$$"));
    }

}
