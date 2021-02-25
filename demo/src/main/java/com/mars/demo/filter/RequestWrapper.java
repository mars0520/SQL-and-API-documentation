package com.mars.demo.filter;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.mars.demo.util.encrypt.AesEncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @description 修改request的值(解密)
 * @author xzp
 * @date 2019/10/24 5:42 下午
 **/
public class RequestWrapper extends HttpServletRequestWrapper{

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * body内容
     */
    private byte[] body;

    /**
     * 参数
     */
    private Map params;

    public RequestWrapper(HttpServletRequest request, Map params){
        super(request);
        this.params = params;
    }

    public RequestWrapper(HttpServletRequest request, String param) {
        super(request);
        getHttpBody(request.getRequestURI(), param);
    }

    /**
     * 获取请求body
     * @author xzp
     * @date 2020/12/31 16:36
     */
    public void getHttpBody(String url, String param){
        Gson gson = new Gson();
        Map mapParam = new HashMap<>(16);
        mapParam = gson.fromJson(param, mapParam.getClass());
        param = (String) mapParam.get("data");
        String decryptParam = null;
        if(null != param){
            decryptParam = AesEncryptUtil.desEncrypt(param);
            body = ((String) JSON.toJSON(decryptParam)).getBytes(StandardCharsets.UTF_8);
        }
        logger.info(url + "-body参数:" + decryptParam);
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException { return byteArrayInputStream.read(); }
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) { }
        };
    }

    @Override
    public Map getParameterMap() {
        return params;
    }

    @Override
    public Enumeration getParameterNames() {
        Vector l = new Vector(params.keySet());
        return l.elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        Object v = params.get(name);
        if(v == null) {
            return null;
        } else
        if(v instanceof String[]) {
            return (String[]) v;
        } else
        if(v instanceof String) {
            return new String[] {(String)v};
        } else {
            return new String[] {v.toString()};
        }
    }

    @Override
    public String getParameter(String name) {
        Object v = params.get(name);
        if(v == null) {
            return null;
        } else
        if(v instanceof String[]) {
            String[] strArr = (String[])v;
            if(strArr.length > 0) {
                return strArr[0];
            } else {
                return null;
            }
        } else
        if(v instanceof String) {
            return (String) v;
        } else {
            return v.toString();
        }
    }

}
