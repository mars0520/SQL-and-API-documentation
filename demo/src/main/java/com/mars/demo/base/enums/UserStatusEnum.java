package com.mars.demo.base.enums;

import com.mars.demo.base.exception.ParamExceptionHandling;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 用户状态
 * @author xzp
 * @date 2020/12/29
 **/
public enum UserStatusEnum {

    NORMAL_USER_STATUS_ENUM(0, "正常"),
    DISABLE_USER_STATUS_ENUM(1, "禁用"),
    FREEZE_USER_STATUS_ENUM(2, "冻结"),

    ;

    /**
     * 值列表
     */
    private static final List<Integer> enums = new ArrayList<Integer>(){{ for (UserStatusEnum e : UserStatusEnum.values()){ add(e.getValue()); } }};

    public static List<Integer> getEnums() {
        return enums;
    }

    /**
     * 验证参数是否满足枚举(switch)
     */
    public static <T extends UserStatusEnum>T getEnumByType(Integer type, Class<T> enumClass){
        for(T t :enumClass.getEnumConstants()){
            if(t.getValue().equals(type)){ return t; }
        }
        throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50013.getCode(), "用户状态".concat(ReturnMessageType.MSG_FAIL_50013.getMsg()));
    }

    /**
     * 状态值
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

    UserStatusEnum(Integer value, String note) {
        this.value = value;
        this.note = note;
    }

}
