package com.mars.demo.bean.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.List;

/**
 * @description 系统角色表
 * @author xzp
 * @date 2020/12/31
 **/
public class SysRole implements Serializable {

    private static final long serialVersionUID = -42203237684771173L;

    public interface SaveEntity extends Default {}

    public interface ModifyEntity extends Default{}

    @ApiModelProperty(value = "自增ID")
    private Integer id;

    @ApiModelProperty(value = "角色标识")
    private String uuid;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色状态")
    private Integer status;

    @ApiModelProperty(value = "菜单ID,逗号隔开")
    private String menuId;

    private List<SysMenu> menuList;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SysMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SysMenu> menuList) {
        this.menuList = menuList;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
