package com.deng.gan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: shayu
 * @date: 2022年09月16日 13:57
 * @ClassName: SwaggerConfig
 * @Description:    swaggerAPI 接口（好像没什么用；http://[ip]:[port]/swagger-ui.html）
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("RabbitApi")
                .apiInfo(webApiInfo())
                .select()
                .build();
    }
    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("rabbitmq接口文档")
                .description("本文档描述了 rabbitmq微服务接口定义")
                .version("1.0")
                .contact(new
                        Contact("shayu",
                        "https://shayu-blogs.shayuyu.cn",
                        ""))
                .build();
    }
}

