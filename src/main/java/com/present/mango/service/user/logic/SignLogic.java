package com.present.mango.service.user.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.present.mango.common.constant.code.TokenCode;
import com.present.mango.common.constant.code.UserCode;
import com.present.mango.common.constant.exception.UserConstException;
import com.present.mango.dto.user.token.UserTokenDTO;
import com.present.mango.common.exception.CustomException;
import com.present.mango.common.token.JwtTokenProvider;
import com.present.mango.form.sign.UserSignInForm;
import com.present.mango.form.sign.UserSignUpForm;
import com.present.mango.jooq.generate.tables.pojos.TblUserBean;
import com.present.mango.validation.UserValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class SignLogic {

  PasswordEncoder passwordEncoder;
  UserValidation userValidation;
  JwtTokenProvider jwtTokenProvider;

  public SignLogic(PasswordEncoder passwordEncoder,
                   UserValidation userValidation,
                   JwtTokenProvider jwtTokenProvider) {
    this.passwordEncoder = passwordEncoder;
    this.userValidation = userValidation;
    this.jwtTokenProvider = jwtTokenProvider;
  }


  /**
   * User Token 생성 (private)
   *
   * @param userTokenDTO
   * @param request
   * @param response
   */
  private void addHeaderUserToken(UserTokenDTO userTokenDTO,
                                  HttpServletRequest request, HttpServletResponse response) {
    ObjectMapper objectMapper = new ObjectMapper();
    Map data = objectMapper.convertValue(userTokenDTO, Map.class);
    // token 생성
    String token = jwtTokenProvider.createToken(TokenCode.type.USER.getValue(), data);
    log.info("token :: {}", token);
    response.setHeader("X-Auth-Token", token);
  }


  /**
   * userToken 추가후 userTokenDTO 리턴
   *
   * @param tblUserBean
   * @param request
   * @param response
   * @return
   */
  public UserTokenDTO addUserToken(TblUserBean tblUserBean,
                                   HttpServletRequest request, HttpServletResponse response) {
    UserTokenDTO userTokenDTO = this.createUserTokenDTO(tblUserBean);
    this.addHeaderUserToken(this.createUserTokenDTO(tblUserBean), request, response);
    return userTokenDTO;
  }


  /**
   * UserTokenDTO 생성 (private)
   *
   * @param tblUserBean
   * @return
   */
  private UserTokenDTO createUserTokenDTO(TblUserBean tblUserBean) {
    UserTokenDTO userTokenDTO = new UserTokenDTO();
    userTokenDTO.setUserNo(tblUserBean.getUserNo());
    userTokenDTO.setUserName(tblUserBean.getName());
    userTokenDTO.setStatus(tblUserBean.getStatus());
    return userTokenDTO;
  }

  /**
   * 회원 SignIn 유효성 체크
   *
   * @param tblUserBean
   * @return
   */
  public void validUserEntityForSignIn(UserSignInForm userSignInForm, TblUserBean tblUserBean) throws CustomException {
    if (!passwordEncoder.matches(userSignInForm.getPassword(), tblUserBean.getPassword())) {
      throw new CustomException(UserConstException.NO_MATCH_PASSWORD);
    }
    if (tblUserBean.getStatus() != UserCode.status.ACTIVE.getValue()) {
      throw new CustomException(UserConstException.INACTIVE_STATUS);
    }
  }

  /**
   * User SignUp 유효성 체크
   *
   * @param userSignUpForm
   * @throws CustomException
   */
  public void validParametersForSignUp(UserSignUpForm userSignUpForm) throws CustomException {
    if (!userValidation.validId(userSignUpForm.getId())) {
      throw new CustomException(UserConstException.INVALID_ID);
    }
    if (!userValidation.validPassword(userSignUpForm.getPassword())) {
      throw new CustomException(UserConstException.INVALID_PASSWORD);
    }
  }

}
