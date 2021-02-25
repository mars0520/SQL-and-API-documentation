package com.mars.demo.filter;

import com.alibaba.fastjson.JSON;

import com.mars.demo.base.bean.ResponseResult;
import com.mars.demo.base.enums.ReturnMessageType;
import com.mars.demo.util.JsonUtil;
import com.mars.demo.util.encrypt.AesEncryptUtil;
import com.mars.demo.util.web.HttpHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @description 过滤器(修改request)
 * @author xzp
 * @date 2019/10/24 5:44 下午
 **/
public class RequestReplacedFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURI();
        //boolean b = "/api/getDesEncrypt".equals(url) || "/api/getEncrypt".equals(url);
        logger.info(request.getSession().getId() + ",请求路径:" + url);
        if(false){
            try {
                String param = HttpHelper.getBodyString(request);
                ServletRequest requestWrapper;
                if(StringUtils.isNotBlank(param)){
                    /* body传参格式 {"data":"秘文"} */
                    //解密body参数
                    requestWrapper = new RequestWrapper(request, param);
                }else {
                    //解密getParameterMap参数
                    Map<String, String[]> map = request.getParameterMap();
                    map = new HashMap<>(map);
                    for(Map.Entry<String, String[]> m : map.entrySet()){
                        String value = null;
                        //解密
                        for(String str : m.getValue()){
                            value = AesEncryptUtil.desEncrypt(str);
                        }
                        if(null != value){
                            //重新赋值
                            map.put(m.getKey(), new String[]{value});
                        }
                    }
                    getHttpParams(url, map);
                    requestWrapper = new RequestWrapper(request, map);
                }
                chain.doFilter(requestWrapper, response);
            } catch (Exception e) {
                e.printStackTrace();
                String jsonObjectStr = JSON.toJSONString(ResponseResult.responseError(ReturnMessageType.MSG_FAIL_50007));
                JsonUtil.returnJson((HttpServletResponse) response, jsonObjectStr);
            }
        }else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }


    /**
     * 获取请求参数
     * @author xzp
     * @date 2020/12/31 16:36
     */
    public void getHttpParams(String url, Map<String, String[]> map){
        if(null != map){
            map = new HashMap(map);
            StringBuffer sb = new StringBuffer();
            sb.append("{");
            int i = 1, size = map.size();
            for(String key: map.keySet()) {
                String[] strArr = map.get(key);
                for (String str : strArr) {
                    sb.append(key).append(":").append(str);
                }
                if(i != size){
                    sb.append(",");
                }
                map.put(key, null);
                i ++;
            }
            sb.append("}");
            logger.info(url + "-参数:" + sb);
        }
    }
}

