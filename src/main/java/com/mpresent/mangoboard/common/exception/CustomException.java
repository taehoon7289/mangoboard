package com.mpresent.mangoboard.common.exception;

import com.mpresent.mangoboard.common.constant.exception.ConstException;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class CustomException extends Exception {
  Integer code;
  public CustomException(ConstException constException) {
    super(constException.getMsg());
    this.code = constException.getCode();
  }
}
