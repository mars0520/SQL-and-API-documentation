package com.mars.demo.base.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author supeng
 * @version 1.0
 * @Description 项目自身属性配置
 * @date 2019/12/6 15:05
 */
@SuppressWarnings("WeakerAccess")
@Configuration
@ConfigurationProperties(prefix = "ftpconfig")
public class CommonConfigProperties {

    /**
     * 账号
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * IP
     */
    private String ip;
    /**
     * 端口
     */
    private int port;
    /**
     * 文件存放的目录
     */
    private String filepath;

    public CommonConfigProperties() {
    }

    public CommonConfigProperties(String userName, String password, String ip, int port, String filepath) {
        this.userName = userName;
        this.password = password;
        this.ip = ip;
        this.port = port;
        this.filepath = filepath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public String toString() {
        return "CommonConfigProperties{" +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", filepath='" + filepath + '\'' +
                '}';
    }
}