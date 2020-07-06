package com.present.mango.common.constant.exception;

import lombok.Getter;

@Getter
public enum UserConstException implements ConstException{

  INVALID_ID(-100, "유효하지않은아이디입니다."),
  INVALID_PASSWORD(-101, "유효하지않은패스워드입니다."),
  INVALID_IP(-102, "유효하지않은아이피입니다."),
  NO_MATCH_ID(-103, "아이디가존재하지않습니다."),
  NO_MATCH_PASSWORD(-104, "패스워드가일치하지않습니다."),
  INVALID_NAME(-105, "유효하지않은이름입니다."),
  INVALID_GENDER(-106, "유효하지않은성별입니다."),
  INVALID_PHONE(-107, "유효하지않은휴대폰번호입니다."),
  ALREADY_ID(-108,"이미등록된아이디입니다."),
  ALREADY_PHONE(-109,"이미등록된휴대폰번호입니다."),
  INACTIVE_STATUS(-110,"비활성화회원입니다."),

  NO_SIGN_UP(-111,"가입되지않았습니다.");

  Integer code;
  String msg;

  UserConstException(Integer code,String msg) {
    this.code = code;
    this.msg = msg;
  }

}
