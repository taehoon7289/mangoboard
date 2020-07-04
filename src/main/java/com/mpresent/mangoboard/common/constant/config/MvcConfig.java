package com.mpresent.mangoboard.common.constant.config;

import com.mpresent.mangoboard.common.constant.intercepter.UserHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new UserHandlerInterceptor())
            .addPathPatterns("/api/user/sign/**")
            .addPathPatterns("/**"); //로그인 쪽은 예외처리를 한다.
  }
}
