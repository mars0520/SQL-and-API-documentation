package com.mars.demo.controller.sys;

import com.mars.demo.base.bean.ResponseResult;
import com.mars.demo.base.captcha.CaptchaStyle;
import com.mars.demo.base.captcha.CaptchaType;
import com.mars.demo.base.constant.SysConst;
import com.mars.demo.base.constant.GlobalUserInfo;
import com.mars.demo.base.enums.ReturnMessageType;
import com.mars.demo.base.enums.UserStatusEnum;
import com.mars.demo.base.exception.ParamExceptionHandling;
import com.mars.demo.bean.vo.AuthPasswordLoginVO;
import com.mars.demo.controller.BaseController;
import com.mars.demo.util.ParamCheckUtil;
import com.mars.demo.util.encrypt.AesEncryptUtil;
import com.mars.demo.util.encrypt.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 登陆模块
 * @author xzp
 * @date 2020/12/29
 **/
@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
@Api(tags = "登录模块")
@RequestMapping("/api")
public class LoginController extends BaseController {

    @PostMapping("/login")
    @ApiOperation(value = "根据账号密码登录", notes = "根据账号密码登录")
    public ResponseResult login(HttpServletRequest request, @Validated @RequestBody AuthPasswordLoginVO param){
        try{
            //核对验证码是否正确
            if(verifyCode(request, param.getCode())){
                removeCode(request);
            }else {
                throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50015);
            }
            String account = param.getAccount(),
            password = AesEncryptUtil.desEncrypt(param.getPassword());
            SecurityUtils.getSubject().login(new UsernamePasswordToken(account, password));
            if(UserStatusEnum.NORMAL_USER_STATUS_ENUM.getValue().equals(GlobalUserInfo.getUserStatus())){
                //登录成功
                SecurityUtils.getSubject().getSession().setAttribute("isSuperMan", GlobalUserInfo.getUserRoleId().contains(SysConst.IS_SUPERMAN_ID));
                return ResponseResult.responseOk(getLoginInfo(request, account, password));
            }else if(UserStatusEnum.DISABLE_USER_STATUS_ENUM.getValue().equals(GlobalUserInfo.getUserStatus())){
                //此账号被禁用
                return ResponseResult.responseError(ReturnMessageType.MSG_SUCCESS_20003);
            } else {
                //此账号已冻结
                return ResponseResult.responseError(ReturnMessageType.MSG_SUCCESS_20005);
            }
        } catch (ParamExceptionHandling e) {
            e.printStackTrace();
            return ResponseResult.responseError(e.getCode(), e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.responseError(ReturnMessageType.MSG_SUCCESS_20010);
        }
    }

    @GetMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录")
    public ResponseResult logout(){
        SecurityUtils.getSubject().logout();
        return ResponseResult.responseOk();
    }

    /**
     * 获取登录后信息
     * @author xzp
     * @date 2020/12/30 14:53
     * @param account 账号
     * @param password 密码
     */
    public Map getLoginInfo(HttpServletRequest request, String account, String password){
        Map map = new HashMap(3);
        map.put(SysConst.SESSION_ID, request.getSession().getId());
        map.put(SysConst.SYSTEM_TOKEN, JwtUtil.sign(account, password));
        map.put("menus", authService.getMenusNotCache());
        return map;
    }

    @GetMapping("/getMenus")
    @ApiOperation(value = "获取菜单(取缓存)", notes = "获取菜单(取缓存)")
    public ResponseResult getMenus(){
        return ResponseResult.responseOk(authService.getMenus());
    }

    @GetMapping("/getMenusNotCache")
    @ApiOperation(value = "获取菜单(查库)", notes = "获取菜单(查库)")
    public ResponseResult getMenusNotCache(){
        return ResponseResult.responseOk(authService.getMenusNotCache());
    }

    @GetMapping("/getEncrypt")
    @ApiOperation(value = "加密参数", notes = "加密参数")
    public ResponseResult getEncrypt(String value){
        ParamCheckUtil.notNull(value, "加密参数");
        return ResponseResult.responseOk(AesEncryptUtil.encrypt(value));
    }

    @GetMapping("/getDesEncrypt")
    @ApiOperation(value = "解密参数", notes = "解密参数")
    public ResponseResult getDesEncrypt(String value){
        ParamCheckUtil.notNull(value, "解密参数");
        return ResponseResult.responseOk(AesEncryptUtil.desEncrypt(value));
    }

    @GetMapping("/getCode")
    @ApiOperation(value = "获取验证码", notes = "请求参数 session_key:默认login_code\n\r style:(验证码展现形式 IMG图片/ANIM动画)\n\r type:(DEFAULT数字、大小写字母随机组合 \n\r ARITHMETIC加、减、乘算数运算表达式 \n\r ARITHMETIC_ZH中文简体加、减、乘算数运算表达式描述 \n\r NUMBER常见汉字（3500个）随机组合 \n\r NUMBER_ZH_CN0~9数字随机组合 \n\r NUMBER_ZH_HK中文数字（零至九）随机组合 \n\r WORD中文繁体数字（零至玖）随机组合 \n\r WORD_UPPER大小写字母随机组合 \n\r WORD_LOWER小写字母随机组合 \n\r WORD_NUMBER_UPPER大写字母随机组合 \n\r WORD_NUMBER_LOWER数字、小写字母随机组合 \n\r CHINESE数字、大写字母随机组合) \n\r length:长度")
    public void getCode(HttpServletRequest request, HttpServletResponse response, String session_key, CaptchaStyle style, CaptchaType type, Integer length){
        generateCode(request, response, session_key, style, type, length);
    }


}
