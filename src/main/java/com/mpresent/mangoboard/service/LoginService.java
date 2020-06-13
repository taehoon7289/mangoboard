package com.mpresent.mangoboard.service;

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

  public Integer login(Optional<String> id,Optional<String> password,Optional<String> ip) throws CustomException {
    User user = userDao.findById(id.get());
    if (Optional.ofNullable(user).isEmpty()) {
      throw new CustomException(UserConstException.NO_MATCH_USER_ID);
    }
    if (!passwordEncoder.matches(password.get(),user.getPassword())) {
      throw new CustomException(UserConstException.NO_MATCH_USER_PASSWORD);
    }
    log.info("user :: {}", user);
    return 1;
  }

}
