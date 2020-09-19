package com.present.mango.common.exception;

import com.present.mango.common.constant.dto.ResultDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class GlobalHandlerException {

  /**
   * CostomException 처리
   * @param e
   * @return
   */
  @ExceptionHandler(CustomException.class)
  public ResultDTO handlerCustomException(CustomException e) {
    log.warn("{} :: {}", e.getCode(), e.getMessage());
    return new ResultDTO(e.getCode(),e.getMessage(),null);
  }

  /**
   * @Valid 인한 Exception 처리
   * @param e
   * @return
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResultDTO handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.info("handlerMethodArgumentNotValidException!!!!!!!");
    BindingResult bindingResult = e.getBindingResult();
    StringBuilder builder = new StringBuilder();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      builder.append("[");
      builder.append(fieldError.getField());
      builder.append("](은)는 ");
      builder.append(fieldError.getDefaultMessage());
      builder.append(" 입력된 값: [");
      builder.append(fieldError.getRejectedValue());
      builder.append("]");
    }
    log.warn("{} :: {}", -9999, builder.toString());
    return new ResultDTO(-9999, builder.toString(), null);
  }

  @ExceptionHandler(BindException.class)
  public ResultDTO handlerBindException(BindException e) {
    log.info("handlerBindException!!!!!!! :: {}", e.getMessage());
    BindingResult bindingResult = e.getBindingResult();
    StringBuilder builder = new StringBuilder();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      builder.append("[");
      builder.append(fieldError.getField());
      builder.append("](은)는 ");
      builder.append(fieldError.getDefaultMessage());
      builder.append(" 입력된 값: [");
      builder.append(fieldError.getRejectedValue());
      builder.append("]");
    }
    log.warn("{} :: {}", -9999, builder.toString());
    return new ResultDTO(-9999, builder.toString(), null);
  }


}
