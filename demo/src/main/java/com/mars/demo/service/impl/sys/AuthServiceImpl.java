package com.mars.demo.service.impl.sys;

import com.mars.demo.base.constant.GlobalUserInfo;
import com.mars.demo.bean.dto.UserMenusDTO;
import com.mars.demo.bean.entity.AuthUser;
import com.mars.demo.service.sys.AuthService;
import com.mars.demo.service.BaseService;
import com.mars.demo.util.trees.MenuTreeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @description 认证服务实现
 * @author xzp
 * @date 2020/5/12 6:20 下午
 **/
@Service("AuthService")
public class AuthServiceImpl extends BaseService implements AuthService {

    /**
     * 根据用户账号询用户信息
     * @author xzp
     * @date 2020/5/13 9:28 上午
     * @param  account 用户账号
     * @return AuthUser
     */
    @Override
    public AuthUser getUserInfo(String account) { return authMapper.queryUserInfo(account); }

    /**
     * 获取菜单(缓存)
     * @author xzp
     * @date 2020/6/30 15:40
     * @return UserMenusDTO
     */
    @Override
    public List<UserMenusDTO> getMenus(){
        List<UserMenusDTO> menus = GlobalUserInfo.getMenus();
        if(null == menus || menus.isEmpty()){
            menus = getMenusNotCache();
        }
        return menus;
    }

    /**
     * 获取菜单(查库)
     * @author xzp
     * @date 2020/12/30 14:49
     * @return UserMenusDTO
     */
    @Override
    public List<UserMenusDTO> getMenusNotCache(){
        String roleId = GlobalUserInfo.getUserRoleId();
        if(null == roleId){
            return null;
        }
        List<UserMenusDTO> menus = MenuTreeUtil.getMenus(authMapper.queryMenu(roleId));
        SecurityUtils.getSubject().getSession().setAttribute("menus", menus);
        SecurityUtils.getSubject().getSession().setAttribute("permissions", authMapper.queryPermission(roleId));
        return menus;
    }

    /**
     * 删除用户SESSION
     * @author xzp
     * @date 2020/7/6 9:52
     * @param  uuid:用户UUID
     */
    @Override
    public void removeSessionById(String uuid, String sessionId){
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) != null) {
                SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                AuthUser authUser = (AuthUser) principalCollection.getPrimaryPrincipal();
                if (uuid.equals(authUser.getUuid()) && sessionId.equals(session.getId())) {
                    Session deleteSession = sessionDAO.readSession(session.getId());
                    sessionDAO.delete(deleteSession);
                }
            }
        }
    }

    /**
     * 获取在线用户
     * @author xzp
     * @date 2020/7/20 10:54
     * @return AuthUser
     */
    @Override
    public List<AuthUser> getOnlineUser(){
        List<AuthUser> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) != null) {
                SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                AuthUser authUser = (AuthUser) principalCollection.getPrimaryPrincipal();
                authUser.setPassword(null);
                authUser.setSalt(null);
                authUser.setSessionId(session.getId().toString());
                authUser.setSessionIp(session.getHost());
                list.add(authUser);
            }
        }
        return list;
    }

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
    @Override
    public boolean createTestData(String tableName, String column, Map<String, Object> columnValue, Map<String, String> columnType){
        return createData(tableName, column, columnValue, columnType);
    }

}
