package com.my.springboot.config;


import com.my.springboot.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration指明当前类是一个配置类：来替代之前的Spring配置文件
 * 在配置文件中使用<bean><bean/>标签添加组件
 */

@Configuration
public class MyAppConfig {

    //将方法返回值添加到容器中，容器中这个组件默认的id就是方法名
    @Bean
    public HelloService helloService(){
        System.out.println("@Bean给容器加入组件了");
        return new HelloService();
    }

}
