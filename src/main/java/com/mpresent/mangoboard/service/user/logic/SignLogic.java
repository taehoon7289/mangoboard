package com.mpresent.mangoboard.service.user.logic;

import com.mpresent.mangoboard.common.constant.code.UserCode;
import com.mpresent.mangoboard.common.constant.exception.UserConstException;
import com.mpresent.mangoboard.common.dto.user.UserTokenDTO;
import com.mpresent.mangoboard.common.exception.CustomException;
import com.mpresent.mangoboard.common.token.JwtTokenProvider;
import com.mpresent.mangoboard.hibernate.entity.UserEntity;
import com.mpresent.mangoboard.service.validation.UserValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class SignLogic {

  PasswordEncoder passwordEncoder;
  UserValidation userValidation;
  JwtTokenProvider jwtTokenProvider;

  public SignLogic (PasswordEncoder passwordEncoder,
                    UserValidation userValidation,
                    JwtTokenProvider jwtTokenProvider) {
    this.passwordEncoder = passwordEncoder;
    this.userValidation = userValidation;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  /**
   * 회원 SignIn 유효성 체크
   * @param userEntity
   * @return
   */
  public void validUserEntityForSignIn(String password, UserEntity userEntity) throws CustomException {
    if (!passwordEncoder.matches(password, userEntity.getPassword())) {
      throw new CustomException(UserConstException.NO_MATCH_PASSWORD);
    }
    if (userEntity.getStatus() != UserCode.status.ACTIVE.getValue()) {
      throw new CustomException(UserConstException.INACTIVE_STATUS);
    }
  }

  /**
   * User SignUp 유효성 체크
   * @param id
   * @param password
   */
  public void validParametersForSignUp(String id, String password) throws CustomException {
    if (!userValidation.validId(id)) {
      throw new CustomException(UserConstException.INVALID_ID);
    }
    if (!userValidation.validPassword(password)) {
      throw new CustomException(UserConstException.INVALID_PASSWORD);
    }
  }

  /**
   * User Token 생성
   * @param userTokenDTO
   * @param request
   * @param response
   */
  public void addHeaderToken(UserTokenDTO userTokenDTO,
                             HttpServletRequest request, HttpServletResponse response) {
    // token 생성
    String token = jwtTokenProvider.createToken(userTokenDTO);
    log.info("token :: {}", token);
    response.setHeader("X-Auth-Token", token);
  }

  /**
   * UserTokenDTO 생성
   * @param userEntity
   * @return
   */
  public UserTokenDTO createUserTokenDTO(UserEntity userEntity) {
    UserTokenDTO userTokenDTO = new UserTokenDTO();
    userTokenDTO.setUserNo(userEntity.getUserNo());
    userTokenDTO.setUserName(userEntity.getName());
    userTokenDTO.setStatus(userEntity.getStatus());
    return userTokenDTO;
  }
}
