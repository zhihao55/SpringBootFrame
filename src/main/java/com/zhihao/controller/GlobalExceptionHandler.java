//package com.zhihao.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler({NoHandlerFoundException.class})
//    @ResponseBody
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public Object notFountHandler(HttpServletRequest request, NoHandlerFoundException e) {
//        Map<String, Object> data = new HashMap();
//        data.put("code", 404);
//        data.put("msg", "未找到该页面");
//        data.put("data", "");
//        return data;
//    }
//}
