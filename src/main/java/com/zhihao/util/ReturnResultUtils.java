package com.zhihao.util;
import com.zhihao.pojo.Result;

public class ReturnResultUtils {
    public static final int SUCCESS = 200;

    public static Result returnSuccess() {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("操作成功");
        return result;
    }

    public static Result returnSuccess(Object data) {
        Result returnResult = new Result();
        returnResult.setCode(200);
        returnResult.setMsg("操作成功");
        returnResult.setData(data);
        return returnResult;
    }

    public static Result returnFail(Integer code, String message) {
        Result returnResult = new Result();
        returnResult.setCode(code);
        returnResult.setMsg(message);
        return returnResult;
    }
}
