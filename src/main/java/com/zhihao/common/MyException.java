package com.zhihao.common;

public class MyException extends RuntimeException{
    private int code;
    private String msg;
    private String data;

    public MyException(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
