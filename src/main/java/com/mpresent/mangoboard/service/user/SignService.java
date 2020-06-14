package com.mpresent.mangoboard.service.user;

import com.mpresent.mangoboard.common.constant.exception.UserConstException;
import com.mpresent.mangoboard.common.exception.CustomException;
import com.mpresent.mangoboard.hibernate.dao.UserDao;
import com.mpresent.mangoboard.hibernate.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LoginService {

  UserDao userDao;
  PasswordEncoder passwordEncoder;

  LoginService(UserDao userDao,
               PasswordEncoder passwordEncoder) {
    this.userDao = userDao;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * 회원 로그인
   * @param id
   * @param password
   * @param ip
   * @return
   * @throws CustomException
   */
  public Integer login(String id,String password,String ip) throws CustomException {
    User user = userDao.findById(id).orElseThrow(() -> new CustomException(UserConstException.NO_MATCH_USER_ID));
    if (!passwordEncoder.matches(password,user.getPassword())) {
      throw new CustomException(UserConstException.NO_MATCH_USER_PASSWORD);
    }
    log.info("user :: {}", user);
    return 1;
  }

}
