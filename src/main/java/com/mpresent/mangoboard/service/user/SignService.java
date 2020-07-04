package com.mpresent.mangoboard.service.user;

import com.mpresent.mangoboard.common.constant.code.UserCode;
import com.mpresent.mangoboard.common.constant.exception.UserConstException;
import com.mpresent.mangoboard.common.exception.CustomException;
import com.mpresent.mangoboard.common.token.JwtTokenProvider;
import com.mpresent.mangoboard.hibernate.dao.UserDao;
import com.mpresent.mangoboard.hibernate.entity.UserEntity;
import com.mpresent.mangoboard.service.user.logic.SignLogic;
import com.mpresent.mangoboard.service.validation.UserValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class SignService {

  UserDao userDao;
  PasswordEncoder passwordEncoder;
  JwtTokenProvider jwtTokenProvider;
  SignLogic signLogic;
  UserValidation userValidation;

  SignService(UserDao userDao,
              PasswordEncoder passwordEncoder,
              JwtTokenProvider jwtTokenProvider,
              SignLogic signLogic,
              UserValidation userValidation) {
    this.userDao = userDao;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.signLogic = signLogic;
    this.userValidation = userValidation;
  }

  /**
   * 회원 로그인
   * @param id
   * @param password
   * @param ip
   * @return
   * @throws CustomException
   */
  public Integer signIn(String id,String password,String ip,
                        HttpServletRequest request, HttpServletResponse response) throws CustomException {
    UserEntity userEntity = userDao.findById(id).orElseThrow(() -> new CustomException(UserConstException.NO_MATCH_ID));
    // SignIn 유효성 체크
    signLogic.validSignInUserEntity(password,userEntity);
    List<String> roles = Arrays.asList("USER");
    // token 생성
    String token = jwtTokenProvider.createToken(userEntity.getUserNo(),roles);
    log.info("token :: {}", token);
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
    // 회원가입 유효성 체크
    // 아이디 유효성 체크
    if (!userValidation.validId(id)) {
      throw new CustomException(UserConstException.INVALID_ID);
    }
    // 아이디 중복 체크
    if (userDao.countById(id) > 0) {
      throw new CustomException(UserConstException.ALREADY_ID);
    }
    // 휴대폰번호 중복 체크
    if (userDao.countByPhone(phone) > 0) {
      throw new CustomException(UserConstException.ALREADY_PHONE);
    }
    UserEntity userEntity = new UserEntity();
    userEntity.setId(id);
    userEntity.setPassword(passwordEncoder.encode(password));
    userEntity.setName(name);
    userEntity.setGender(gender);
    userEntity.setPhone(phone);
    userDao.save(userEntity);
    return userEntity.getUserNo();
  }

}
