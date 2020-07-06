package com.present.mango.common.constant.intercepter;

import com.present.mango.common.token.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class UserHandlerInterceptor extends HandlerInterceptorAdapter {

  JwtTokenProvider jwtTokenProvider;

  UserHandlerInterceptor(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    log.info("UserHandlerInterceptor > preHandle");
    String token = jwtTokenProvider.resolveToken(request);
    if (ObjectUtils.isEmpty(token)) {
      log.info("token 비어있음!!!!!!!!!!!");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return false;
    }
    if (!jwtTokenProvider.validateToken(token)) {
      log.info("token 만료!!!!!!!!!!!!!!");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return false;
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    log.info("UserHandlerInterceptor > postHandle");
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
    log.info("UserHandlerInterceptor > afterCompletion" );
  }
}
