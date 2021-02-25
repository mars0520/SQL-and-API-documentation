package com.mars.demo.bean.dto;

import java.io.Serializable;

/**
 * @description 密码、盐
 * @author xzp
 * @date 2020/12/29
 **/
public class UserPasswordDTO implements Serializable {

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    public UserPasswordDTO() {
    }

    public UserPasswordDTO(String password, String salt) {
        this.password = password;
        this.salt = salt;
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

    @Override
    public String toString() {
        return "UserPasswordDTO{" +
                "password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
