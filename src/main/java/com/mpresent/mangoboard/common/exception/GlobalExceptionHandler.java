package com.mpresent.mangoboard.common.exception;

import com.mpresent.mangoboard.common.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResultDTO handlerCustomException(CustomException e) {
    return new ResultDTO(e.getCode(),e.getMessage(),null);
  }
}
