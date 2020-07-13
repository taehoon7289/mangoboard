package com.present.mango.common.constant.config;

import com.present.mango.application.domain.jooq.generate.tables.TblBoardMaster;
import com.present.mango.application.domain.jooq.generate.tables.TblFileMaster;
import com.present.mango.application.domain.jooq.generate.tables.TblUserMaster;
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
  public TblUserMaster tblUserMaster() {
    return TblUserMaster.TblUserMaster;
  }

  @Bean
  public TblBoardMaster tblBoardMaster() {
    return TblBoardMaster.TblBoardMaster;
  }

  @Bean
  public TblFileMaster tblFileMaster() {
    return TblFileMaster.TblFileMaster;
  }

}
