package com.present.mango.common.exception;

import com.present.mango.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * CostomException 처리
   * @param e
   * @return
   */
  @ExceptionHandler(CustomException.class)
  public ResultDTO handlerCustomException(CustomException e) {
    return new ResultDTO(e.getCode(),e.getMessage(),null);
  }

  /**
   * @Valid 인한 Exception 처리
   * @param e
   * @return
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResultDTO handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
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
    return new ResultDTO(-9999, builder.toString(), null);
  }
}
