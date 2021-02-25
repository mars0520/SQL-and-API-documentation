package com.mars.demo.base.captcha;

/**
 * @author xzp
 * @description
 * @date 2021/1/5
 **/
public enum CaptchaType {
    /**
     * 数字、大小写字母随机组合
     */
    DEFAULT,
    /**
     * 加、减、乘算数运算表达式
     */
    ARITHMETIC,
    /**
     * 中文简体加、减、乘算数运算表达式描述
     */
    ARITHMETIC_ZH,
    /**
     * 常见汉字（3500个）随机组合
     */
    NUMBER,
    /**
     * 0~9数字随机组合
     */
    NUMBER_ZH_CN,
    /**
     * 中文数字（零至九）随机组合
     */
    NUMBER_ZH_HK,
    /**
     * 中文繁体数字（零至玖）随机组合
     */
    WORD,
    /**
     * 大小写字母随机组合
     */
    WORD_UPPER,
    /**
     * 小写字母随机组合
     */
    WORD_LOWER,
    /**
     * 大写字母随机组合
     */
    WORD_NUMBER_UPPER,
    /**
     * 数字、小写字母随机组合
     */
    WORD_NUMBER_LOWER,
    /**
     * 数字、大写字母随机组合
     */
    CHINESE;
}
