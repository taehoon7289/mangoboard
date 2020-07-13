package com.present.mango.common.constant.code;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Const {

  public static String BASE_DIR;

  @Value("${const.base-dir}")
  public void setBaseDir(String baseDir) {
    BASE_DIR = baseDir;
  }
}
