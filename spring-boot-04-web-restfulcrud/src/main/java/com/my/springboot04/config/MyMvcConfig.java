package com.my.springboot04.config;


import com.my.springboot04.component.LoginHandlerInterceptor;
import com.my.springboot04.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//使用WebMvcConfigurer可以扩展
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器发送/my， 请求来到success页面
        registry.addViewController("/my").setViewName("success");

    }

    //所有的WebMvcConfigurer都会一起起作用
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer configurer = new WebMvcConfigurer(){
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");
            }

            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //静态资源：*css, *js不需要管，SpringBoot已经做好静态资源映射
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/index.html","/","/user/login","/assert/css/**","/webjars/**");
            }
        };
        return configurer;
    }

    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();

    }
}
