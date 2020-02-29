package com.my.springboot04.controller;

import com.my.springboot04.exception.UserNotExitException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {

    //1.浏览器客户端返回的是json，不是自适应
//    @ResponseBody
//    @ExceptionHandler(UserNotExitException.class)
//    public Map<String, Object> handleException(Exception e){
//        Map<String, Object> map = new HashMap<>();
//        map.put("code", "user.notexist");
//        map.put("message", e.getMessage());
//        return map;
//    }


    @ExceptionHandler(UserNotExitException.class)
    public String handleException(Exception e, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        //传入我们自己的错误状态码
        request.setAttribute("javax.servlet.error.status_code", 500);
        map.put("code", "user.notexist");
        map.put("message", e.getMessage());

        request.setAttribute("ext", map);
        //转发到error
        return "forward:/error";
    }

}
