package com.mars.demo.base.exception;


import com.mars.demo.base.enums.ReturnMessageType;

/**
 * @description 定义全局业务异常,封装业务异常及业务错误
 * @author xzp
 * @date 2020/4/30 14:08
 **/
public class ParamExceptionHandling extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected int code;
    protected String msg;

    public ParamExceptionHandling() {
        super();
    }

    /**
     * @description 抛出参数认证失败异常
     * @author xzp
     * @date 2020/7/21 10:59
     * @param 》content:说明
     * @return 》
     */
    public static void throwVerifyParam(String content){
        throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50005.getCode(), content.concat(ReturnMessageType.MSG_FAIL_50005.getMsg()));
    }

    public ParamExceptionHandling(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public ParamExceptionHandling(ReturnMessageType returnMessageType) {
        super();
        this.code = returnMessageType.getCode();
        this.msg = returnMessageType.getMsg();
    }

    public ParamExceptionHandling(Throwable e) {
        super(e);
    }

    public ParamExceptionHandling(String message) {
        super(message);
    }

    public ParamExceptionHandling(int code, String msg, Throwable e) {
        super(e);
        this.code = code;
        this.msg = msg;
    }

    public ParamExceptionHandling(ReturnMessageType returnMessageType, Throwable e) {
        super(e);
        this.code = returnMessageType.getCode();
        this.msg = returnMessageType.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
