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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Service
public class WeatherService {

  @Value(value = "${const.open-weathermap-app-id}")
  private String appId;

  @Value(value = "${const.open-weathermap-url}")
  private String url;

  private final WeatherFunc weatherFunc;

  public WeatherService(WeatherFunc weatherFunc) {
    this.weatherFunc = weatherFunc;
  }

  /**
   * 날씨 데이터 받아오기
   * @return
   */
  public Map getOneCall(Double lat, Double lon) {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("lat", lat.toString());
    parameters.put("lon", lon.toString());
    parameters.put("units", "metric");
    parameters.put("appid", appId);
    Map result = new HashMap();
    try {
      result = weatherFunc.sendGet(url, parameters);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

}
