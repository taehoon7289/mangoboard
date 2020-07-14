package com.present.mango.application.service;

import com.present.mango.application.dto.token.TokenDTO;
import com.present.mango.application.form.sign.UserSignInForm;
import com.present.mango.application.form.sign.UserSignUpForm;
import com.present.mango.application.service.business.SignBusiness;
import com.present.mango.common.constant.exception.UserConstException;
import com.present.mango.common.exception.CustomException;
import com.present.mango.application.domain.jooq.command.UserCommand;
import com.present.mango.application.domain.jooq.generate.tables.pojos.TblUserMasterBean;
import com.present.mango.application.domain.jooq.generate.tables.records.TblUserMasterRecord;
import com.present.mango.application.domain.jooq.query.UserQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@AllArgsConstructor
@Service
public class SignService {

  UserCommand userCommand;
  UserQuery userQuery;
  PasswordEncoder passwordEncoder;
  SignBusiness signBusiness;

  /**
   * 회원 sign in
   * @param userSignInForm
   * @param request
   * @param response
   * @return
   * @throws CustomException
   */
  public TokenDTO signIn(UserSignInForm userSignInForm,
                         HttpServletRequest request, HttpServletResponse response) throws CustomException {
    Record record = userQuery.selectUserById(userSignInForm.getId())
            .orElseThrow(() -> new CustomException(UserConstException.NO_MATCH_ID));
    TblUserMasterBean tblUserMasterBean = record.into(TblUserMasterRecord.class).into(TblUserMasterBean.class);
    // SignIn 유효성 체크
    signBusiness.validUserEntityForSignIn(userSignInForm,tblUserMasterBean);
    // userToken 추가
    return signBusiness.addUserToken(tblUserMasterBean,request,response);
  }

  /**
   * 회원 sign up
   * @param userSignUpForm
   * @param request
   * @param response
   * @return
   * @throws CustomException
   */
  @Transactional(rollbackFor = Exception.class, noRollbackFor = CustomException.class)
  public TokenDTO signUp(UserSignUpForm userSignUpForm,
                         HttpServletRequest request, HttpServletResponse response) throws CustomException {
    // 회원가입 유효성 체크
//    INFO: 테스트로 인한 주석처리
//    signLogic.validParametersForSignUp(userSignUpForm);
    // 아이디 중복 체크
    if (userQuery.selectUserCountById(userSignUpForm.getId()) > 0) {
      throw new CustomException(UserConstException.ALREADY_ID);
    }
    // 휴대폰번호 중복 체크
    if (userQuery.selectUserCountByPhone(userSignUpForm.getPhone()) > 0) {
      throw new CustomException(UserConstException.ALREADY_PHONE);
    }
    // TblUserBean 생성
    TblUserMasterBean tblUserMasterBean = new TblUserMasterBean();
    tblUserMasterBean.setId(userSignUpForm.getId());
    tblUserMasterBean.setPassword(passwordEncoder.encode(userSignUpForm.getPassword()));
    tblUserMasterBean.setName(userSignUpForm.getName());
    tblUserMasterBean.setGender(userSignUpForm.getGender());
    tblUserMasterBean.setPhone(userSignUpForm.getPhone());
    // insert
    TblUserMasterRecord tblUserMasterRecord =  userCommand.insertUserMaster(tblUserMasterBean)
            .orElseThrow(() -> new CustomException(UserConstException.NO_SIGN_UP));
    tblUserMasterBean = tblUserMasterRecord.into(TblUserMasterBean.class);
    // userToken 추가
    return signBusiness.addUserToken(tblUserMasterBean,request,response);
  }

}
