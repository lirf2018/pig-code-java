package com.yufan.data.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 创建人: lirf
 * 创建时间:  2019/4/15 11:47
 * 功能介绍:
 */
public class ApplicationInit {

    private static ApplicationContext applicationContext = null;

    static {
        //加载applicationContext.xml文件
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
    }

    public static Object getTargetClassBan(Class c) {
        return applicationContext.getBean(c);
    }

}
