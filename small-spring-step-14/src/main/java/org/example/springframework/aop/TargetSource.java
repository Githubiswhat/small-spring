package org.example.springframework.aop;

import cn.hutool.core.lang.generator.ObjectGenerator;

public class TargetSource {

    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }


    public Class<?>[] getTargetClasses() {
        return this.target.getClass().getInterfaces();
    }

    public Object getTarget(){
        return this.target;
    }

}
