package com.mars.demo.base.bean;


import com.mars.demo.base.enums.ResultValue;
import com.mars.demo.base.enums.ReturnMessageType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @description 响应结果统一返回
 * @author xzp
 * @date 2020/4/30 13:40
 */
@ApiModel(description = "响应结果")
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 1794322263468322103L;

    @ApiModelProperty("调用响应结果")
    private boolean result;

    @ApiModelProperty(value = "状态码")
    private int resultCode;

    @ApiModelProperty("调用结果消息")
    private String msg;

    @ApiModelProperty("响应数据")
    private T data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseResult<T> responseOk() {
        ResponseResult<T> responseResult = new ResponseResult<T>();
        responseResult.setResult(ResultValue.SUCCESS.getValue());
        responseResult.setResultCode(ReturnMessageType.MSG_SUCCESS_200.getCode());
        responseResult.setData(null);
        responseResult.setMsg("调用成功");
        return responseResult;
    }

    public static <T> ResponseResult<T> responseOk(T data) {
        ResponseResult<T> responseResult = new ResponseResult<T>();
        responseResult.setResult(ResultValue.SUCCESS.getValue());
        responseResult.setResultCode(ReturnMessageType.MSG_SUCCESS_200.getCode());
        responseResult.setData(data);
        responseResult.setMsg("调用成功");
        return responseResult;
    }

    public static <T> ResponseResult<T> responseOk(String msg, T data) {
        ResponseResult<T> responseResult = new ResponseResult<T>();
        responseResult.setResult(ResultValue.SUCCESS.getValue());
        responseResult.setResultCode(ReturnMessageType.MSG_SUCCESS_200.getCode());
        responseResult.setMsg(msg);
        responseResult.setData(data);
        return responseResult;
    }

    public static <T> ResponseResult<T> responseOk(ReturnMessageType returnMessageType, T data) {
        ResponseResult<T> responseResult = new ResponseResult<T>();
        responseResult.setResult(ResultValue.SUCCESS.getValue());
        responseResult.setResultCode(returnMessageType.getCode());
        responseResult.setMsg(returnMessageType.getMsg());
        responseResult.setData(data);
        return responseResult;
    }

    public static <T> ResponseResult responseError(T data) {
        ResponseResult<T> responseResult = responseError("调用失败", data);
        responseResult.setData(data);
        return responseResult;
    }

    public static <T> ResponseResult responseError(ReturnMessageType data) {
        ResponseResult<T> responseResult = new ResponseResult<T>();
        responseResult.setResult(ResultValue.ERROR.getValue());
        responseResult.setResultCode(data.getCode());
        responseResult.setMsg(data.getMsg());
        responseResult.setData(null);
        return responseResult;
    }

    public static <T> ResponseResult<T> responseError(String msg, T data) {
        ResponseResult<T> responseResult = new ResponseResult<T>();
        responseResult.setResult(ResultValue.ERROR.getValue());
        responseResult.setResultCode(ReturnMessageType.MSG_FAIL_500.getCode());
        responseResult.setMsg(msg);
        responseResult.setData(data);
        return responseResult;
    }

    public static <T> ResponseResult responseError(int code, String msg, T data) {
        ResponseResult responseResult = responseError(msg, data);
        responseResult.setResultCode(code);
        responseResult.setData(data);
        return responseResult;
    }

    public static <T> ResponseResult responseError(int code, String msg) {
        ResponseResult responseResult = responseError(code, msg, null);
        responseResult.setResultCode(code);
        return responseResult;
    }

}
