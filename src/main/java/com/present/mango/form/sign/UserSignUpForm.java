package com.present.mango.form.sign;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserSignUpForm {
  @NotEmpty String id;
  @NotEmpty String password;
  @NotEmpty String name;
  @NotEmpty String gender;
  @NotEmpty String phone;
  String ip;
}
