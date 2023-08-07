package org.ymall.learn.easyExcel.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 * @author cheng-qiang
 * @date 2022年08月02日11:38
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.cheng.controller"))
                .paths(PathSelectors.any())
                .build().groupName("接口文档").pathMapping("/");
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("接口文档")
                .contact(new Contact("接口文档",
                        "http://localhost:8213/doc.html",
                        ""))
                .description("接口文档")
                .version("1.0.1")
                .termsOfServiceUrl("http://localhost:8213/swagger-ui.html")
                .build();
    }
}
