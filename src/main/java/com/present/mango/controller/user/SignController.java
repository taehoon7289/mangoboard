package com.present.mango.controller.user;


import com.present.mango.dto.ResultDTO;
import com.present.mango.dto.user.token.UserTokenDTO;
import com.present.mango.common.exception.CustomException;
import com.present.mango.form.sign.UserSignForm;
import com.present.mango.service.user.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(value = "/api/user/sign")
public class SignController {

  SignService signService;

  SignController(SignService signService) {
    this.signService = signService;
  }

  @GetMapping(value = "/")
  public ResultDTO signIn(@RequestParam String id,
                          @RequestParam String password,
                          @RequestParam(required = false) String ip,
                          HttpServletRequest request, HttpServletResponse response) throws CustomException {
    UserTokenDTO result = null;
    result = signService.signIn(id,password,ip,request,response);
    return new ResultDTO(1,"",result);
  }

  @PostMapping(value = "/")
  public ResultDTO signUp(@RequestBody UserSignForm userSignForm,
                          HttpServletRequest request, HttpServletResponse response) throws CustomException {
    UserTokenDTO result = null;
    result = signService.signUp(userSignForm,request,response);
    return new ResultDTO(1,"",result);
  }


}
