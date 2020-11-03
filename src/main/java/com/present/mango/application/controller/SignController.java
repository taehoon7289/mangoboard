package com.present.mango.application.controller;


import com.present.mango.common.constant.dto.ResultDTO;
import com.present.mango.application.dto.token.TokenDTO;
import com.present.mango.common.exception.CustomException;
import com.present.mango.application.form.sign.UserSignInForm;
import com.present.mango.application.form.sign.UserSignUpForm;
import com.present.mango.application.service.SignService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/user/sign")
public class SignController {

  SignService signService;

  @PostMapping(value = "/in")
  public ResultDTO signIn(@RequestBody @Valid UserSignInForm userSignInForm,
                          HttpServletRequest request, HttpServletResponse response) throws CustomException {
    TokenDTO result = null;
    result = signService.signIn(userSignInForm,request,response);
    return new ResultDTO(1,"",result);
  }

  @PostMapping(value = "/up")
  public ResultDTO signUp(@RequestBody @Valid UserSignUpForm userSignUpForm,
                          HttpServletRequest request, HttpServletResponse response) throws CustomException {
    TokenDTO result = null;
    result = signService.signUp(userSignUpForm,request,response);
    return new ResultDTO(1,"",result);
  }


}
