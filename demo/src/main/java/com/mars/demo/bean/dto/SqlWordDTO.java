package com.mars.demo.bean.dto;

/**
 * @author xzp
 * @description
 * @date 2021/2/4
 **/
public class SqlWordDTO {

    /**
     * 表名(中文说明)
     */
    private String tableName;
    /**
     * 序号
     */
    private Integer number;
    /**
     * 字段编码
     */
    private String code;
    /**
     * 字段名称
     */
    private String name;
    /**
     * 是否主键
     */
    private String isPrimary;
    /**
     * 类型
     */
    private String type;
    /**
     * 小数位数
     */
    private Integer decimal;
    /**
     * 是否允许为空
     */
    private String isNull;
    /**
     * 默认值
     */
    private String defaultValue;

    @Override
    public String toString() {
        return "WordTableVO{" +
                "tableName='" + tableName + '\'' +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", isPrimary='" + isPrimary + '\'' +
                ", type='" + type + '\'' +
                ", decimal=" + decimal +
                ", isNull='" + isNull + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }

    public SqlWordDTO() {
    }

    public SqlWordDTO(String tableName, Integer number, String code, String name, String isPrimary, String type, Integer decimal, String isNull, String defaultValue) {
        this.tableName = tableName;
        this.number = number;
        this.name = name;
        this.code = code;
        this.isPrimary = isPrimary;
        this.type = type;
        this.decimal = decimal;
        this.isNull = isNull;
        this.defaultValue = defaultValue;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getNumber() {
        return String.valueOf(number);
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDecimal() {
        return String.valueOf(decimal);
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

}
