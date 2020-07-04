package com.mpresent.mangoboard.controller.user;


import com.mpresent.mangoboard.common.constant.exception.UserConstException;
import com.mpresent.mangoboard.common.dto.ResultDTO;
import com.mpresent.mangoboard.common.dto.user.UserTokenDTO;
import com.mpresent.mangoboard.common.exception.CustomException;
import com.mpresent.mangoboard.service.user.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
  public ResultDTO signUp(@RequestParam String id,
                          @RequestParam String password,
                          @RequestParam String name,
                          @RequestParam String gender,
                          @RequestParam String phone,
                          @RequestParam(required = false) String ip,
                          HttpServletRequest request, HttpServletResponse response) throws CustomException {
    Integer result = signService.signUp(id,password,name,gender,phone,ip,request,response);
    return new ResultDTO(1,"",result);
  }


}
