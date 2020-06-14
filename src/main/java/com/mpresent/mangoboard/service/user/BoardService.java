package com.mpresent.mangoboard.service.user;

import com.mpresent.mangoboard.common.constant.exception.UserConstException;
import com.mpresent.mangoboard.common.exception.CustomException;
import com.mpresent.mangoboard.hibernate.dao.UserDao;
import com.mpresent.mangoboard.hibernate.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SignService {

  UserDao userDao;
  PasswordEncoder passwordEncoder;

  SignService(UserDao userDao,
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
  public Integer signIn(String id,String password,String ip) throws CustomException {
    User user = userDao.findById(id).orElseThrow(() -> new CustomException(UserConstException.NO_MATCH_USER_ID));
    if (!passwordEncoder.matches(password,user.getPassword())) {
      throw new CustomException(UserConstException.NO_MATCH_USER_PASSWORD);
    }
    log.info("user :: {}", user);
    return 1;
  }

  /**
   * 회원 가입
   * @param id
   * @param password
   * @param name
   * @param gender
   * @param phone
   * @param request
   * @param response
   * @return
   * @throws CustomException
   */
  public Integer signUp(String id, String password, String name, String gender, String phone, String ip,
                        HttpServletRequest request, HttpServletResponse response) throws CustomException {
    // 아이디 중복 체크
    if (userDao.countById(id) > 0) {
      throw new CustomException(UserConstException.ALREADY_USER_ID);
    }
    // 휴대폰번호 중복 체크
    if (userDao.countByPhone(phone) > 0) {
      throw new CustomException(UserConstException.ALREADY_USER_PHONE);
    }
    User user = new User();
    user.setId(id);
    user.setGender(passwordEncoder.encode(password));
    user.setName(name);
    user.setGender(gender);
    user.setPassword(phone);
    userDao.save(user);
    return user.getUserNo();
  }

}
