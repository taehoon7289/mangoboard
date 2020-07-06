package com.present.mango.common.constant.config;

import com.present.mango.common.constant.intercepter.UserHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

  UserHandlerInterceptor userHandlerInterceptor;

  public MvcConfig(UserHandlerInterceptor userHandlerInterceptor){
    this.userHandlerInterceptor = userHandlerInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(userHandlerInterceptor)
            .addPathPatterns("/api/user/**")
            .excludePathPatterns("/api/user/sign/**"); //로그인 쪽은 예외처리를 한다.
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}
