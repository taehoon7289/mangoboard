package com.present.mango.application.service;

import com.present.mango.application.domain.jooq.command.BoardCommand;
import com.present.mango.application.domain.jooq.generate.tables.pojos.TblBoardMasterBean;
import com.present.mango.application.domain.jooq.generate.tables.pojos.TblUserMasterBean;
import com.present.mango.application.domain.jooq.generate.tables.records.TblBoardMasterRecord;
import com.present.mango.application.domain.jooq.generate.tables.records.TblUserMasterRecord;
import com.present.mango.application.domain.jooq.query.BoardQuery;
import com.present.mango.application.form.board.BoardSaveForm;
import com.present.mango.application.service.function.WeatherFunc;
import com.present.mango.common.constant.exception.BoardConstException;
import com.present.mango.common.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class WeatherService {

  WeatherFunc weatherFunc;

  /**
   * 날씨 데이터 받아오기
   * @return
   */
  public Map getOneCall() {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("lat", "36");
    parameters.put("lon", "128");
    parameters.put("units", "metric");
    parameters.put("appid", "a5c6b2491ef8a7b7d340299797f576df");
    Map result = new HashMap();
    try {
      result = weatherFunc.sendGet("https://api.openweathermap.org/data/2.5/onecall", parameters);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

}
