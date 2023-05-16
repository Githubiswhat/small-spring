package org.example.springframework.beans;

public class BeansException extends RuntimeException {

    public BeansException(String s) {
        super(s);
    }

    public BeansException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
