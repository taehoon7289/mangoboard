package com.present.mango.application.service.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.present.mango.application.dto.token.TokenDTO;
import com.present.mango.application.form.sign.UserSignInForm;
import com.present.mango.application.form.sign.UserSignUpForm;
import com.present.mango.application.validation.UserValidation;
import com.present.mango.common.constant.code.TokenCode;
import com.present.mango.common.constant.code.UserCode;
import com.present.mango.common.constant.exception.UserConstException;
import com.present.mango.common.exception.CustomException;
import com.present.mango.common.token.JwtTokenProvider;
import com.present.mango.application.domain.jooq.generate.tables.pojos.TblUserMasterBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Component
public class SignBusiness {

  PasswordEncoder passwordEncoder;
  UserValidation userValidation;
  JwtTokenProvider jwtTokenProvider;

  /**
   * User Token 생성 (private)
   *
   * @param tokenDTO
   * @param request
   * @param response
   */
  private void addHeaderUserToken(TokenDTO tokenDTO,
                                  HttpServletRequest request, HttpServletResponse response) {
    ObjectMapper objectMapper = new ObjectMapper();
    Map data = objectMapper.convertValue(tokenDTO, Map.class);
    // token 생성
    String token = jwtTokenProvider.createToken(TokenCode.type.USER.getValue(), data);
    response.setHeader("X-Auth-Token", token);
  }


  /**
   * userToken 추가후 userTokenDTO 리턴
   *
   * @param tblUserMasterBean
   * @param request
   * @param response
   * @return
   */
  public TokenDTO addUserToken(TblUserMasterBean tblUserMasterBean,
                               HttpServletRequest request, HttpServletResponse response) {
    TokenDTO tokenDTO = this.createUserTokenDTO(tblUserMasterBean);
    this.addHeaderUserToken(this.createUserTokenDTO(tblUserMasterBean), request, response);
    return tokenDTO;
  }


  /**
   * TokenDTO 생성 (private)
   *
   * @param tblUserMasterBean
   * @return
   */
  private TokenDTO createUserTokenDTO(TblUserMasterBean tblUserMasterBean) {
    TokenDTO tokenDTO = new TokenDTO();
    tokenDTO.setUserNo(tblUserMasterBean.getUserNo());
    tokenDTO.setUserName(tblUserMasterBean.getName());
    tokenDTO.setStatus(tblUserMasterBean.getStatus());
    return tokenDTO;
  }

  /**
   * 회원 SignIn 유효성 체크
   *
   * @param tblUserMasterBean
   * @return
   */
  public void validUserEntityForSignIn(UserSignInForm userSignInForm, TblUserMasterBean tblUserMasterBean) throws CustomException {
    if (!passwordEncoder.matches(userSignInForm.getPassword(), tblUserMasterBean.getPassword())) {
      throw new CustomException(UserConstException.NO_MATCH_PASSWORD);
    }
    if (tblUserMasterBean.getStatus() != UserCode.status.ACTIVE.getValue()) {
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
