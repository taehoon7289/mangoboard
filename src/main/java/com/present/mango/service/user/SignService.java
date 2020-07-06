package com.present.mango.service.user;

import com.present.mango.common.constant.exception.UserConstException;
import com.present.mango.common.dto.user.UserTokenDTO;
import com.present.mango.common.exception.CustomException;
import com.present.mango.common.token.JwtTokenProvider;
import com.present.mango.hibernate.dao.UserDao;
import com.present.mango.hibernate.entity.UserEntity;
import com.present.mango.jooq.command.UserCommand;
import com.present.mango.jooq.generate.tables.pojos.TblUserBean;
import com.present.mango.jooq.generate.tables.records.TblUserRecord;
import com.present.mango.jooq.query.UserQuery;
import com.present.mango.service.user.logic.SignLogic;
import com.present.mango.validation.UserValidation;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Service
public class SignService {

  UserCommand userCommand;
  UserQuery userQuery;
  PasswordEncoder passwordEncoder;
  JwtTokenProvider jwtTokenProvider;
  SignLogic signLogic;
  UserValidation userValidation;

  SignService(UserCommand userCommand,
              UserQuery userQuery,
              PasswordEncoder passwordEncoder,
              JwtTokenProvider jwtTokenProvider,
              SignLogic signLogic,
              UserValidation userValidation) {
    this.userCommand = userCommand;
    this.userQuery = userQuery;
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
  public UserTokenDTO signIn(String id, String password, String ip,
                             HttpServletRequest request, HttpServletResponse response) throws CustomException {
    Record record = userQuery.selectUserById(id).orElseThrow(() -> new CustomException(UserConstException.NO_MATCH_ID));
    TblUserBean tblUserBean = record.into(TblUserRecord.class).into(TblUserBean.class);
    // SignIn 유효성 체크
    signLogic.validUserEntityForSignIn(password,tblUserBean);
    // userTokenDTO 생성
    UserTokenDTO userTokenDTO = signLogic.createUserTokenDTO(tblUserBean);
    // Token 생성후 response header 추가
    signLogic.addHeaderToken(userTokenDTO,request,response);
    return userTokenDTO;
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
  public UserTokenDTO signUp(String id, String password, String name, String gender, String phone, String ip,
                        HttpServletRequest request, HttpServletResponse response) throws CustomException {
    // 회원가입 유효성 체크
    signLogic.validParametersForSignUp(id,password);
    // 아이디 중복 체크
    if (userQuery.selectCountById(id) > 0) {
      throw new CustomException(UserConstException.ALREADY_ID);
    }
    // 휴대폰번호 중복 체크
    if (userQuery.selectCountByPhone(phone) > 0) {
      throw new CustomException(UserConstException.ALREADY_PHONE);
    }
    TblUserBean tblUserBean = new TblUserBean();
    tblUserBean.setId(id);
    tblUserBean.setPassword(passwordEncoder.encode(password));
    tblUserBean.setName(name);
    tblUserBean.setGender(gender);
    tblUserBean.setPhone(phone);
    TblUserRecord tblUserRecord =  userCommand.insertUser(tblUserBean)
            .orElseThrow(() -> new CustomException(UserConstException.NO_SIGN_UP));
    tblUserBean = tblUserRecord.into(TblUserBean.class);
    // UserTokenDTO 생성
    UserTokenDTO userTokenDTO = signLogic.createUserTokenDTO(tblUserBean);
    // Token 생성후 response header 추가
    signLogic.addHeaderToken(userTokenDTO,request,response);
    return userTokenDTO;
  }

}
