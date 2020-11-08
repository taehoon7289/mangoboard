package com.present.mango.application.form.sign;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserSignInForm {
  @NotEmpty String id;
  @NotEmpty String password;
  String ip;
}
