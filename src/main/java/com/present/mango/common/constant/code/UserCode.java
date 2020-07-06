package com.present.mango.common.constant.code;

import lombok.Getter;

public class UserCode {

  @Getter
  public enum status {
    ACTIVE("활성", 1),
    INACTIVE("비활성", 0);
    String name;
    Integer value;
    status(String name, Integer value) {
      this.name = name;
      this.value = value;
    }
  }
}
