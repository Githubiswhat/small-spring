package org.example.springframework.test;


import org.example.springFramework.beans.factory.PropertyValue;
import org.example.springFramework.beans.factory.PropertyValues;
import org.example.springFramework.beans.factory.config.BeanDefinition;
import org.example.springFramework.beans.factory.config.BeanReference;
import org.example.springFramework.beans.factory.support.DefaultListableBeanFactory;
import org.example.springframework.test.bean.UserDao;
import org.example.springframework.test.bean.UserService;
import org.junit.Test;

/**
 * 作者：DerekYRC https://github.com/DerekYRC/mini-spring
 */
public class ApiTest {

    @Test
    public void test_BeanFactory() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. UserDao 注册
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 3. UserService 设置属性[uId、userDao]
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("UId", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        // 4. UserService 注入bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 5. UserService 获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }

}
