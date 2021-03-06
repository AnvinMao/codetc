package com.codetc.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Swagger 配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * Swagger 配置参数类
     */
    @Resource
    private SwaggerProps swaggerProps;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerProps.getEnable())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API 文档")
                .description("REST API")
                .version("1.0.0")
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(new ApiKey("Auth", "X-Auth-Token", "Header"));
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(securityReferences())
                .forPaths(PathSelectors.any())
                .build());
    }

    private List<SecurityReference> securityReferences() {
        return Collections.singletonList(SecurityReference.builder()
                .reference("Auth")
                .scopes(new AuthorizationScope[0])
                .build());
    }
}
