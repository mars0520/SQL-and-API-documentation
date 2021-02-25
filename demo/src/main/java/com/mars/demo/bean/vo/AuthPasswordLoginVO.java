package com.mars.demo.bean.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @description 密码登录
 * @author xzp
 * @date 2020/5/13 9:02
 **/
public class AuthPasswordLoginVO implements Serializable {

    @NotBlank(message = "登录账号不能为空")
    @Size(min = 1, max = 16, message = "登录账号长度在1-16之间")
    @ApiModelProperty(value = "登录账号")
    private String account;

    @NotBlank(message = "登录密码不能为空")
    @Size(min = 6, max = 16, message = "登录密码长度在6-16之间")
    @ApiModelProperty(value = "登录密码")
    private String password;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String code;

    public String getAccount() {
        return account.trim();
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password.trim();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "AuthPasswordLoginVO{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
