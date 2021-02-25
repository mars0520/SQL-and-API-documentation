package com.mars.demo.dao.sys;

import com.mars.demo.bean.dto.UserMenusDTO;
import com.mars.demo.bean.entity.AuthUser;
import com.mars.demo.bean.entity.SysMenu;
import com.mars.demo.bean.entity.SysRole;
import com.mars.demo.bean.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @description 管理Mapper层接口
 * @author xzp
 * @date 2020/5/11 5:31 下午
 **/
@Component
public interface AdminMapper {

    /**
     * 保存用户
     * @author xzp
     * @date 2020/12/31 10:11
     * @param sysUser
     * @return SysUser
     */
    void saveUser(SysUser sysUser);

    /**
     * 保存角色
     * @author xzp
     * @date 2020/12/31 10:11
     * @param sysRole
     * @return SysRole
     */
    void saveRole(SysRole sysRole);

    /**
     * 保存菜单
     * @author xzp
     * @date 2020/12/31 10:11
     * @param sysMenu
     * @return SysMenu
     */
    void saveMenu(SysMenu sysMenu);

    /**
     * 获取用户列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @return SysUser
     */
    List<SysUser> listUser();

    /**
     * 获取角色列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @return SysRole
     */
    List<SysRole> listRole();

    /**
     * 获取菜单列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @param category 分类(0菜单/1权限)
     * @return SysMenu
     */
    List<SysMenu> listMenu(@Param("category") Integer category);

    /**
     * 获取用户角色联动列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @return SysUser
     */
    List<SysUser> listUserRole();

    /**
     * 获取角色菜单权限联动列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @param category 分类(0菜单/1权限)
     * @return SysRole
     */
    List<SysRole> listRoleMenu(@Param("category") Integer category);

}
