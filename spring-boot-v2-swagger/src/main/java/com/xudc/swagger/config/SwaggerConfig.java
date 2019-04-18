package com.xudc.swagger.config;

import org.springframework.boot.SpringBootConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xudc
 * @date 2019/4/18 14:47
 */
@SpringBootConfiguration
@EnableSwagger2
public class SwaggerConfig {
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xudc.swagger.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Swagger构建Restful API文档")
                .description("文档除了查看API还可以进行调试")
                .contact(new Contact("xudc", "https://blog.csdn.net/xudc0521", "e-mail"))
                .version("1.0.0")
                .build();
    }
}
