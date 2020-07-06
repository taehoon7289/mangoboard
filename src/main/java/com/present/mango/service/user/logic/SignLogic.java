package com.present.mango.service.user.logic;

import com.present.mango.common.constant.code.UserCode;
import com.present.mango.common.constant.exception.UserConstException;
import com.present.mango.common.dto.user.UserTokenDTO;
import com.present.mango.common.exception.CustomException;
import com.present.mango.common.token.JwtTokenProvider;
import com.present.mango.hibernate.entity.UserEntity;
import com.present.mango.jooq.generate.tables.pojos.TblUserBean;
import com.present.mango.validation.UserValidation;
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
   * @param tblUserBean
   * @return
   */
  public void validUserEntityForSignIn(String password, TblUserBean tblUserBean) throws CustomException {
    if (!passwordEncoder.matches(password, tblUserBean.getPassword())) {
      throw new CustomException(UserConstException.NO_MATCH_PASSWORD);
    }
    if (tblUserBean.getStatus() != UserCode.status.ACTIVE.getValue()) {
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
   * @param tblUserBean
   * @return
   */
  public UserTokenDTO createUserTokenDTO(TblUserBean tblUserBean) {
    UserTokenDTO userTokenDTO = new UserTokenDTO();
    userTokenDTO.setUserNo(tblUserBean.getUserNo());
    userTokenDTO.setUserName(tblUserBean.getName());
    userTokenDTO.setStatus(tblUserBean.getStatus());
    return userTokenDTO;
  }
}
