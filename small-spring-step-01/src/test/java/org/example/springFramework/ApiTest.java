package org.example.springFramework;

import junit.framework.TestCase;
import org.example.springFramework.bean.UserService;
import org.junit.Test;

public class ApiTest extends TestCase {

    @Test
    public void testApi() {
        BeanFactory beanFactory = new BeanFactory();
        BeanDefinition userServiceBean = new BeanDefinition(new UserService());
        beanFactory.registerBeanDefinition("userService", userServiceBean);
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.printUser();
    }

}