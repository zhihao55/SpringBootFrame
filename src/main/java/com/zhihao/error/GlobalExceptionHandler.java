package com.zhihao.error;

import com.zhihao.common.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public Object handleExceptions(Exception e, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        map.put("code", 400);
        map.put("msg", e.getMessage());
        map.put("data", "");
        return map;
    }

    @ExceptionHandler({MyException.class})
    public Object handleException(Exception e, HttpServletRequest request) {
        System.out.println("捕获异常2");
        Map<String, Object> map = new HashMap();
        map.put("code", ((MyException) e).getCode());
        map.put("msg", ((MyException) e).getMsg());
        map.put("data", ((MyException) e).getData());
        return map;
    }

    /**
     * 处理请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public Object handleException(BindException e, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        map.put("code", 400);
        map.put("msg", e.getBindingResult().getFieldError().getDefaultMessage());
        map.put("data", "");
        return map;
    }

    /**
     * 处理请求参数格式错误 @RequestBody上使用@Valid 实体上使用@NotNull等，验证失败后抛出的异常是MethodArgumentNotValidException异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleException(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        map.put("code", 400);
        map.put("msg", e.getMessage());
        map.put("data", "");
        return map;
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Object handleException(ConstraintViolationException e, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        map.put("code", 400);
        map.put("msg", e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining()));
//        map.put("msg", e.getMessage());
        map.put("data", "");
        return map;
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object notFountHandler(HttpServletRequest request, NoHandlerFoundException e) {
        Map<String, Object> data = new HashMap();
        data.put("code", 404);
        data.put("msg", "未找到该页面");
        data.put("data", "");
        return data;
    }


}
