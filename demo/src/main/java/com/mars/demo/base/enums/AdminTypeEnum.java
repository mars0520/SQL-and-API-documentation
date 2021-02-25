package com.mars.demo.base.enums;

import com.mars.demo.base.exception.ParamExceptionHandling;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 管理类型
 * @author xzp
 * @date 2020/12/31
 **/
public enum AdminTypeEnum {

    USER_ADMIN_TYPE_ENUM(1, "用户"),
    ROLE_ADMIN_TYPE_ENUM(2, "角色"),
    MENU_ADMIN_TYPE_ENUM(3, "菜单权限"),
    ;

    /**
     * 值列表
     */
    private static final List<Integer> enums = new ArrayList<Integer>(){{ for (AdminTypeEnum e : AdminTypeEnum.values()){ add(e.getValue()); } }};

    public static List<Integer> getEnums() {
        return enums;
    }

    /**
     * 验证参数是否满足枚举(switch)
     */
    public static <T extends AdminTypeEnum>T getEnumByType(Integer type, Class<T> enumClass){
        for(T t :enumClass.getEnumConstants()){
            if(t.getValue().equals(type)){ return t; }
        }
        throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50013.getCode(), "管理类型".concat(ReturnMessageType.MSG_FAIL_50013.getMsg()));
    }

    /**
     * 类型
     */
    private Integer value;

    /**
     * 备注
     */
    private String note;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    AdminTypeEnum() {
    }

    AdminTypeEnum(Integer value, String note) {
        this.value = value;
        this.note = note;
    }
}
