package com.present.mango.application.service.function;

import com.present.mango.application.model.FileBean;
import com.present.mango.common.constant.code.Const;
import com.present.mango.common.constant.exception.FileConstException;
import com.present.mango.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileFunc {

    @Value("${const.thumbnail-size}")
    List<Integer> thumbnailSize = new ArrayList<>();

    public FileBean multipartFileUpload(MultipartFile multipartFile) throws CustomException {
        String originalName = multipartFile.getOriginalFilename();
        String generateName = UUID.randomUUID().toString();
        String ext = "";
        if (originalName.lastIndexOf(".") > -1) {
            ext = originalName.substring(originalName.lastIndexOf(".") + 1);
            generateName = String.format("%s.%s", generateName, ext);
        }
        long size = multipartFile.getSize();
        String contentType = multipartFile.getContentType();
        String basePath = String.format("%s/%s", Const.BASE_DIR, "origin");
        File folder = new File(basePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = String.format("%s/%s", basePath, generateName);
        try {
            File file = new File(path);
            multipartFile.transferTo(file);
            if (contentType.indexOf("image") > -1) {
                this.makeThumbnail(file);
            }
        } catch (IOException e) {
            throw new CustomException(FileConstException.NOT_TRANSFER_MULTIPART_FILE);
        }
        FileBean fileBean = new FileBean();
        fileBean.setOriginName(originalName);
        fileBean.setGenerateName(generateName);
        fileBean.setPath(path);
        fileBean.setExt(ext);
        fileBean.setSize(size);
        fileBean.setContentType(contentType);

        return fileBean;
    }

    public void makeThumbnail(File file) {
        log.info("이미지임. :: {}", file);
        for (Integer size : thumbnailSize) {
            log.info("size :: {}", size);
        }
    }

}
