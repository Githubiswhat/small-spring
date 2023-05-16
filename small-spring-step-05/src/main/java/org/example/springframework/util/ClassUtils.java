package org.example.springframework.util;

public class ClassUtils {

    public static ClassLoader getClassLoader() {
        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Exception e) {

        }

        if (cl == null) {
            cl = ClassLoader.class.getClassLoader();
        }
        return cl;
    }

    public static ClassLoader getClassLoader1() {
        ClassLoader classLoader = null;

        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Exception e) {
        }
        if (classLoader == null) {
            classLoader = ClassLoader.class.getClassLoader();
        }
        return classLoader;
    }


}
