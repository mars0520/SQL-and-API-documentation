package com.mars.demo.base.constant;

import com.mars.demo.base.enums.ReturnMessageType;
import com.mars.demo.base.exception.ParamExceptionHandling;
import com.mars.demo.bean.dto.UserMenusDTO;
import com.mars.demo.bean.dto.UserPasswordDTO;
import com.mars.demo.bean.entity.AuthUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @description 获取登录后的用户信息
 * @author xzp
 * @date 2020/1/16 3:54 下午
 **/
public class GlobalUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 获取32位UUID
     * @author xzp
     * @date 2020/6/30 16:07
     */
    public static String getRandomUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取默认密码123456
     * @author xzp
     * @date 2020/6/30 16:09
     */
    public static UserPasswordDTO getDefaultPassword(){
        String salt = getRandomUUID(), password = new SimpleHash("MD5", "123456", salt, 1).toString();
        return new UserPasswordDTO(password, salt);
    }

    /**
     * 根据原始密码获取新密码
     * @author xzp
     * @date 2020/6/30 16:09
     * @param password:原始密码
     */
    public static UserPasswordDTO getNewPassword(String password){
        String salt = getRandomUUID(), newPassword = new SimpleHash("MD5", password, salt, 1).toString();
        return new UserPasswordDTO(newPassword, salt);
    }

    /**
     * 获取用户信息
     * @author xzp
     * @date 2020/6/30 3:57 下午
     */
    public static AuthUser getUserInfo() {
        AuthUser authUser = (AuthUser) SecurityUtils.getSubject().getPrincipal();
        if(null != authUser){ return authUser; }
        throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50006);
    }

    /**
     * 获取用户状态
     * @author xzp
     * @date 2020/6/30 3:57 下午
     */
    public static Integer getUserStatus(){
        return getUserInfo().getStatus();
    }

    /**
     * 获取用户UUID
     * @author xzp
     * @date 2020/6/30 3:57 下午
     */
    public static String getUserUUId(){
        return getUserInfo().getUuid();
    }

    /**
     * 获取用户角色ID
     * @author xzp
     * @date 2020/6/30 3:57 下午
     */
    public static String getUserRoleId(){
        return getUserInfo().getRoleId();
    }

    /**
     * 获取菜单
     * @author xzp
     * @date 2020/6/30 3:57 下午
     */
    public static List<UserMenusDTO> getMenus(){
        return (List<UserMenusDTO>) SecurityUtils.getSubject().getSession().getAttribute("menus");
    }

    /**
     * 获取权限列表
     * @author xzp
     * @date 2020/6/30 3:57 下午
     */
    public static List<String> getPermissions() {
        return (List<String>) SecurityUtils.getSubject().getSession().getAttribute("permissions");
    }

    /**
     * 获取ftp下载权限列表
     * @author xzp
     * @date 2020/6/30 3:57 下午
     */
    public static Set<String> getFtpDownloadList() {
        return (Set<String>) SecurityUtils.getSubject().getSession().getAttribute("FtpDownloadList");
    }

    /**
     * 判断是否为超管
     * @author xzp
     * @date 2020/6/30 3:57 下午
     */
    public static boolean isSuperMan() {
        try{
            return (boolean) SecurityUtils.getSubject().getSession().getAttribute("isSuperMan");
        }catch (Exception e){
            return false;
        }
    }

}