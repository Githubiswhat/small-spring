package org.example.util;

public class ClassUtil {

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Exception e) {

        }
        if (classLoader == null) {
           classLoader = ClassUtil.class.getClassLoader();
        }
        return classLoader;
    }

    public static boolean isCglibSubClass(Class<?> clazz) {
        return clazz != null && isCglibSubClassName(clazz.getName());
    }

    private static boolean isCglibSubClassName(String name) {
        return name != null && name.contains("$$");
    }

}
