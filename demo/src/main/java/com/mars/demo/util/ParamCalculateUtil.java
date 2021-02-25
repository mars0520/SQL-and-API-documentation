package com.mars.demo.util;

/**
 * @description 参数计算工具类
 * @author xzp
 * @date 2020/12/30
 **/
public class ParamCalculateUtil {

    /**
     * @description 获取绝对值
     * @author xzp
     * @date 2020/12/30 10:10
     */
    public static Integer getAbsoluteValue(Integer i){
        return Math.abs(i);
    }
    public static Long getAbsoluteValue(Long i){
        return Math.abs(i);
    }
    public static Float getAbsoluteValue(Float i){
        return Math.abs(i);
    }
    public static Double getAbsoluteValue(Double i){
        return Math.abs(i);
    }

    /**
     * @description 获取绝对值
     * @author xzp
     * @date 2020/12/30 10:10
     */
    public static Integer getMaxValue(Integer i, Integer i2){
        return Math.max(i, i2);
    }
    public static Long getMaxValue(Long i, Long i2){
        return Math.max(i, i2);
    }
    public static Float getMaxValue(Float i, Float i2){
        return Math.max(i, i2);
    }
    public static Double getMaxValue(Double i, Double i2){
        return Math.max(i, i2);
    }

    /**
     * @description 计算x的y次方
     * @author xzp
     * @date 2020/12/30 10:10
     */
    public static Double getPow(Double x, Double y){
        return Math.pow(x, y);
    }

    /**
     * @description 返回a正确的四舍五入的正平方根
     * @author xzp
     * @date 2020/12/30 10:10
     */
    public static Double getSqrt(Double x){
        return Math.sqrt(x);
    }

    /**
     * @description 返回欧拉数x的a次幂
     * @author xzp
     * @date 2020/12/30 10:10
     */
    public static Double getExp(Double x){
        return Math.exp(x);
    }

    /**
     * @description 计算以e为底的对数
     * @author xzp
     * @date 2020/12/30 10:10
     */
    public static Double getLog(Double x){
        return Math.log(x);
    }

    /**
     * @description 计算以10为底的对数
     * @author xzp
     * @date 2020/12/30 10:10
     */
    public static Double getLog10(Double x){
        return Math.log10(x);
    }

    /**
     * @description 返回一个角的三角正弦值
     * @author xzp
     * @date 2020/12/30 10:10
     */
    public static Double sin(Double x){
        return Math.sin(x);
    }

    /**
     * @description 返回一个角的三角余弦值
     * @author xzp
     * @date 2020/12/30 10:10
     */
    public static Double cos(Double x){
        return Math.cos(x);
    }

    /**
     * @description 返回一个角的三角正切
     * @author xzp
     * @date 2020/12/30 10:10
     */
    public static Double tan(Double x){
        return Math.tan(x);
    }

    /**
     * @description 返回一个值的反正弦值
     * @author xzp
     * @date 2020/12/30 10:10
     */
    public static Double asin(Double x){
        return Math.asin(x);
    }

    /**
     * @description 返回一个值的反余弦
     * @author xzp
     * @date 2020/12/30 10:10
     */
    public static Double acos(Double x){
        return Math.acos(x);
    }

}
