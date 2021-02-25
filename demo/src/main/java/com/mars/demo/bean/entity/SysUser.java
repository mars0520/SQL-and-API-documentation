package com.mars.demo.bean.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.List;

/**
 * @description 系统用户表(SysUser)实体类
 * @author xzp
 * @date 2020-12-28 17:35:30
 */
public class SysUser implements Serializable {

    private static final long serialVersionUID = -42203237684771173L;
    
    public interface SaveEntity extends Default {}

    public interface ModifyEntity extends Default{}
    
    @ApiModelProperty(value = "自增ID")
    private Integer id;
    
    @ApiModelProperty(value = "用户标识")
    private String uuid;

    @NotBlank(message = "登录账号不能为空", groups = {SysMenu.ModifyEntity.class})
    @Size(min = 6, max = 32, message = "登录账号长度6-32")
    @ApiModelProperty(value = "登录账号")
    private String account;

    @NotBlank(message = "登录密码不能为空", groups = {SysMenu.ModifyEntity.class})
    @Size(min = 6, max = 32, message = "登录密码长度6-32")
    @ApiModelProperty(value = "密码")
    private String password;
    
    @ApiModelProperty(value = "密码盐")
    private String salt;
    
    @ApiModelProperty(value = "用户状态：0正常/1禁用/2冻结")
    private Integer status;

    @NotBlank(message = "姓名不能为空", groups = {SysMenu.ModifyEntity.class})
    @Size(min = 1, max = 32, message = "姓名长度1-32")
    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "出生日期")
    private String birthday;

    @ApiModelProperty(value = "手机号码")
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "角色ID,逗号隔开")
    private String roleId;

    private List<SysRole> roleList;

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}