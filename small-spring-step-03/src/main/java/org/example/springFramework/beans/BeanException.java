package org.example.springFramework.beans;

public class BeanException extends RuntimeException {
    public BeanException(String s) {
        super(s);
    }

    public BeanException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
