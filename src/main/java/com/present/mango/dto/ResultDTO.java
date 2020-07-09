package com.present.mango.dto;

import lombok.Data;

@Data
public class ResultDTO {
  Integer code;
  String msg;
  Object result;
  public ResultDTO (Integer code, String msg, Object result) {
    this.code = code;
    this.msg = msg;
    this.result = result;
  }
}
