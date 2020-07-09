package com.present.mango.service.user;

import com.present.mango.common.constant.exception.UserConstException;
import com.present.mango.dto.user.token.UserTokenDTO;
import com.present.mango.common.exception.CustomException;
import com.present.mango.common.token.JwtTokenProvider;
import com.present.mango.form.sign.UserSignInForm;
import com.present.mango.form.sign.UserSignUpForm;
import com.present.mango.jooq.command.user.UserCommand;
import com.present.mango.jooq.generate.tables.pojos.TblUserBean;
import com.present.mango.jooq.generate.tables.records.TblUserRecord;
import com.present.mango.jooq.query.UserQuery;
import com.present.mango.service.user.logic.SignLogic;
import com.present.mango.validation.UserValidation;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class SignService {

  UserCommand userCommand;
  UserQuery userQuery;
  PasswordEncoder passwordEncoder;
  SignLogic signLogic;

  SignService(UserCommand userCommand,
              UserQuery userQuery,
              PasswordEncoder passwordEncoder,
              SignLogic signLogic) {
    this.userCommand = userCommand;
    this.userQuery = userQuery;
    this.passwordEncoder = passwordEncoder;
    this.signLogic = signLogic;
  }

  /**
   * 회원 sign in
   * @param userSignInForm
   * @param request
   * @param response
   * @return
   * @throws CustomException
   */
  public UserTokenDTO signIn(UserSignInForm userSignInForm,
                             HttpServletRequest request, HttpServletResponse response) throws CustomException {
    Record record = userQuery.selectUserById(userSignInForm.getId()).orElseThrow(() -> new CustomException(UserConstException.NO_MATCH_ID));
    TblUserBean tblUserBean = record.into(TblUserRecord.class).into(TblUserBean.class);
    // SignIn 유효성 체크
    signLogic.validUserEntityForSignIn(userSignInForm,tblUserBean);
    // userToken 추가
    return signLogic.addUserToken(tblUserBean,request,response);
  }

  /**
   * 회원 sign up
   * @param userSignUpForm
   * @param request
   * @param response
   * @return
   * @throws CustomException
   */
  @Transactional(rollbackFor = Exception.class)
  public UserTokenDTO signUp(UserSignUpForm userSignUpForm,
                             HttpServletRequest request, HttpServletResponse response) throws CustomException {
    // 회원가입 유효성 체크
//    INFO: 테스트로 인한 주석처리
//    signLogic.validParametersForSignUp(userSignUpForm);
    // 아이디 중복 체크
    if (userQuery.selectCountById(userSignUpForm.getId()) > 0) {
      throw new CustomException(UserConstException.ALREADY_ID);
    }
    // 휴대폰번호 중복 체크
    if (userQuery.selectCountByPhone(userSignUpForm.getPhone()) > 0) {
      throw new CustomException(UserConstException.ALREADY_PHONE);
    }
    // TblUserBean 생성
    TblUserBean tblUserBean = new TblUserBean();
    tblUserBean.setId(userSignUpForm.getId());
    tblUserBean.setPassword(passwordEncoder.encode(userSignUpForm.getPassword()));
    tblUserBean.setName(userSignUpForm.getName());
    tblUserBean.setGender(userSignUpForm.getGender());
    tblUserBean.setPhone(userSignUpForm.getPhone());
    // insert
    TblUserRecord tblUserRecord =  userCommand.insertUser(tblUserBean)
            .orElseThrow(() -> new CustomException(UserConstException.NO_SIGN_UP));
    tblUserBean = tblUserRecord.into(TblUserBean.class);
    // userToken 추가
    return signLogic.addUserToken(tblUserBean,request,response);
  }

}
