package com.mpresent.mangoboard.controller.user;


import com.mpresent.mangoboard.common.constant.exception.UserConstException;
import com.mpresent.mangoboard.common.dto.ResultDTO;
import com.mpresent.mangoboard.common.exception.CustomException;
import com.mpresent.mangoboard.service.user.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/sign")
public class SignController {

  SignService signService;

  SignController(SignService signService) {
    this.signService = signService;
  }

  @GetMapping(value = "/in")
  public ResultDTO signIn(@RequestParam Optional<String> id,
                         @RequestParam Optional<String> password,
                         @RequestParam Optional<String> ip,
                         HttpServletRequest request, HttpServletResponse response) throws CustomException {
    // 필수값 null 체크
    String ogId = id.orElseThrow(() -> new CustomException(UserConstException.INVALID_USER_ID));
    String ogPassword = password.orElseThrow(() -> new CustomException(UserConstException.INVALID_USER_PASSWORD));
    String ogIp = ip.orElseThrow(() -> new CustomException(UserConstException.INVALID_USER_IP));
    signService.signIn(ogId,ogPassword,ogIp);
    return new ResultDTO(1,"",null);
  }

  @GetMapping(value = "/up")
  public ResultDTO signUp(@RequestParam Optional<String> id,
                          @RequestParam Optional<String> password,
                          @RequestParam Optional<String> name,
                          @RequestParam Optional<String> gender,
                          @RequestParam Optional<String> phone,
                          @RequestParam Optional<String> ip,
                          HttpServletRequest request, HttpServletResponse response) throws CustomException {
    // 필수값 null 체크
    String ogId = id.orElseThrow(() -> new CustomException(UserConstException.INVALID_USER_ID));
    String ogPassword = password.orElseThrow(() -> new CustomException(UserConstException.INVALID_USER_PASSWORD));
    String ogName = name.orElseThrow(() -> new CustomException(UserConstException.INVALID_USER_NAME));
    String ogGender = gender.orElseThrow(() -> new CustomException(UserConstException.INVALID_USER_GENDER));
    String ogPhone = phone.orElseThrow(() -> new CustomException(UserConstException.INVALID_USER_PHONE));
    String ogIp = ip.orElseThrow(() -> new CustomException(UserConstException.INVALID_USER_IP));
    Integer result = signService.signUp(ogId,ogPassword,ogName,ogGender,ogPhone,ogIp,request,response);
    return new ResultDTO(1,"",result);
  }


}
