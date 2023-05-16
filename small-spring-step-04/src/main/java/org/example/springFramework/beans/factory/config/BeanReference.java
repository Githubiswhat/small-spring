package org.example.springFramework.beans.factory.config;

public class BeanReference {
    private String className;

    public BeanReference(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
