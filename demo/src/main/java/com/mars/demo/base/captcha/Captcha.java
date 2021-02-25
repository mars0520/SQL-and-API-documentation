package com.mars.demo.base.captcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author xzp
 * @description 通用的验证码
 * @date 2021/1/5
 **/
public class Captcha extends AbstractCaptcha {

    @Override
    public void render(OutputStream out) {
        drawImage(text(),out);
    }

    @Override
    public String toBase64() {
        return toBase64("data:image/png;base64,");
    }

    public void drawImage(char[] chars, OutputStream output) {
        BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D)img.getGraphics();
        g.setBackground(Color.WHITE);
        g.fillRect(0,0,width,height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        drawLine(g);
        drawOval(g);
        drawBezierLine(g);
        g.setFont(getFont());
        FontMetrics fontMetrics = g.getFontMetrics();
        int fw = (width/chars.length) - 2;
        int fm = (fw - (int)fontMetrics.getStringBounds("8",g).getWidth())/2;
        for(int i=0;i<chars.length;i++){
            g.setColor(color());
            int fh = height - ((height-(int)fontMetrics.getStringBounds(String.valueOf(chars[i]),g).getHeight()) >> 1);
            g.drawString(String.valueOf(chars[i]),i*fw+fm,fh);
        }
        g.dispose();
        try {
            ImageIO.write(img,"png",output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Captcha(){}

    public Captcha(int width,int height){
        this();
        setWidth(width);
        setHeight(height);
    }

    public Captcha(int width,int height,int length){
        this(width, height);
        setLength(length);
    }

    public Captcha(int width, int height, CaptchaType type){
        this(width, height);
        setType(type);
    }

    public Captcha(int width,int height,CaptchaType type,int length){
        this(width, height, type);
        setLength(length);
    }

    public Captcha(int width, int height, CaptchaType type, Font font){
        this(width, height, type);
        setFont(font);
    }

    public Captcha(int width,int height,CaptchaType type,Font font,int length){
        this(width, height, type, font);
        setLength(length);
    }

}
