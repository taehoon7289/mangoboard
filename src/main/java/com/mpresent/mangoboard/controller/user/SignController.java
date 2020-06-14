package com.mpresent.mangoboard.controller.user;


import com.mpresent.mangoboard.common.constant.exception.UserConstException;
import com.mpresent.mangoboard.common.dto.ResultDTO;
import com.mpresent.mangoboard.common.exception.CustomException;
import com.mpresent.mangoboard.service.user.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/user")
public class LoginController {

  LoginService loginService;

  LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @GetMapping(value = "/login")
  public ResultDTO login(@RequestParam Optional<String> id,
                         @RequestParam Optional<String> password,
                         @RequestParam Optional<String> ip,
                         HttpServletRequest request, HttpServletResponse response) throws CustomException {
    // 필수값 null 체크
    String ogId = id.orElseThrow(() -> new CustomException(UserConstException.INVALID_USER_ID));
    String ogPassword = password.orElseThrow(() -> new CustomException(UserConstException.INVALID_USER_PASSWORD));
    String ogIp = ip.orElseThrow(() -> new CustomException(UserConstException.INVALID_USER_IP));
    loginService.login(ogId,ogPassword,ogIp);
    return new ResultDTO(1,"",null);
  }


}