package com.my.springboot04.config;

import com.my.springboot04.filter.Myfilter;
import com.my.springboot04.listener.MyListener;
import com.my.springboot04.servlet.MyServlet;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.Servlet;
import java.util.Arrays;

@Configuration
public class MyServerConfig {

    //注册三大组件
    @Bean
    public ServletRegistrationBean myServlet(){
        ServletRegistrationBean<Servlet> servletServletRegistrationBean = new ServletRegistrationBean<>(new MyServlet(), "/myServlet");
        servletServletRegistrationBean.setLoadOnStartup(1);
        return servletServletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean myFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new Myfilter());
        filterFilterRegistrationBean.setUrlPatterns(Arrays.asList("/hello", "/myServlet"));

        return filterFilterRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean myListener(){
        ServletListenerRegistrationBean<MyListener> myListenerServletListenerRegistrationBean = new ServletListenerRegistrationBean<>(new MyListener());
        return myListenerServletListenerRegistrationBean;
    }

    //配置嵌入式服务器
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {

            //定制嵌入式Servlet容器相关的规则
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                factory.setPort(8083);
            }
        };
    }
}
