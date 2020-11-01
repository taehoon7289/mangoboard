package com.present.mango.application.model;

import lombok.Data;

@Data
public class FileBean {

    private String originName;
    private String generateName;
    private String path;
    private Long size;
    private String ext;
    private String contentType;

}
