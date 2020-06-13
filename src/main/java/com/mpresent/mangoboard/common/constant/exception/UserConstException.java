package com.mpresent.mangoboard.common.constant.exception;

import lombok.Getter;

@Getter
public enum UserConstException implements ConstException{

  INVALID_USER_ID(-100, "유효하지않은아이디입니다."),
  INVALID_USER_PASSWORD(-101, "유효하지않은패스워드입니다."),
  INVALID_USER_IP(-102, "유효하지않은아이피입니다."),
  NO_MATCH_USER_ID(-103, "아이디가존재하지않습니다."),
  NO_MATCH_USER_PASSWORD(-104, "패스워드가일치하지않습니다.");

  Integer code;
  String msg;

  UserConstException(Integer code,String msg) {
    this.code = code;
    this.msg = msg;
  }

}
