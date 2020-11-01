package com.present.mango.application.model;

import lombok.Data;

@Data
public class FileBean {

    private Integer no;
    private String originName;
    private String generateName;
    private String path;
    private Double size;
    private Double ext;

}
