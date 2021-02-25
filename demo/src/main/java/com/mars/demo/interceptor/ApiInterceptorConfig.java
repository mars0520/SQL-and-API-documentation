package com.mars.demo.interceptor;


import java.nio.charset.StandardCharsets;
import java.util.List;

import com.mars.demo.base.config.WhiteConfig;
import com.mars.demo.base.constant.SysConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @description 操作拦截器
 * @author xzp
 * @date 2019/10/9 10:57 上午
 **/
@SuppressWarnings("deprecation")
//@Configuration
public class ApiInterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired private WhiteConfig whiteConfig;
    @Autowired private RedisTemplate<String, String> redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiInterceptor(whiteConfig, redisTemplate))
                .addPathPatterns("/**")
                .excludePathPatterns(SysConst.LOGIN_URL, "/api/test/test", "/api/getEncrypt", "/api/getDesEncrypt", "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html/**", "/webSocket/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler( "swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler( "/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * springboot2.4 allowedOriginPatterns
     * springboot2.0 allowedOrigins
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*")
                .allowedMethods(SysConst.REQUEST_OPTIONS, SysConst.REQUEST_HEAD, SysConst.REQUEST_GET, SysConst.REQUEST_POST, SysConst.REQUEST_PUT, SysConst.REQUEST_DELETE)
                .allowCredentials(true).maxAge(3600);
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter( StandardCharsets.UTF_8 );
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters( converters );
        //解决中文乱码
        converters.add( responseBodyConverter() );
        //解决 添加解决中文乱码后 上述配置之后，返回json数据直接报错 500：no convertter for return value of type
        converters.add( messageConverter() );
    }

    @Bean
    public MappingJackson2HttpMessageConverter messageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper( getObjectMapper() );
        return converter;
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}
