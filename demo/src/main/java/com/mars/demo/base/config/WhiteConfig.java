package com.mars.demo.base.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description 白名单配置
 * @author xzp
 * @date 2019/11/6 6:33 下午
 **/
@Component
@Configurable
@ConfigurationProperties(prefix = "mars.whiteurl")
public class WhiteConfig {

    /**
     * 登录后操作的白名单url
     */
    private List<String> whiteUrlWithLogin;

    /**
     * 登录后操作的黑名单url
     */
    private List<String> blackUrlWithLogin;

    public List<String> getWhiteUrlWithLogin() {
        return whiteUrlWithLogin;
    }

    public void setWhiteUrlWithLogin(List<String> whiteUrlWithLogin) {
        this.whiteUrlWithLogin = whiteUrlWithLogin;
    }

    public List<String> getBlackUrlWithLogin() {
        return blackUrlWithLogin;
    }

    public void setBlackUrlWithLogin(List<String> blackUrlWithLogin) {
        this.blackUrlWithLogin = blackUrlWithLogin;
    }
}
