package com.present.mango.common.constant.exception;

import lombok.Getter;

@Getter
public enum FileConstException implements ConstException{

  NOT_TRANSFER_MULTIPART_FILE(-300, "파일 생성에 실패하였습니다.");

  Integer code;
  String msg;

  FileConstException(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

}
