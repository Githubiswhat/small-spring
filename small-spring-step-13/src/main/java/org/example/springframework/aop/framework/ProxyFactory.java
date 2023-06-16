package org.example.springframework.aop.framework;

import org.example.springframework.aop.AdvisedSupport;

public class ProxyFactory {

    private AdvisedSupport advisorSupport;

    public ProxyFactory(AdvisedSupport advisorSupport) {
        this.advisorSupport = advisorSupport;
    }

    private AopProxy getAopProxy(){
        if (advisorSupport.isProxyByDefault()){
            return new CglibAopProxy(advisorSupport);
        }
        return new JdkDynamicAopProxy(advisorSupport);
    }

    public Object getProxy() {
        return getAopProxy().getProxy();
    }
}
