package com.mars.demo.util.web;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @description 监听实现 IpFilter
 * @author xzp
 * @date 2019/10/24 11:48 上午
 **/
@WebListener
public class MyApplicationListener implements ServletContextListener {

    Logger logger = LoggerFactory.getLogger(MyApplicationListener.class.getName());

    private WebApplicationContext springContext;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("MyApplicationListener初始化成功");
        ServletContext context = event.getServletContext();
        // IP存储器
        Map<String, Long[]> ipMap = new HashMap<String, Long[]>();
        context.setAttribute("ipMap", ipMap);
        // 限制IP存储器：存储被限制的IP信息
        Map<String, Long> limitedIpMap = new HashMap<String, Long>();
        context.setAttribute("limitedIpMap", limitedIpMap);

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
            String annotationValue = ((ApiOperation) annotation).value();

            Set<String> patterns = prc.getPatterns();
            for (String path : patterns) {
                pathMap.put(path, annotationValue);

                logger.info("path:{},annotation:{}", path, annotationValue);

            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub

    }
}