package org.example.aop;

public class TargetSource {

    private Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetClass() {
        return target.getClass().getInterfaces();
    }

}
