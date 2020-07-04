package com.mpresent.mangoboard.controller.user;


import com.mpresent.mangoboard.common.dto.ResultDTO;
import com.mpresent.mangoboard.common.dto.user.UserTokenDTO;
import com.mpresent.mangoboard.common.exception.CustomException;
import com.mpresent.mangoboard.service.user.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestController {

  @GetMapping(value = "/")
  public ResultDTO test(HttpServletRequest request, HttpServletResponse response) throws CustomException {
    System.out.println("testteststest");
    return new ResultDTO(1,"",null);
  }

}
