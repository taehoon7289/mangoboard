package com.present.mango.form.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class BoardSaveForm {
  @NotEmpty String title;
  @NotEmpty String contents;
  String image;
  MultipartFile imageFile;
}
