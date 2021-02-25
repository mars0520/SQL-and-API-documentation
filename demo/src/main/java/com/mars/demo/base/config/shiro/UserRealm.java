package com.mars.demo.base.config.shiro;

import com.mars.demo.base.constant.GlobalUserInfo;
import com.mars.demo.bean.entity.AuthUser;
import com.mars.demo.service.sys.AuthService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description shiro配置
 * @author xzp
 * @date 2019/11/6 6:33 下午
 **/
public class UserRealm extends AuthorizingRealm {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired private AuthService authService;

    /**
     * @description 授权
     * @author xzp
     * @date 2020/5/7 17:18
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<String> sysPermissions = GlobalUserInfo.getPermissions();
        if (null != sysPermissions) {
            info.addStringPermissions(sysPermissions);
        }
        return info;
    }

    /**
     * @description 认证
     * @author xzp
     * @date 2020/5/7 17:18
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        AuthUser user = authService.getUserInfo(token.getUsername());
        if (null == user) {
            logger.error(token.getUsername() + "-无此账号");
            return null;
        }
        logger.info(user.getAccount() + "-正在认证");
        return new SimpleAuthenticationInfo(user, user.getPassword().toCharArray(), ByteSource.Util.bytes(user.getSalt()), getName());
    }

}
