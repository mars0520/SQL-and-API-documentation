package com.mars.demo.service;

import com.mars.demo.base.constant.ConstColumn;
import com.mars.demo.base.constant.CreateDataConst;
import com.mars.demo.base.enums.ReturnMessageType;
import com.mars.demo.base.exception.ParamExceptionHandling;
import com.mars.demo.dao.BaseMapper;
import com.mars.demo.dao.sys.AuthMapper;
import com.mars.demo.dao.sys.AdminMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @description 统一引用
 * @author xzp
 * @date 2020/12/28
 **/
public class BaseService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired protected SessionDAO sessionDAO;

    @Autowired protected StringRedisTemplate stringRedisTemplate;


    @Autowired protected AuthMapper authMapper;

    @Autowired protected AdminMapper adminMapper;

    @Autowired protected BaseMapper baseMapper;

    /**
     * 新增验证单个条件是否存在
     * @author xzp
     * @date 2021/1/4 11:32
     * @param tableName 表名
     * @param column 列名
     * @param value 值
     * @param isSave 是否新增
     * @return
     */
    protected void saveUniqueKey(boolean isSave, String tableName, String column, String value){
        Integer number = getCount(tableName, column, value);
        saveUniqueKey(number, isSave);
    }

    /**
     * 新增验证多个条件是否存在
     * 所有条件满足一即可
     * @author xzp
     * @date 2021/1/4 11:32
     * @param tableName 表名
     * @param map 列名 值
     * @param isSave 是否新增
     * @return
     */
    protected void saveUniqueKeyOr(boolean isSave, String tableName, Map map){
        Integer number = getCountByMap(tableName, map);
        saveUniqueKey(number, isSave);
    }

    /**
     * 新增验证多个条件是否存在
     * 所有条件都要满足
     * @author xzp
     * @date 2021/1/4 11:32
     * @param tableName 表名
     * @param map 列名 值
     * @param isSave 是否新增
     * @return
     */
    protected void saveUniqueKeyAnd(boolean isSave, String tableName, Map map){
        Integer number = getCountByMap(tableName, map);
        saveUniqueKey(number, isSave);
    }

    /**
     * 公共方法
     * 根据数量判断新增修改异常
     * @author xzp
     * @date 2021/1/14 11:26
     * @param number 数量
     * @param isSave 是否新增
     * @return
     */
    private void saveUniqueKey(Integer number, boolean isSave){
        boolean isThrow = (isSave && null != number) || (!isSave && null != number && number > 1);
        if(isThrow){
            throw new ParamExceptionHandling(ReturnMessageType.MSG_SUCCESS_20001);
        }
    }

    /**
     * 根据单个条件查询是否存在
     * @author xzp
     * @date 2021/1/12 09:43
     * @param tableName 表名
     * @param column 列名
     * @param value 值
     * @return
     */
    protected Integer getCount(String tableName, String column, Object value){
        boolean isString = value instanceof String;
        StringBuffer columnValue = new StringBuffer();
        columnValue.append(column).append(" = ").append(isString ? "\' " + value + " \'" : value);
        return baseMapper.getCount(tableName, columnValue.toString());
    }

    /**
     * 根据多个条件查询是否存在
     * @author xzp
     * @date 2021/1/12 09:43
     * @param tableName 表名
     * @param map 列名 值
     * @return
     */
    protected Integer getCountByMap(String tableName, Map map){
        if(null == map || map.isEmpty()){
            throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50005);
        }
        StringBuffer columnValue = new StringBuffer();
        map.forEach((key, value) -> {
            boolean isString = value instanceof String;
            columnValue.append(key).append(" = ").append(isString ? "\' " + value + " \'" : value).append(",");
        });
        return baseMapper.getCount(tableName, columnValue.substring(0, columnValue.length() - 1));
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
     * 例: createTestData("表名", ConstColumn.TEST_COLUMN, CreateDataConst.TEST_VALUE, CreateDataConst.TEST_TYPE);
     */
    protected boolean createData(String tableName, String column, Map<String, Object> columnValue, Map<String, String> columnType){
        boolean isSuccess = false,
                isColumnValue = columnValue == null || columnValue.isEmpty(),
                iSColumnType = columnType == null || columnType.isEmpty();
        if(isColumnValue || iSColumnType){
            return false;
        }
        try{
            StringBuffer values = new StringBuffer();
            //列集合
            List<String> columns = Arrays.asList(column.split(","));
            Integer columnSize = columns.size(), columnSize2 = columnSize -1;
            //遍历数据条数
            for(int i = 1; i <= 500; i ++){
                values.append("(");
                //遍历列,开始拼接
                for(int c = 0; c < columnSize; c ++){
                    //列名
                    String col = columns.get(c).trim();
                    if("RandomArray".equals(columnType.get(col))){
                        Object[] objects = (Object[]) columnValue.get(col);
                        Integer objectLength = objects.length;
                        values.append(objects[new Random().nextInt(objectLength)]);
                    }else {
                        Object value = columnValue.get(col);
                        if(null == value){
                            //当列没有默认值时,随机数填充
                            switch (columnType.get(col)){
                                case "String":
                                    values.append("\'").append(RandomStringUtils.randomAlphanumeric(2)).append("\'");
                                    break;
                                case "Integer":
                                    values.append(new Random().nextInt(99));
                                    break;
                                default: break;
                            }
                        }else {
                            values.append("\'").append(value).append("\'");
                        }
                    }
                    if(c < columnSize2){
                        values.append(",");
                    }
                }
                values.append("),");
            }
            String str = values.substring(0, values.length() - 1);
            logger.info("创建测试数据值:" + str);
            baseMapper.createData(tableName, column, str);
            isSuccess = true;
        }catch (Exception e){
            logger.error("创建测试数据异常原因：", e);
        }
        return isSuccess;
    }

}
