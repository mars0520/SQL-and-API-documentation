package com.mars.demo.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description json工具类
 * @author xzp
 * @date 2019/10/24 5:42 下午
 **/
public class JsonUtil {

    /**
     * json 转为 List<Map<String, Object>>
     *
     * @param json
     * @return
     * @author xnx
     */
    public static List<Map<String, Object>> toListMap(String json) {
        List<Object> list = JSON.parseArray(json);

        List<Map<String, Object>> listw = new ArrayList<Map<String, Object>>();
        for (Object object : list) {
            Map<String, Object> ageMap = new HashMap<String, Object>();
            Map<String, Object> ret = (Map<String, Object>) object;
            //取出list里面的值转为map
            listw.add(ret);
        }
        return listw;

    }


    public static Map<String, String> jsonToMap(String jsonStr) {
        Map<String, String> jsonMap = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonMap = mapper.readValue(jsonStr, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonMap;
    }

    /**
     * @description 返回前端JSON格式数据
     * @author xzp
     * @date 2019/10/9 4:53 下午
     **/
    public static void returnJson(HttpServletResponse response, String json) {
        PrintWriter writer = null;
        response.setCharacterEncoding( "UTF-8" );
        response.setContentType( "application/json;charset=utf-8" );
        try {
            writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream()));
            writer.print( json );
        } catch (IOException e) {
        }finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    /**
     * hippo-server used 清洗对象格式
     *
     * @param obj origin obj
     * @return obj
     */
    public static Object cleanseToObject(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            if (obj.getClass().isPrimitive()) {
                return obj;
            } else {
                return JSON.toJSONString(obj);
            }
        } catch (Exception e) {
            throw new ClassCastException("obj cleanse:" + ToStringBuilder.reflectionToString(obj));
        }
    }


}