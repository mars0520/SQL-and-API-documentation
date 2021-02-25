package com.mars.demo.util.web;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @author xzp
 * @date 2019/10/24 11:48 上午
 **/
@Component
public class ServiceInitListener implements ServletContextListener, ApplicationListener<WebServerInitializedEvent> {

    Logger logger = LoggerFactory.getLogger(ServiceInitListener.class.getName());

    private WebApplicationContext springContext;


    @Override
    public void contextInitialized(ServletContextEvent event) {
        springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());

        HashMap<String, String> pathMap = new HashMap<String, String>();
        //存储所有url集合

        //WebApplicationContext springContext = (WebApplicationContext) SpringContextUtil.getApplicationContext();

        RequestMappingHandlerMapping bean = springContext.getBean(RequestMappingHandlerMapping.class);
        //通过上下文对象获取RequestMappingHandlerMapping实例对象

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();

        for (RequestMappingInfo rmi : handlerMethods.keySet()) {

            HandlerMethod hm = handlerMethods.get(rmi);
            String pathClassName = hm.getBeanType().getName();
            PatternsRequestCondition prc = rmi.getPatternsCondition();
            Annotation annotation = hm.getMethodAnnotation(ApiOperation.class);
            String annotationValue = "";
            if (annotation != null) {
                annotationValue = ((ApiOperation) annotation).value();
            }


            Set<String> patterns = prc.getPatterns();
            for (String path : patterns) {
                pathMap.put(path, annotationValue);

                logger.info("path:{},annotation:{}", path, annotationValue);

            }
        }


    }


    @Override
    public void contextDestroyed(ServletContextEvent event) {


    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        int serverPort = event.getWebServer().getPort();
        String ip = getIp();
    }


    private String getIp() {
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }


}
