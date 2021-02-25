package com.mars.demo.base.config.swagger;


import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description swagger配置
 * @author xzp
 * @date 2019/11/6 6:33 下午
 **/
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket( DocumentationType.SWAGGER_2 )
                .apiInfo( apiInfo() )
                .select()
                .apis( RequestHandlerSelectors.basePackage( "com.mars.demo.controller" ) )
                .paths( PathSelectors.any() )
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title( "demo" )
                .description( "文档说明" )
                .termsOfServiceUrl("localhost:8000")
                .contact( new Contact( "xzp", "", "" ) )
                .version( "1.0" )
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration( null, "list", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L );
    }


}

