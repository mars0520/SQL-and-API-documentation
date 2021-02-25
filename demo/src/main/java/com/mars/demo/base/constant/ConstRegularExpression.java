package com.mars.demo.base.constant;

/**
 * @author xzp
 * @description 正则表达式
 * @date 2021/1/4
 **/
public class ConstRegularExpression {

    /**
     * 汉字
     */
    public static final String CHINESE_CHARACTERS = "^[\\u4e00-\\u9fa5]{0,}$";

    /**
     * 英文和数字
     * ^[A-Za-z0-9]{4,40}$
     */
    public static final String ENGLISH_AND_NUMBERS = "^[A-Za-z0-9]+$";

    /**
     * 由26个英文字母组成的字符串
     */
    public static final String STRING_LETTERS_26 = "^[A-Za-z]+$";

    /**
     * 由26个大写英文字母组成的字符串
     */
    public static final String STRING_CAPITAL_LETTERS_26 = "^[A-Z]+$";

    /**
     * 由26个小写英文字母组成的字符串
     */
    public static final String STRING_LOWERCASE_LETTERS_26 = "^[a-z]+$";

    /**
     * 由数字和26个英文字母组成的字符串
     */
    public static final String STRING_NUMBERS_LETTERS_26 = "^[A-Za-z0-9]+$";

    /**
     * 中文、英文、数字包括下划线
     */
    public static final String CHINESE_ENGLISH_FIGURES_UNDERLINE = "^[\u4E00-\u9FA5A-Za-z0-9_]+$";

    /**
     * 中文、英文、数字但不包括下划线等符号
     * ^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$
     */
    public static final String CHINESE_ENGLISH_FIGURES_NOT_UNDERLINE = "^[\u4E00-\u9FA5A-Za-z0-9]+$";

    /**
     * Email地址
     */
    public static final String EMAIL_ADDRESS = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 域名
     */
    public static final String DOMAIN_NAME = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.?";

    /**
     * InternetURL
     * ^http://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$
     */
    public static final String INTERNET_URL = "[a-zA-z]+://[^\\s]*";

    /**
     * 手机号码
     */
    public static final String MOBILE_PHONE_NUMBER = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

    /**
     * 电话号码
     * “XXX-XXXXXXX”、”XXXX-XXXXXXXX”、”XXX-XXXXXXX”、”XXX-XXXXXXXX”、”XXXXXXX”和”XXXXXXXX
     */
    public static final String PHONE_NUMBER = "^($$\\d{3,4}-)|\\d{3.4}-)?\\d{7,8}$";

    /**
     * 国内电话号码
     */
    public static final String DOMESTIC_TELEPHONE_NUMBER = "\\d{3}-\\d{8}|\\d{4}-\\d{7}";

    /**
     * 身份证号(15位、18位数字)
     */
    public static final String ID_CARD = "^\\d{15}|\\d{18}$";

    /**
     * 短身份证号码(数字、字母x结尾)
     * ^\d{8,18}|[0-9x]{8,18}|[0-9X]{8,18}?$
     */
    public static final String SHORT_ID_CARD = "^([0-9]){7,18}(x|X)?$";

    /**
     * 帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)
     */
    public static final String ACCOUNT_VALID = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";

    /**
     * 密码(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)
     */
    public static final String PASSWORD = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在6-12之间)
     */
    public static final String STRONG_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,12}$";

    /**
     * 腾讯QQ号
     */
    public static final String TENCENT_QQ_NUMBER = "[1-9][0-9]{4,}";

    /**
     * 中国邮政编码 中国邮政编码为6位数字
     */
    public static final String CHINA_POSTAL_CODE = "[1-9]\\d{5}(?!\\d)";


}
