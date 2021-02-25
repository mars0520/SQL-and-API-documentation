package com.mars.demo.service.sys;

import com.mars.demo.bean.dto.UserMenusDTO;
import com.mars.demo.bean.entity.AuthUser;

import java.util.List;
import java.util.Map;

/**
 * @description 认证服务
 * @author xzp
 * @date 2020/4/30 12:05
 **/
public interface AuthService {

    /**
     * 根据用户账号询用户信息
     * @author xzp
     * @date 2020/5/13 9:28 上午
     * @param account 用户账号
     * @return AuthUser 用户信息
     */
    AuthUser getUserInfo(String account);

    /**
     * 获取菜单
     * @author xzp
     * @date 2020/6/30 15:02
     * @return UserMenusDTO
     */
    List<UserMenusDTO> getMenus();

    /**
     * 剔除用户
     * @author xzp
     * @date 2020/12/29 15:19
     * @param userId 用户ID
     * @param sessionId sessionID
     */
    void removeSessionById(String userId, String sessionId);

    /**
     * 获取在线用户
     * @author xzp
     * @date 2020/12/29 15:19
     * @return AuthUser对象
     */
    List<AuthUser> getOnlineUser();

    /**
     * 获取菜单(查库)
     * @author xzp
     * @date 2020/12/30 14:49
     * @return UserMenusDTO
     */
    List<UserMenusDTO> getMenusNotCache();

    /**
     * 根据表名字段创建数据
     * @author xzp
     * @date 2021/1/12 09:49
     * @param tableName 表名
     * @param column 列名
     * @param columnValue 列默认值 例：sex = '男' 注意:无默认值的统一生成随机数
     * @param columnType 列类型,根据类型默认值填充随机数(默认字符), 注:value = 'Array' 根据 columnValue对应的数组获取随机数
     * @return
     */
    boolean createTestData(String tableName, String column, Map<String, Object> columnValue, Map<String, String> columnType);

}
