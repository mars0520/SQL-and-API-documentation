package com.mars.demo.util;

import com.mars.demo.base.enums.ReturnMessageType;
import com.mars.demo.base.enums.UserStatusEnum;
import com.mars.demo.base.exception.ParamExceptionHandling;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @description 参数检查工具类
 * @author xzp
 * @date 2020/12/3
 **/
public class ParamCheckUtil {

    private static Logger logger = LoggerFactory.getLogger(ParamCheckUtil.class);

    public static void main(String[] args) {
        //举例verificationEnums方法
        //正确案例
        listContains(UserStatusEnum.getEnums(), 0, "用户状态");
        //错误案例
        listContains(UserStatusEnum.getEnums(), 999, "用户状态");
    }

    /**
     * 验证是否符合正则表达式
     * @param regular 正则表达式(ConstRegularExpression常量获取)
     * @param value 值
     * @return boolean
     */
    public boolean verifyRegular(String regular, Object value){
        notNull(regular, "正则表达式");
        notNull(value, "正则表达式对应值");
        Pattern pattern = Pattern.compile(regular);
        return pattern.matcher(value.toString()).matches();
    }

    /**
     * 验证是否符合正则表达式,不满足抛异常
     * @param regular 正则表达式(ConstRegularExpression常量获取)
     * @param value 值
     */
    public void verifyRegularThrow(String regular, Object value){
        if(!verifyRegular(regular, value)){
            throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50016);
        }
    }

    /**
     * @description 打印日志抛出异常
     * @author xzp
     * @date 2020/12/30 10:10
     */
    public static void throwExceptionPrintLog(StringBuffer note){
        throwExceptionPrintLog(null != note ? note.toString() : null);
    }
    public static void throwExceptionPrintLog(String note){
        StringBuffer stringBuffer = new StringBuffer();
        if(null != note){
            stringBuffer.append(note).append("-");
        }
        stringBuffer.append(ReturnMessageType.MSG_FAIL_50005.getMsg());
        String str = stringBuffer.toString();
        logger.error(str);
        throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50005.getCode(), str);
    }

    /**
     * @description 判断集合是否包含参数
     * @author xzp
     * @date 2020/12/30 09:46
     * @param: list 枚举的getEnums()
     * @param: obj 具体参数
     * @param: note 说明
     */
    public static void listContains(List<?> list, Object obj, String note){
        listLsContains(false, list, obj, note);
    }
    public static void notListContains(List<?> list, Object obj, String note){
        listLsContains(true, list, obj, note);
    }
    private static void listLsContains(boolean is, List<?> list, Object obj, String note){
        notNull(list, "列表");
        notNull(obj, note);
        logger.info(new StringBuffer().append("列表:").append(list).append(",值:").append(obj).toString());
        if(list.contains(obj) == is){ throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50010); }
    }

    /**
     * 非空判断
     * 数值类型则非负数
     * @param value 值
     * @param note 说明
     */
    public static void notNull(String value, String note){
        if(StringUtils.isBlank(value)){
            throwExceptionPrintLog(note);
        }
    }
    public static void notNull(String value, String note, String value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(String value, String note, String value2, String note2, String value3, String note3){
        notNull(value, note, value2, note2);
        notNull(value3, note3);
    }
    public static void notNull(Integer value, String note){
        if(null == value || value < 0){
            throwExceptionPrintLog(note);
        }
    }
    public static void notNull(Integer value, String note, Integer value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(Integer value, String note, Integer value2, String note2, Integer value3, String note3){
        notNull(value, note, value2, note2);
        notNull(value3, note3);
    }
    public static void notNull(Double value, String note){
        if(null == value || value < 0){
            throwExceptionPrintLog(note);
        }
    }
    public static void notNull(Double value, String note, Double value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(Double value, String note, Double value2, String note2, Double value3, String note3){
        notNull(value, note, value2, note2);
        notNull(value3, note3);
    }
    public static void notNull(Short value, String note){
        if(null == value || value < 0){
            throwExceptionPrintLog(note);
        }
    }
    public static void notNull(Short value, String note, Short value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(Short value, String note, Short value2, String note2, Short value3, String note3){
        notNull(value, note, value2, note2);
        notNull(value3, note3);
    }
    public static void notNull(Byte value, String note){
        if(null == value || value < 0){
            throwExceptionPrintLog(note);
        }
    }
    public static void notNull(Byte value, String note, Byte value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(Byte value, String note, Byte value2, String note2, Byte value3, String note3){
        notNull(value, note, value2, note2);
        notNull(value3, note3);
    }
    public static void notNull(Character value, String note){
        if(null == value || value < 0){
            throwExceptionPrintLog(note);
        }
    }
    public static void notNull(Character value, String note, Character value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(Character value, String note, Character value2, String note2, Character value3, String note3){
        notNull(value, note, value2, note2);
        notNull(value3, note3);
    }
    public static void notNull(Float value, String note){
        if(null == value || value < 0){
            throwExceptionPrintLog(note);
        }
    }
    public static void notNull(Float value, String note, Float value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(Float value, String note, Float value2, String note2, Float value3, String note3){
        notNull(value, note, value2, note2);
        notNull(value3, note3);
    }
    public static void notNull(Long value, String note){
        if(null == value || value < 0){
            throwExceptionPrintLog(note);
        }
    }
    public static void notNull(Long value, String note, Long value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(Long value, String note, Long value2, String note2, Long value3, String note3){
        notNull(value, note, value2, note2);
        notNull(value3, note3);
    }
    public static void notNull(BigDecimal value, String note){
        if(null == value || value.compareTo(BigDecimal.ZERO) == -1){
            throwExceptionPrintLog(note);
        }
    }
    public static void notNull(BigDecimal value, String note, BigDecimal value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(BigDecimal value, String note, BigDecimal value2, String note2, BigDecimal value3, String note3){
        notNull(value, note, value2, note2);
        notNull(value3, note3);
    }
    public static void notNull(Object value, String note){
        if(null == value){
            throwExceptionPrintLog(note);
        }
    }
    public static void notNull(Object value, String note, Object value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(Object value, String note, Object value2, String note2, Object value3, String note3){
        notNull(value, note, value2, note2);
        notNull(value3, note3);
    }
    public static void notNull(Map value, String note){
        if(null == value){
            throwExceptionPrintLog(note);
        }
    }
    public static void notNull(Map value, String note, Map value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(Map value, String note, Map value2, String note2, Map value3, String note3){
        notNull(value, note, value2, note2);
        notNull(value3, note3);
    }
    public static void notNull(List value, String note){
        if(null == value){
            throwExceptionPrintLog(note);
        }
    }
    public static void notNull(List value, String note, List value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(List value, String note, List value2, String note2, List value3, String note3){
        notNull(value, note, value2, note2);
        notNull(value3, note3);
    }
    public static void notNull(Set value, String note){
        if(null == value){
            throwExceptionPrintLog(note);
        }
    }
    public static void notNull(Set value, String note, Set value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(Set value, String note, Set value2, String note2, Set value3, String note3){
        notNull(value, note, value2, note2);
        notNull(value3, note3);
    }

    /**
     * 非空且长度在i-i2范围内
     */
    public static void notNullInScope(String value, String note, Integer i, Integer i2){
        notNull(value, note);
        notNull(i, "范围i", i2, "范围i2");
        Integer length = value.length();
        boolean is = (i < i2 && (length < i || length > i2)) || (i > i2 && (length > i || length <i2));
        if(is){
            throwExceptionPrintLog(new StringBuffer().append(note).append("不在 ").append(i).append(" - ").append(i2).append( "范围内"));
        }
    }
    public static void notNullInScope(Integer value, String note, Integer i, Integer i2){
        notNull(value, note);
        notNullInScope(value.toString(), note, i, i2);
    }
    public static void notNullInScope(Double value, String note, Integer i, Integer i2){
        notNull(value, note);
        notNullInScope(value.toString(), note, i, i2);
    }
    public static void notNullInScope(Short value, String note, Integer i, Integer i2){
        notNull(value, note);
        notNullInScope(value.toString(), note, i, i2);
    }
    public static void notNullInScope(Float value, String note, Integer i, Integer i2){
        notNull(value, note);
        notNullInScope(value.toString(), note, i, i2);
    }
    public static void notNullInScope(BigDecimal value, String note, Integer i, Integer i2){
        notNull(value, note);
        notNullInScope(value.toString(), note, i, i2);
    }
    public static void notNullInScope(Map value, String note, Integer i, Integer i2){
        notNull(value, note);
        notNullInScope(value.size(), note, i, i2);
    }
    public static void notNullInScope(List value, String note, Integer i, Integer i2){
        notNull(value, note);
        notNullInScope(value.size(), note, i, i2);
    }

    /**
     * 组合非空
     */
    public static void notNull(String value, String note, Integer value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(String value, String note, Double value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(String value, String note, Short value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(String value, String note, Float value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(String value, String note, BigDecimal value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(String value, String note, Object value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(String value, String note, Map value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }
    public static void notNull(String value, String note, List value2, String note2){
        notNull(value, note);
        notNull(value2, note2);
    }

}
