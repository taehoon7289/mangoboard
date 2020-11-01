package com.present.mango.application.service.function;

import com.present.mango.application.model.FileBean;
import com.present.mango.common.constant.code.Const;
import com.present.mango.common.constant.exception.FileConstException;
import com.present.mango.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class FileFunc {

    public FileBean multipartFileUpload(MultipartFile multipartFile) throws CustomException {
        log.info("multipartFile getContentType :: {}", multipartFile.getContentType());
        log.info("multipartFile getName :: {}", multipartFile.getName());
        log.info("multipartFile getOriginalFilename :: {}", multipartFile.getOriginalFilename());
        String originalName = multipartFile.getOriginalFilename();
        String ext = multipartFile.getContentType();
        String generateName = UUID.randomUUID().toString();
        long size = multipartFile.getSize();
        String path = String.format("%s/%s", Const.BASE_DIR, "origin");
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try {
            multipartFile.transferTo(new File(String.format("%s/%s", path, originalName)));
        } catch (IOException e) {
            throw new CustomException(FileConstException.NOT_TRANSFER_MULTIPART_FILE);
        }
        FileBean fileBean = new FileBean();
        return fileBean;
    }

}
