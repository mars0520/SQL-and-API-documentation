package com.mars.demo.bean.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.groups.Default;
import java.io.Serializable;

/**
 * @description 菜单权限表
 * @author xzp
 * @date 2020/12/31
 **/
public class SysMenu implements Serializable {

    private static final long serialVersionUID = -42203237684771173L;

    public interface SaveEntity extends Default {}

    public interface ModifyEntity extends Default{}

    @ApiModelProperty(value = "自增ID")
    private Integer id;

    @ApiModelProperty(value = "菜单标识")
    private String uuid;

    @ApiModelProperty(value = "父ID")
    private String parentUuid;

    @ApiModelProperty(value = "菜单/权限")
    private Integer category;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "权限/路径")
    private String authCode;

    @ApiModelProperty(value = "序号")
    private String sort;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "状态")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
