package com.mars.demo.util.trees;

import com.mars.demo.base.constant.SysConst;
import com.mars.demo.bean.dto.UserMenusDTO;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description树形菜单
 * @author xzp
 * @date 2019/10/9 10:35 上午
 **/
public class MenuTreeUtil {

    /**
     * 初始化菜单列表(用于子级遍历)
     */
    public static List<UserMenusDTO> initializeMenus;

    /**
     * 获取父子层级菜单
     * @author xzp
     * @date 2020/12/30 14:35
     */
    public static List<UserMenusDTO> getMenus(List<UserMenusDTO> list) {
        List<UserMenusDTO> menus = new ArrayList<>();
        initializeMenus = list;
        for (UserMenusDTO userMenus : list) {
            if (SysConst.MENU_LEVEL_1.equals(userMenus.getParentUuid())) {
                userMenus.setChildren(getMenuChildren(userMenus.getUuid()));
                menus.add(userMenus);
            }
        }
        return menus;
    }

    /**
     * 根据code获取子级
     * @author xzp
     * @date 2020/12/30 14:35
     */
    public static List<UserMenusDTO> getMenuChildren(String code) {
        List<UserMenusDTO> list = new ArrayList<>();
        for (UserMenusDTO userMenus : initializeMenus) {
            if (code.equals(userMenus.getParentUuid())) {
                userMenus.setChildren(getMenuChildren(userMenus.getUuid()));
                list.add(userMenus);
            }
        }
        return list;
    }

}

