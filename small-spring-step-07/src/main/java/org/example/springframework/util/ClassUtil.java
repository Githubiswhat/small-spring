package org.example.springframework.util;

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
}
