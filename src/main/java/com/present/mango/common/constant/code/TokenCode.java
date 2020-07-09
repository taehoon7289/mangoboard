package com.present.mango.common.constant.code;

import lombok.Getter;

public class TokenCode {
  @Getter
  public enum type {
    USER("일반회원", "USER"),
    ADMIN("관리자", "ADMIN");
    String name;
    String value;
    type(String name, String value) {
      this.name = name;
      this.value = value;
    }
  }
}
