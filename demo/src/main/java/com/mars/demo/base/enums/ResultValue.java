package com.mars.demo.base.enums;

/**
 * @description 响应结果
 * @author xzp
 * @date 2020/4/30 13:39
 */
public enum ResultValue {

    SUCCESS(true),
    ERROR(false);

    private boolean value;

    ResultValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

}
