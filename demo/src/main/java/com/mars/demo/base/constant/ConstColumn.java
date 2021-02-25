package com.mars.demo.base.constant;

/**
 * @author xzp
 * @description 常量列
 * @date 2021/1/4
 **/
public class ConstColumn {

    /**
     * 测试
     */
    public static final String TEST_COLUMN = "name, sex, age";

    /**
     * 用户
     */
    public static final String COLUMN_USER = "id, uuid, account, status, name, birthday, telephone, email";

    /**
     * 角色
     */
    public static final String COLUMN_ROLE = "id, uuid, name, status";

    /**
     * 菜单
     */
    public static final String COLUMN_MENU = "id, uuid, parent_uuid AS parentUuid, name, auth_code AS authCode, sort, icon, status";

}
