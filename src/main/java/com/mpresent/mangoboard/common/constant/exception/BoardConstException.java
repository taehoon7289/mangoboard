package com.mpresent.mangoboard.common.constant.exception;

import lombok.Getter;

@Getter
public enum BoardConstException implements ConstException{

  INVALID_BOARD_NO(-200, "유효하지않은게시물입니다."),
  INVALID_BOARD_TITLE(-200, "유효하지않은제목입니다."),
  INVALID_BOARD_CONTENTS(-200, "유효하지않은내용입니다.");

  Integer code;
  String msg;

  BoardConstException(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

}
