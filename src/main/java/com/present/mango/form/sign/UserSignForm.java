package com.present.mango.form.sign;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserSignForm {
  String id;
  String password;
  String name;
  String gender;
  String phone;
  String ip;
}
