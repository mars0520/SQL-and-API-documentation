package com.mars.demo.base.constant;

import com.mars.demo.bean.dto.InterfaceParamDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 系统常量
 * @author xzp
 * @date 2020/12/29
 **/
public class SysConst {

    /**
     * 一天(单位：毫秒)
     */
    public static final int SHIRO_SESSION_TIMEOUT = 24 * 3600 * 1000;

    /**
     * 超级管理员ID
     */
    public static final String IS_SUPERMAN_ID = "isSuperMan-isSuperMan-isSuperMan";

    /**
     * 菜单第一层
     */
    public static final String MENU_LEVEL_1 = "00000000000000000000000000000000";

    /**
     * 请求类型OPTIONS
     */
    public static final String REQUEST_OPTIONS = "OPTIONS";

    /**
     * 请求类型POST
     */
    public static final String REQUEST_POST = "POST";

    /**
     * 请求类型GET
     */
    public static final String REQUEST_GET = "GET";

    /**
     * 请求类型PUT
     */
    public static final String REQUEST_PUT = "PUT";

    /**
     * 请求类型DELETE
     */
    public static final String REQUEST_DELETE = "DELETE";

    /**
     * 请求类型HEAD
     */
    public static final String REQUEST_HEAD = "HEAD";

    /**
     * 字符编码
     */
    public static final String CHARACTER_UTF_8 = "UTF-8";

    /**
     * token
     */
    public static final String SYSTEM_TOKEN = "TOKEN";

    /**
     * sessionId
     */
    public static final String SESSION_ID = "sessionId";

    /**
     * 登录验证码
     */
    public static final String LOGIN_CODE = "login_code";

    /**
     * 简单密码
     */
    public static final List<String> SIMPLE_PASSWORD_LIST = new ArrayList<String>(8){{add("123456");add("654321");add("qwer1234");}};

    /**
     * 简单数字
     */
    public static final List<Integer> SIMPLE_NUMBER_LIST = new ArrayList<Integer>(8){{add(1);add(2);add(3);}};

    /**
     * 登陆路径
     */
    public static final String LOGIN_URL = "/api/login";


    /**
     * token失效时间
     */
    public static final long EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    /**
     * token码
     */
    public static final String TOKEN_SECRET = "mars";


    /**
     * 基础类型
     */
    public static String[] baseTypes = {
            "com.mars.demo.base.bean.ResponseResult",
            "java.lang.Integer",
            "java.lang.Double",
            "java.lang.Float",
            "java.lang.Long",
            "java.lang.Short",
            "java.lang.Byte",
            "java.lang.Boolean",
            "java.lang.Character",
            "java.lang.String",
            "int","double","long","short","byte","boolean","char","float"
    };



    /**
     * 获取响应列表
     * @return
     */
    public static List<InterfaceParamDTO> responseList = new ArrayList<InterfaceParamDTO>(8){{
        add(new InterfaceParamDTO().setParamName("200").setParamCode("响应成功"));
        add(new InterfaceParamDTO().setParamName("401").setParamCode("未授权"));
        add(new InterfaceParamDTO().setParamName("403").setParamCode("被禁止"));
        add(new InterfaceParamDTO().setParamName("404").setParamCode("找不到资源"));
        add(new InterfaceParamDTO().setParamName("500").setParamCode("服务器内部错误"));
    }};

}
