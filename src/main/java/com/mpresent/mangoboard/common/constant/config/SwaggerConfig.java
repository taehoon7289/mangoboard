package com.mpresent.mangoboard.common.constant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo()) //기본정보
            .select()
            .apis(RequestHandlerSelectors.any())  //주소접근 옵션
            .paths(PathSelectors.any())
            .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
            .title("Spring Boot REST Sample with Swagger")
            .description("스웨거 테스트")
            .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
            .contact("user@gmail.com")
            .license("Apache License Version 2.0")
            .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
            .version("2.0")
            .build();
  }
}
