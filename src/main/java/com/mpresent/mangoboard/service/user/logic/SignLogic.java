package com.mpresent.mangoboard.service.user.logic;

import com.mpresent.mangoboard.common.constant.code.UserCode;
import com.mpresent.mangoboard.common.constant.exception.UserConstException;
import com.mpresent.mangoboard.common.exception.CustomException;
import com.mpresent.mangoboard.hibernate.entity.UserEntity;
import com.mpresent.mangoboard.service.validation.UserValidation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SignLogic {

  PasswordEncoder passwordEncoder;
  UserValidation userValidation;

  public SignLogic (PasswordEncoder passwordEncoder,
                    UserValidation userValidation) {
    this.passwordEncoder = passwordEncoder;
    this.userValidation = userValidation;
  }

  /**
   * 회원 SignIn 유효성 체크
   * @param userEntity
   * @return
   */
  public void validSignInUserEntity(String password, UserEntity userEntity) throws CustomException {
    if (!passwordEncoder.matches(password, userEntity.getPassword())) {
      throw new CustomException(UserConstException.NO_MATCH_PASSWORD);
    }
    if (userEntity.getStatus() != UserCode.status.ACTIVE.getValue()) {
      throw new CustomException(UserConstException.INACTIVE_STATUS);
    }
  }

  public void validSignUpUserEntity(String userId, String password, UserEntity userEntity) {
    if (userValidation.validId(userId)) {

    }
  }
}
