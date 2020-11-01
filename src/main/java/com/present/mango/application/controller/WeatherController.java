package com.present.mango.application.controller;


import com.present.mango.application.form.board.BoardSaveForm;
import com.present.mango.application.form.weather.WeatherForm;
import com.present.mango.application.service.BoardService;
import com.present.mango.application.service.WeatherService;
import com.present.mango.common.constant.dto.ResultDTO;
import com.present.mango.common.exception.CustomException;
import com.present.mango.common.token.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@CrossOrigin("*")
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/user/weathers")
public class WeatherController {

    JwtTokenProvider jwtTokenProvider;
    WeatherService weatherService;

    @GetMapping(value = "/")
    public ResultDTO getOneCall(@RequestHeader(value = "X-Auth-Token", required = false) String token,
                                @RequestParam(defaultValue = "36") Double lat,
                                @RequestParam(defaultValue = "128") Double lon) {
//    Map data = jwtTokenProvider.getData(token);
//    Integer userNo = (Integer) data.getOrDefault("userNo", -1);
        Map result = weatherService.getOneCall(lat, lon);
        return new ResultDTO(1, "", result);
    }

}
