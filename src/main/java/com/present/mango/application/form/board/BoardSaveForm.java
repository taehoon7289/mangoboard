package com.present.mango.application.form.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class BoardSaveForm {
  Integer boardNo;
  @NotEmpty String title;
  @NotEmpty String contents;
  List<String> images;
  List<MultipartFile> imageFiles;
//  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime uptDate;
}
