package com.present.mango.application.service;

import com.present.mango.application.domain.jooq.generate.tables.TblFileMaster;
import com.present.mango.common.constant.code.Const;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FileService {

  protected TblFileMaster tblFileMaster;

  /**
   * 멀티파트 파일 리스트 -> 파일 생성
   * @param files
   * @return
   */
  public List<File> makeFiles(List<MultipartFile> files) {
    File baseDirectory = new File(Const.BASE_DIR);
    if (!baseDirectory.exists()) {
      baseDirectory.mkdirs();
    }
    return files.stream().map(file -> {
      String filePath = Const.BASE_DIR + "\\" + file.getOriginalFilename();
      File uploadFile = new File(filePath);
      try {
        file.transferTo(uploadFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return uploadFile;
    }).collect(Collectors.toList());
  }

}
