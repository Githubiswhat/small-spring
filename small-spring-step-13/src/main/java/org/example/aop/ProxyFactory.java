package org.example.aop;

import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import org.example.aop.framework.AopProxy;
import org.example.aop.framework.CglibAopProxy;
import org.example.aop.framework.JdkDynamicAopProxy;

public class ProxyFactory {

    private AdvisorSupport advisorSupport;

    public ProxyFactory(AdvisorSupport advisorSupport) {
        this.advisorSupport = advisorSupport;
    }

    public Object createProxy(){
        return getAopProxy().getProxy();
    }

    private AopProxy getAopProxy(){
        if (advisorSupport.isProxyByDefault()){
            return new CglibAopProxy();
        }
        return new JdkDynamicAopProxy();
    }

}
