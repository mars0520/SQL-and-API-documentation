package com.mars.demo.base.enums;

import com.mars.demo.base.exception.ParamExceptionHandling;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xzp
 * @description 通用状态 启用/禁用
 * @date 2021/1/4
 **/
public enum BasicsStatusEnum {

    ENABLE(0),
    FORBIDDEN(1)
    ;

    /**
     * 值列表
     */
    private static final List<Integer> enums = new ArrayList<Integer>(){{ for (BasicsStatusEnum e : BasicsStatusEnum.values()){ add(e.getValue()); } }};

    public static List<Integer> getEnums() {
        return enums;
    }

    /**
     * 验证参数是否满足枚举(switch)
     */
    public static <T extends BasicsStatusEnum>T getEnumByType(Integer type, Class<T> enumClass){
        for(T t :enumClass.getEnumConstants()){
            if(t.getValue().equals(type)){ return t; }
        }
        throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50013.getCode(), "用户状态".concat(ReturnMessageType.MSG_FAIL_50013.getMsg()));
    }

    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    BasicsStatusEnum(Integer value) {
        this.value = value;
    }
}
