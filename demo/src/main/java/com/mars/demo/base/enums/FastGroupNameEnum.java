package com.mars.demo.base.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xzp
 * @description
 * @date 2021/1/20
 **/
public enum FastGroupNameEnum {

    TEST("test"),
    GROUP1("group1"),

    ;

    /**
     * 默认长度
     */
    private static final int size = 16;

    /**
     * map集合
     */
    private static final Map<String, String> map = new HashMap<String, String>(size){{
        for(FastGroupNameEnum groupNameEnum : FastGroupNameEnum.values()){
            put(groupNameEnum.getGroupName(), groupNameEnum.getGroupName());
        }
    }};

    public static Map<String, String> getMaps(){
        return map;
    }

    /**
     * 组名
     */
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    FastGroupNameEnum(String groupName) {
        this.groupName = groupName;
    }

}
