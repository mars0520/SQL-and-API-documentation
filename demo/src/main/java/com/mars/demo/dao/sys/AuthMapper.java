package com.mars.demo.dao.sys;

import com.mars.demo.bean.dto.UserMenusDTO;
import com.mars.demo.bean.entity.AuthUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description 权限Mapper层接口
 * @author xzp
 * @date 2020/5/11 5:31 下午
 **/
@Component
public interface AuthMapper {

    /**
     * 根据用户账号询用户信息
     * @author xzp
     * @date 2020/5/13 9:28 上午
     * @param account 用户账号
     * @return AuthUser 用户信息
     */
    AuthUser queryUserInfo(@Param("account") String account);

    /**
     * 获取权限
     * @author xzp
     * @date 2020/6/30 15:02
     * @param roleId 角色UUID
     * @return [{authCode:权限标识}]
     */
    List<String> queryPermission(@Param("roleId") String roleId);

    /**
     * 获取菜单
     * @author xzp
     * @date 2020/6/30 15:02
     * @param roleId 角色UUID
     * @return UserMenusDTO
     */
    List<UserMenusDTO> queryMenu(@Param("roleId") String roleId);

}
