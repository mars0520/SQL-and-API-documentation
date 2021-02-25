package com.mars.demo.base.enums;

/**
 * @description 返回枚举错误类型
 * @author xzp
 * @date 2019/10/9 10:58 上午
 **/
public enum ReturnMessageType {

    /**
     * 正确状态码提示
     */
    MSG_SUCCESS_200(200, "请求成功"),

    MSG_SUCCESS_20001(20001, "该数据(主键)已经存在"),
    MSG_SUCCESS_20002(20002, "登录账号不存在"),
    MSG_SUCCESS_20003(20003, "登录账号已禁用/启用"),
    MSG_SUCCESS_20004(20004, "登录密码输入错误"),
    MSG_SUCCESS_20005(20005, "此账号已冻结"),
    MSG_SUCCESS_20006(20006, "当前登录用户不在此系统内"),
    MSG_SUCCESS_20007(20007, "旧密码不匹配"),
    MSG_SUCCESS_20008(20008, "新密码不能与旧密码相同"),
    MSG_SUCCESS_20009(20009, "不能禁用/启用自己"),
    MSG_SUCCESS_20010(20010, "账号或密码不正确"),
    MSG_SUCCESS_20011(20011, "账号未绑定小程序"),
    MSG_SUCCESS_20012(20012, "验证码不正确"),
    MSG_SUCCESS_20013(20013, "此业务五分钟内已经发送过验证码"),
    MSG_SUCCESS_20014(20014, "验证码不正确或不存在验证码"),
    MSG_SUCCESS_20015(20015, "账号在项目内没有绑定角色或角色已经禁用"),


    /**
     * 系统(非法)状态码提示
     */
    MSG_FAIL_500(500, "系统异常"),

    MSG_FAIL_50001(50001, "TOKEN认证失败"),
    MSG_FAIL_50002(50002, "用户授权失败"),
    MSG_FAIL_50003(50003, "权限认证失败"),
    MSG_FAIL_50004(50004, "接口超过请求次数"),
    MSG_FAIL_50005(50005, "参数不符合要求"),
    MSG_FAIL_50006(50006, "用户未登录或登录超时,请重新登录"),
    MSG_FAIL_50007(50007, "(加密)参数不符合要求"),
    MSG_FAIL_50008(50008, "已被系统拉入IP黑名单,禁止访问"),
    MSG_FAIL_50009(50009, "请勿重复请求"),
    MSG_FAIL_50010(50010, "参数不符合标准(请输入正确的枚举值)"),
    MSG_FAIL_50011(50011, "密码输入错误超过五次,请联系管理员"),
    MSG_FAIL_50012(50012, "请求超时，请重试"),
    MSG_FAIL_50013(50013, "枚举不符合"),
    MSG_FAIL_50014(50014, "非管理员无权限修改他人密码"),
    MSG_FAIL_50015(50015, "验证码不正确"),
    MSG_FAIL_50016(50016, "条件不符合正则表达式"),
    MSG_FAIL_50017(50017, "word导出失败"),



    ;

    private int code;
    private String msg;


    ReturnMessageType(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
