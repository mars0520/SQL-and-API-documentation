package com.mars.demo.base.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xzp
 * @description 创建测试数据常量配置
 * @date 2021/1/12
 **/
public class CreateDataConst {

    /**
     * 例子-值
     */
    public static final Map<String, Object> TEST_VALUE = new HashMap<String, Object>(2){{
        //随机数组
        put("name", new String[] {"张三", "李四", "王五"});
        //默认值
        put("sex", "男");
    }};
    /**
     * 例子-类型
     */
    public static final Map<String, String> TEST_TYPE = new HashMap<String, String>(3){{
        //随机数组
        put("name", "RandomArray");
        put("sex", "String");
        put("age", "Integer");
    }};


}
