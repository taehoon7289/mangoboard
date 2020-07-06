package com.present.mango.common.exception;

import com.present.mango.common.constant.exception.ConstException;
import lombok.Getter;

@Getter
public class CustomException extends Exception {
  Integer code;
  public CustomException(ConstException constException) {
    super(constException.getMsg());
    this.code = constException.getCode();
  }
}
