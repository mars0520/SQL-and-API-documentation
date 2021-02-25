package com.mars.demo.dao;

import com.mars.demo.bean.dto.SqlWordDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description 基础dao
 * @author xzp
 * @date 2020/5/11 5:31 下午
 **/
@Component
public interface BaseMapper {

    /**
     * 根据表名和主键查询单条数据
     * @author xzp
     * @date 2020/12/31 10:11
     * @param tableName 表名
     * @param column 列
     * @param id 主键
     * @return SysMenu
     */
    Map getInfoByTableName(@Param("tableName") String tableName, @Param("column") String column, @Param("id") Integer id);

    /**
     * 根据表名和主键删除
     * @author xzp
     * @date 2020/12/31 10:11
     * @param tableName 表名
     * @param id 主键
     * @return SysMenu
     */
    void removeByTableName(@Param("tableName") String tableName, @Param("id") Integer id);

    /**
     * 根据表名和uuid删除
     * @author xzp
     * @date 2020/12/31 10:11
     * @param tableName 表名
     * @param column 列名
     * @param uuid uuid多个逗号隔开
     * @return SysMenu
     */
    void removeByTableNameIn(@Param("tableName") String tableName, @Param("column") String column, @Param("uuid") String uuid);

    /**
     * 根据表名查看字段是否存在
     * @author xzp
     * @date 2021/1/4 11:28
     * @param tableName 表名
     * @param columnValue 列名和值 例： id = 1,name = '张三'
     * @return
     */
    Integer getCount(@Param("tableName") String tableName, @Param("columnValue") String columnValue);

    /**
     * 根据uuid查询ID
     * @author xzp
     * @date 2021/1/4 12:25
     * @param tableName 表名
     * @param uuid uuid
     * @return
     */
    Integer getIdByUuId(@Param("tableName") String tableName, @Param("uuid") String uuid);

    /**
     * 根据uuid查询ID
     * @author xzp
     * @date 2021/1/4 12:25
     * @param tableName 表名
     * @param uuid uuid多个逗号隔开
     * @return
     */
    List<Integer> getIdsByUuId(@Param("tableName") String tableName, @Param("uuid") String uuid);

    /**
     * 根据表名获取列信息
     * @author xzp
     * @date 2021/2/25 17:00
     * @param tableName 表名
     * @return
     */
    List<SqlWordDTO> getColumnInfoByTableName(@Param("tableName") String tableName);

    /**
     * 获取所有表
     * @author xzp
     * @date 2021/2/25 17:34
     * @return
     */
    List<String> getTableName();

    /**
     * 创建测试数据
     * @author xzp
     * @date 2021/1/12 09:42
     * @param tableName 表名
     * @param column 列
     * @param values 多条数据值(),(),()
     * @return
     */
    void createData(@Param("tableName") String tableName, @Param("column") String column, @Param("values") String values);

}
