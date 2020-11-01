package com.present.mango.application.service.function;

import com.present.mango.application.model.FileBean;
import com.present.mango.common.constant.code.Const;
import com.present.mango.common.constant.exception.FileConstException;
import com.present.mango.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
        String originName = multipartFile.getOriginalFilename();
        String generateName = UUID.randomUUID().toString();
        String ext = "";
        if (originName.lastIndexOf(".") > -1) {
            ext = originName.substring(originName.lastIndexOf(".") + 1);
        }
        long size = multipartFile.getSize();
        String contentType = multipartFile.getContentType();
        String basePath = String.format("%s/%s", Const.BASE_DIR, "origin");
        File folder = new File(basePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = String.format("%s/%s", String.format("%s/%s", Const.BASE_DIR, "origin"), String.format("%s.%s", generateName, ext));
        try {
            File file = new File(path);
            multipartFile.transferTo(file);
            if (contentType.indexOf("image") > -1) {
                try {
                    this.makeThumbnail(generateName, ext, file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new CustomException(FileConstException.NOT_TRANSFER_MULTIPART_FILE);
        }
        FileBean fileBean = new FileBean();
        fileBean.setOriginName(originName);
        fileBean.setGenerateName(generateName);
        fileBean.setPath(path);
        fileBean.setExt(ext);
        fileBean.setSize(size);
        fileBean.setContentType(contentType);

        return fileBean;
    }

    public void makeThumbnail(String originName, String ext, File file) throws Exception {
        BufferedImage srcImg = ImageIO.read(file);
        for (Integer size : thumbnailSize) {
            BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, size);
            String basePath = String.format("%s/%d", Const.BASE_DIR, size);
            File thumbFile = new File(String.format("%s/%s.%s", basePath, originName, ext));
            File folder = new File(basePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            ImageIO.write(destImg, ext, thumbFile);
        }
    }

}
