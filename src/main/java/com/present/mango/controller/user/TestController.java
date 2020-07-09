package com.present.mango.controller.user;


import com.present.mango.dto.ResultDTO;
import com.present.mango.common.exception.CustomException;
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
