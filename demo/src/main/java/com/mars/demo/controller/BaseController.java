package com.mars.demo.controller;

import com.mars.demo.base.captcha.CaptchaStyle;
import com.mars.demo.base.captcha.CaptchaType;
import com.mars.demo.base.captcha.Fonts;
import com.mars.demo.base.captcha.HappyCaptcha;
import com.mars.demo.base.constant.SysConst;
import com.mars.demo.base.enums.AdminTypeEnum;
import com.mars.demo.service.WordService;
import com.mars.demo.service.sys.AuthService;
import com.mars.demo.service.sys.AdminService;
import com.mars.demo.util.ParamCheckUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 统一引用
 * @author xzp
 * @date 2020/12/29
 **/
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired protected AuthService authService;

    @Autowired protected AdminService adminService;

    @Autowired protected WordService wordService;

    /**
     * 生成验证码
     * @author xzp
     * @date 2021/1/5 12:16
     */
    protected void generateCode(HttpServletRequest request, HttpServletResponse response) {
        generateCode(request, response, SysConst.LOGIN_CODE, CaptchaStyle.ANIM, CaptchaType.CHINESE, 6);
    }
    protected void generateCode(HttpServletRequest request, HttpServletResponse response, String session_key, CaptchaStyle style, CaptchaType type, Integer length) {
        if(StringUtils.isBlank(session_key)){
            session_key = SysConst.LOGIN_CODE;
        }
        if(null == style){
            style = CaptchaStyle.IMG;
        }
        if(null == type){
            type = CaptchaType.DEFAULT;
        }
        if(null == length || 0 > length || 10 < length){
            length = 6;
        }
        HappyCaptcha.require(request, response, session_key)
        //设置展现样式为动画
        .style(style)
        //设置验证码内容为汉字
        .type(type)
        //设置字符长度为6
        .length(length)
        //设置动画宽度为220
        .width(220)
        //设置动画高度为80
        .height(80)
        //设置汉字的字体
        .font(Fonts.getInstance().zhFont())
        //生成并输出验证码
        .build().finish();
    }

    /**
     * 核对验证码
     * @author xzp
     * @date 2021/1/5 12:16
     */
    protected boolean verifyCode(HttpServletRequest request, String code){
        return verifyCode(request, code, SysConst.LOGIN_CODE);
    }
    protected boolean verifyCode(HttpServletRequest request, String code, String session_key){
        return HappyCaptcha.verification(request, code, session_key);
    }

    /**
     * 清除验证码
     * @author xzp
     * @date 2021/1/5 12:16
     */
    protected void removeCode(HttpServletRequest request, String session_key){
        HappyCaptcha.remove(request, session_key);
    }
    protected void removeCode(HttpServletRequest request){
        HappyCaptcha.remove(request, SysConst.LOGIN_CODE);
    }

    /**
     * 验证管理类型
     * @author xzp
     * @date 2020/12/31 11:15
     * @param type 枚举状态
     */
    protected void validationAdminType(Integer type){
        ParamCheckUtil.listContains(AdminTypeEnum.getEnums(), type, "管理类型");
    }

}
