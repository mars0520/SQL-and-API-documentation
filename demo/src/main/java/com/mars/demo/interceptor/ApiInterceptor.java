package com.mars.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.mars.demo.base.bean.ResponseResult;
import com.mars.demo.base.config.WhiteConfig;
import com.mars.demo.base.constant.SysConst;
import com.mars.demo.base.constant.GlobalUserInfo;
import com.mars.demo.base.enums.ReturnMessageType;
import com.mars.demo.base.exception.ParamExceptionHandling;
import com.mars.demo.util.JsonUtil;
import com.mars.demo.util.encrypt.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description 拦截器
 * @author xzp
 * @date 2020/12/29
 **/
//@Component
public class ApiInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

	public WhiteConfig whiteConfig;

	public RedisTemplate<String, String> redisTemplate;

	public ApiInterceptor(WhiteConfig whiteConfig, RedisTemplate<String, String> redisTemplate){
		this.whiteConfig = whiteConfig;
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 1.请求地址是否是黑名单
	 * 2.接口是否重复请求
	 * 3.账号是否登陆
	 * 4.账号TOKEN是否正确
	 * 5.账号是否超级管理员
	 * 6.访问路径是否是白名单
	 * 7.账号是否有权限访问
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		response.setCharacterEncoding(SysConst.CHARACTER_UTF_8);
		//预请求放行
		if (SysConst.REQUEST_OPTIONS.equals(request.getMethod())){
			response.setStatus(HttpServletResponse.SC_OK);
			return true;
		}
		String requestUrl = request.getRequestURI();
		try{
			//请求地址是否是黑名单
			isBlacklist(requestUrl);
			//接口是否重复请求
			filterRepeatAccess(requestUrl);
			//账号是否登陆
			judgeAsLoginStatus();
			//账号TOKEN是否正确
			judgeBusinessToken(request);
			//账号是否超级管理员 访问路径是否是白名单 账号是否有权限访问
			isHasPermission(requestUrl);
			return true;
		}catch (ParamExceptionHandling e){
			e.printStackTrace();
			String jsonObjectStr = JSON.toJSONString(ResponseResult.responseError(e.getCode(), e.getMsg()));
			logger.error("请求路径:" + requestUrl + ",错误信息:" + e.getMsg());
			JsonUtil.returnJson(response, jsonObjectStr);
			response.setStatus(e.getCode());
			return false;
		}catch (Exception e){
			e.printStackTrace();
			String jsonObjectStr = JSON.toJSONString(ResponseResult.responseError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage()));
			logger.error("请求路径:" + requestUrl + ",错误信息:" + e.getMessage());
			JsonUtil.returnJson(response, jsonObjectStr);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
	}

	/**
	 * @description 请求地址是否是黑名单
	 * @author xzp
	 * @date 2020/12/29 16:37
	 */
	private void isBlacklist(String requestUrl){
		List<String> list = whiteConfig.getBlackUrlWithLogin();
		if(null != list && list.contains(requestUrl)){
			throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50008);
		}
	}

	/**
	 * @description 请求地址是否是白名单
	 * @author xzp
	 * @date 2020/12/29 16:37
	 */
	private boolean isWhitelist(String requestUrl){
		List<String> list = whiteConfig.getWhiteUrlWithLogin();
		return null == list ? false : list.contains(requestUrl);
	}

	/**
	 * @description 接口是否重复请求
	 * @author xzp
	 * @date 2020/12/29 16:23
	 */
	private void filterRepeatAccess(String requestUrl) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
		HttpServletRequest request = attributes.getRequest();
		StringBuffer keyStr = new StringBuffer();
		keyStr.append(sessionId).append("-").append(request.getServletPath()).append("-").append(requestUrl);
		String key = keyStr.toString(), value = opsForValue.get(key);
		if (StringUtils.isEmpty(value)) {
			opsForValue.set(key, sessionId, 100, TimeUnit.MILLISECONDS);
		} else {
			logger.warn("重复请求:" + key);
			throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50009);
		}
	}

	/**
	 * @description 判断登陆状态
	 * @author xzp
	 * @date 2020/12/29 16:27
	 */
	private void judgeAsLoginStatus(){
		GlobalUserInfo.getUserInfo();
	}

	/**
	 * @description 账号TOKEN是否正确
	 * @author xzp
	 * @date 2020/12/29 16:27
	 */
	private void judgeBusinessToken(HttpServletRequest request){
		final String token = request.getHeader(SysConst.SYSTEM_TOKEN);
		if(null == token || !JwtUtil.verify(token)) {
			throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50001);
		}
	}

	/**
	 * @description 账号是否有权限访问
	 * @author xzp
	 * @date 2020/12/29 16:27
	 */
	private void isHasPermission(String requestUrl) {
		//非超级管理员 访问路径非白名单 账号权限不包含访问路径
		if(!GlobalUserInfo.isSuperMan() && !isWhitelist(requestUrl) && !GlobalUserInfo.getPermissions().contains(requestUrl)) {
			throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50003);
		}
	}

}
