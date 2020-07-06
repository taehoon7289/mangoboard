package com.present.mango.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidation {

  /**
   * User 아이디 유효성 체크
   * @param id
   * @return
   */
  public Boolean validId(String id) {
    // 시작은 영문으로만, '_'를 제외한 특수문자 안되며 영문, 숫자, '_'으로만 이루어진 5 ~ 12자 이하
    String regex = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
    Matcher matcher = Pattern.compile(regex).matcher(id);
    return matcher.matches();
  }

  /**
   * User password 유효성 체크
   * @param password
   * @return
   */
  public Boolean validPassword(String password) {
//    1. 영문(대소문자 구분), 숫자, 특수문자 조합
//    2. 6~12자리 사이 문자
//    3. 같은 문자 4개 이상 사용 불가
//    4. 비밀번호에 ID 포함 불가
//    5. 공백문자 사용 불가
    String regex = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{6,12}$";
    Matcher matcher = Pattern.compile(regex).matcher(password);
    return matcher.matches();
  }

  /**
   * User 이메일 유효성 체크
   * @param email
   * @return
   */
  public boolean validEmail(String email) {
    String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
    Matcher matcher = Pattern.compile(regex).matcher(email);
    return matcher.matches();
  }

}
