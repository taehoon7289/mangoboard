package com.present.mango.common.constant.config;

import com.present.mango.jooq.generate.tables.TblBoard;
import com.present.mango.jooq.generate.tables.TblUser;
import com.present.mango.jooq.generate.tables.daos.TblBoardDao;
import com.present.mango.jooq.generate.tables.daos.TblUserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public TblUser tbluser() {
    return TblUser.TblUser;
  }

  @Bean
  public TblBoard tblBoard() {
    return TblBoard.TblBoard;
  }

  @Bean
  public TblUserDao tblUserDao() {
    return new TblUserDao();
  }

  @Bean
  public TblBoardDao tblBoardDao() {
    return new TblBoardDao();
  }

}
