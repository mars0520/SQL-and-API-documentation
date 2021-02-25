package com.mars.demo.base.captcha;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;

/**
 * @author xzp
 * @description
 * @date 2021/1/5
 **/
public class HappyCaptcha {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private CaptchaType type;
    private CaptchaStyle style;
    private Font font;
    private int width;
    private int height;
    private int length;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String session_key;

    /**
     * 生成验证码
     * @param session_key
     * @return
     */
    public static Builder require(HttpServletRequest request, HttpServletResponse response, String session_key){
        return new Builder(request, response, session_key);
    }

    public boolean finish() {
        try {
            if(this.style.equals(CaptchaStyle.IMG)){
                Captcha captcha = new Captcha();
                captcha.setType(this.type);
                captcha.setWidth(this.width);
                captcha.setHeight(this.height);
                captcha.setLength(this.length);
                captcha.setFont(this.font);
                setHeader(this.response);
                String code = captcha.getCaptchaCode();
                this.request.getSession().setAttribute(session_key, code);
                this.request.getSession().setMaxInactiveInterval(1800);
                logger.info("sessionId:" + this.request.getSession().getId() + ",值:" + code);
                captcha.render(this.response.getOutputStream());
                return true;
            }else if(this.style.equals(CaptchaStyle.ANIM)){
                AnimCaptcha captcha = new AnimCaptcha();
                captcha.setType(this.type);
                captcha.setWidth(this.width);
                captcha.setHeight(this.height);
                captcha.setLength(this.length);
                captcha.setFont(this.font);
                setHeader(this.response);
                String code = captcha.getCaptchaCode();
                this.request.getSession().setAttribute(session_key, code);
                this.request.getSession().setMaxInactiveInterval(1800);
                logger.info("sessionId:" + this.request.getSession().getId() + ",值:" + code);
                captcha.render(this.response.getOutputStream());
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 校验验证码
     * @param code              code
     * @param session_key       key
     * @return                  boolean
     */
    public static boolean verification(HttpServletRequest request, String code, String session_key){
        return verification(request, code, session_key, true);
    }
    public static boolean verification(HttpServletRequest request, String code, String session_key, boolean ignoreCase){
        if(StringUtils.isBlank(code)){
            return false;
        }
        String captcha = (String)request.getSession().getAttribute(session_key);
        return ignoreCase ? code.equalsIgnoreCase(captcha) : code.equals(captcha);
    }

    /**
     * 删除session
     * @param session_key   key
     */
    public static void remove(HttpServletRequest request, String session_key){
        request.getSession().removeAttribute(session_key);
    }

    public void setHeader(HttpServletResponse response) {
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }


    private HappyCaptcha(Builder builder){
        this.type = builder.type;
        this.style = builder.style;
        this.font = builder.font;
        this.width = builder.width;
        this.height = builder.height;
        this.length = builder.length;
        this.request = builder.request;
        this.response = builder.response;
    }

    public static class Builder{
        private CaptchaType type = CaptchaType.DEFAULT;
        private CaptchaStyle style = CaptchaStyle.IMG;
        private Font font = Fonts.getInstance().defaultFont();
        private int width = 160;
        private int height = 50;
        private int length = 5;
        private final HttpServletRequest request;
        private final HttpServletResponse response;
        private final String session_key;

        public Builder(HttpServletRequest request, HttpServletResponse response, String session_key){
            this.request = request;
            this.response = response;
            this.session_key = session_key;
        }

        public HappyCaptcha build(){
            return new HappyCaptcha(this);
        }

        public Builder type(CaptchaType type){
            this.type = type;
            return this;
        }

        public Builder style(CaptchaStyle style){
            this.style = style;
            return this;
        }

        public Builder width(int width){
            this.width = width;
            return this;
        }

        public Builder height(int height){
            this.height = height;
            return this;
        }

        public Builder length(int length){
            this.length = length;
            return this;
        }
        public Builder font(Font font){
            this.font = font;
            return this;
        }
    }

}
