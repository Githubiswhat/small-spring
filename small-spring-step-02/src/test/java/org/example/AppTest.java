package org.example;

import junit.framework.TestCase;
import org.example.springFramework.bean.UserService;
import org.example.springFramework.beans.factory.config.BeanDefinition;
import org.example.springFramework.beans.factory.support.DefaultListableBeanFactory;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    public void test_beanFactory() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        BeanDefinition userServiceBeanDefinition = new BeanDefinition(UserService.class);
        defaultListableBeanFactory.registerBeanDefinition("userService", userServiceBeanDefinition);
        UserService userService = (UserService) defaultListableBeanFactory.getBean("userService");
        userService.printUser();
    }

}
