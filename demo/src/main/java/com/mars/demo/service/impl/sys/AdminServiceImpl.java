package com.mars.demo.service.impl.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mars.demo.base.constant.ConstColumn;
import com.mars.demo.base.constant.GlobalUserInfo;
import com.mars.demo.base.enums.AdminTypeEnum;
import com.mars.demo.base.enums.ReturnMessageType;
import com.mars.demo.base.exception.ParamExceptionHandling;
import com.mars.demo.bean.dto.UserPasswordDTO;
import com.mars.demo.bean.entity.SysMenu;
import com.mars.demo.bean.entity.SysRole;
import com.mars.demo.bean.entity.SysUser;
import com.mars.demo.service.BaseService;
import com.mars.demo.service.sys.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 管理服务实现
 * @author xzp
 * @date 2020/5/12 6:20 下午
 **/
@Service("AdminService")
public class AdminServiceImpl extends BaseService implements AdminService {

    /**
     * 保存用户
     * @author xzp
     * @date 2020/12/31 10:11
     * @param sysUser
     * @return SysUser
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUser sysUser) {
        String password = null, salt = null;
        boolean isSave = null == sysUser.getId();
        saveUniqueKey(isSave,"sys_user", "account", sysUser.getAccount());
        if(isSave){
            UserPasswordDTO userPassword = GlobalUserInfo.getNewPassword(sysUser.getPassword());
            password = userPassword.getPassword();
            salt = userPassword.getSalt();
            sysUser.setUuid(GlobalUserInfo.getRandomUUID());
        }else {
            baseMapper.removeByTableNameIn("sys_user_role_relation", "user_uuid", sysUser.getRoleId());
        }
        sysUser.setPassword(password);
        sysUser.setSalt(salt);
        adminMapper.saveUser(sysUser);
    }

    /**
     * 修改用户密码
     * @author xzp
     * @date 2020/12/31 10:11
     * @param sysUser 用户对象
     * @return 用户对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(SysUser sysUser) {
        if(!GlobalUserInfo.isSuperMan() || !GlobalUserInfo.getUserInfo().getUserId().equals(sysUser.getId())){
            throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50014);
        }
        UserPasswordDTO userPassword = GlobalUserInfo.getNewPassword(sysUser.getPassword());
        sysUser.setPassword(userPassword.getPassword());
        sysUser.setSalt(userPassword.getSalt());
        adminMapper.saveUser(sysUser);
    }

    /**
     * 保存角色
     * @author xzp
     * @date 2020/12/31 10:11
     * @param sysRole
     * @return SysRole
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRole sysRole) {
        boolean isSave = null == sysRole.getId();
        saveUniqueKey(isSave,"sys_role", "name", sysRole.getName());
        if(isSave){
            sysRole.setUuid(GlobalUserInfo.getRandomUUID());
        }else {
            baseMapper.removeByTableNameIn("sys_role_menu_relation", "role_uuid", sysRole.getMenuId());
        }
        adminMapper.saveRole(sysRole);
    }

    /**
     * 保存菜单
     * @author xzp
     * @date 2020/12/31 10:11
     * @param sysMenu
     * @return SysMenu
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMenu(SysMenu sysMenu) {
        boolean isSave = null == sysMenu.getId();
        if(isSave){
            sysMenu.setUuid(GlobalUserInfo.getRandomUUID());
        }
        Map map = new HashMap(2){{
            put("name", sysMenu.getName());
            put("auth_code", sysMenu.getAuthCode());
        }};
        saveUniqueKeyOr(isSave,"sys_menu", map);
        adminMapper.saveMenu(sysMenu);
    }

    /**
     * 根据类型删除
     * @author xzp
     * @date 2020/12/31 10:11
     * @param type 枚举状态
     * @param id 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer type, Integer id) {
        String tableName = getTableNameByAdminType(type);
        baseMapper.removeByTableName(tableName, id);
    }

    /**
     * 根据类型查看详情
     * @author xzp
     * @date 2020/12/31 10:11
     * @param type 枚举状态
     * @param id 主键
     * @return Map
     */
    @Override
    public Map getInfo(Integer type, Integer id) {
        String tableName = getTableNameByAdminType(type), column;
        switch (AdminTypeEnum.getEnumByType(type, AdminTypeEnum.class)){
            case USER_ADMIN_TYPE_ENUM:
                column = ConstColumn.COLUMN_USER;
                break;
            case ROLE_ADMIN_TYPE_ENUM:
                column = ConstColumn.COLUMN_ROLE;
                break;
            case MENU_ADMIN_TYPE_ENUM:
                column = ConstColumn.COLUMN_MENU;
                break;
            default:
                logger.error(ReturnMessageType.MSG_FAIL_50013.getMsg());
                throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50013);
        }
        return baseMapper.getInfoByTableName(tableName, column, id);
    }

    /**
     * 根据管理类型获取表名
     * @author xzp
     * @date 2021/1/4 09:44
     * @param type 类型
     * @return tableName 表名
     */
    public String getTableNameByAdminType(Integer type){
        String tableName;
        switch (AdminTypeEnum.getEnumByType(type, AdminTypeEnum.class)){
            case USER_ADMIN_TYPE_ENUM:
                tableName = "sys_user";
                break;
            case ROLE_ADMIN_TYPE_ENUM:
                tableName = "sys_role";
                break;
            case MENU_ADMIN_TYPE_ENUM:
                tableName = "sys_menu";
                break;
            default:
                logger.error(ReturnMessageType.MSG_FAIL_50013.getMsg());
                throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50013);
        }
        return tableName;
    }

    /**
     * 获取用户列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return SysUser
     */
    @Override
    public PageInfo listUser(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return new PageInfo(adminMapper.listUser());
    }

    /**
     * 获取角色列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return SysRole
     */
    @Override
    public PageInfo listRole(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return new PageInfo(adminMapper.listRole());
    }

    /**
     * 获取菜单权限列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @param category 分类(0菜单/1权限)
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return SysMenu
     */
    @Override
    public PageInfo listMenu(Integer category, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return new PageInfo(adminMapper.listMenu(category));
    }

    /**
     * 获取用户角色联动列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return SysUser
     */
    @Override
    public PageInfo listUserRole(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return new PageInfo(adminMapper.listUserRole());
    }

    /**
     * 获取角色菜单权限联动列表
     * @author xzp
     * @date 2020/12/31 10:11
     * @param category 分类(0菜单/1权限)
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return SysRole
     */
    @Override
    public PageInfo listRoleMenu(Integer category, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return new PageInfo(adminMapper.listRoleMenu(category));
    }
}
