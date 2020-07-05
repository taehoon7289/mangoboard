package com.mpresent.mangoboard.jooq.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

@Component
public class JUserDao {
  DSLContext context;
  public JUserDao(DSLContext context){
    this.context = context;
  }
  public Integer test() {
    return 1;
  }
}
