package com.mars.demo.service.sys;

import com.github.pagehelper.PageInfo;
import com.mars.demo.bean.dto.UserMenusDTO;
import com.mars.demo.bean.entity.AuthUser;
import com.mars.demo.bean.entity.SysMenu;
import com.mars.demo.bean.entity.SysRole;
import com.mars.demo.bean.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @description 管理服务
 * @author xzp
 * @date 2020/4/30 12:05
 **/
public interface AdminService {

    /**
     * 保存用户
     * @author xzp
     * @date 2020/12/31 10:11
     * @param sysUser 用户对象
     * @return 用户对象
     */
    void saveUser(SysUser sysUser);

    /**
     * 修改用户密码
     * @author xzp
     * @date 2020/12/31 10:11
     * @param sysUser 用户对象
     * @return 用户对象
     */
    void updatePassword(SysUser sysUser);

    /**
     * 保存角色
     * @author xzp
     * @date 2020/12/31 10:11
     * @param sysRole 角色对象
     * @return 角色对象
     */
    void saveRole(SysRole sysRole);

    /**
     * 保存菜单
     * @author xzp
     * @date 2020/12/31 10:11
     * @param sysMenu 菜单对象
     * @return 菜单对象
     */
    void saveMenu(SysMenu sysMenu);

    /**
     * 根据类型删除
     * @author xzp
     * @date 2020/12/31 10:11
     * @param type 枚举状态
     * @param id 主键
     */
    void remove(Integer type, Integer id);

    /**
     * 根据类型查看详情
     * @author xzp
     * @date 2020/12/31 10:11
     * @param type 枚举状态
     * @param id 主键
     * @return Map
     */
    Map getInfo(Integer type, Integer id);

    /**
     * 获取用户列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return SysUser
     */
    PageInfo listUser(Integer currentPage, Integer pageSize);

    /**
     * 获取角色列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return SysRole
     */
    PageInfo listRole(Integer currentPage, Integer pageSize);

    /**
     * 获取菜单权限列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @param category 分类(0菜单/1权限)
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return SysMenu
     */
    PageInfo listMenu(Integer category, Integer currentPage, Integer pageSize);

    /**
     * 获取用户角色联动列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return SysUser
     */
    PageInfo listUserRole(Integer currentPage, Integer pageSize);

    /**
     * 获取角色菜单权限联动列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @param category 分类(0菜单/1权限)
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return SysRole
     */
    PageInfo listRoleMenu(Integer category, Integer currentPage, Integer pageSize);

}
