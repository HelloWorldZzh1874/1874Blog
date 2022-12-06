package com.zzh.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zzh
 * @Date 2022/1/17 17:06
 * @Version 0.1
 * @Description Swagger3 配置
 **/

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    /** 系统基础配置 */
    @Autowired
    private ApplicationConfig applicationConfig;

    /** 是否开启swagger */
    @Value("${swagger.enabled}")
    private boolean enable;

    @Bean
    public Docket createRestApi() {
        // 添加请求参数，我们这里把token作为请求头部参数传入后端
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameterBuilder.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        parameters.add(parameterBuilder.build());
        //swagger设置，基本信息，要解析的接口及路径等
        return new Docket(DocumentationType.OAS_30)
                // 是否启用swagger
                .enable(enable)
                // 文档页面自定义信息
                .apiInfo(apiInfo())
                // 暴露给swagger的接口
                .select()
                //设置通过什么方式定位需要自动生成文档的接口，这里定位方法上的@ApiOperation注解
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //接口URI路径设置，any是全路径，也可以通过PathSelectors.regex()正则匹配
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    /**
     * 生成接口信息，包括标题、联系人，联系方式等
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 标题
                .title("HelloX接口文档")
                // 描述
                .description("接口文档")
                // 作者信息
                .contact(new Contact(applicationConfig.getName(),null,null))
                .version("版本号"+applicationConfig.getVersion())
                .build();
    }
}
