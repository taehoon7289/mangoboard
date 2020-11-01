package com.present.mango.application.controller;


import com.present.mango.application.model.FileBean;
import com.present.mango.application.service.WeatherService;
import com.present.mango.application.service.function.FileFunc;
import com.present.mango.common.constant.dto.ResultDTO;
import com.present.mango.common.exception.CustomException;
import com.present.mango.common.token.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@CrossOrigin("*")
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/user/files")
public class FileController {

    JwtTokenProvider jwtTokenProvider;
    WeatherService weatherService;
    FileFunc fileFunc;

    @PostMapping(value = "/")
    public ResultDTO fileUpload(@RequestHeader(value = "X-Auth-Token", required = false) String token,
                                @RequestPart MultipartFile multipartFile) throws CustomException {
//    Map data = jwtTokenProvider.getData(token);
//    Integer userNo = (Integer) data.getOrDefault("userNo", -1);
        FileBean result = fileFunc.multipartFileUpload(multipartFile);
        return new ResultDTO(1, "", result);
    }

}
