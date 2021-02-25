package com.mars.demo.bean.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @description 用户菜单
 * @author xzp
 * @date 2020/12/29
 **/
public class UserMenusDTO implements Serializable {

    /**
     * 菜单ID
     */
    private Integer id;

    /**
     * 菜单UUID
     */
    private String uuid;

    /**
     * 父级UUID
     */
    private String parentUuid;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 权限标识/路径
     */
    private String authCode;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 图标路径
     */
    private String icon;

    /**
     * 子集
     */
    private List<UserMenusDTO> children;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<UserMenusDTO> getChildren() {
        return children;
    }

    public void setChildren(List<UserMenusDTO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "UserMenusDTO{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", parentUuid='" + parentUuid + '\'' +
                ", name='" + name + '\'' +
                ", authCode='" + authCode + '\'' +
                ", sort=" + sort +
                ", icon='" + icon + '\'' +
                ", children=" + children +
                '}';
    }
}
