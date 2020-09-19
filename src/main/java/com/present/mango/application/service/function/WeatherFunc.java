package com.present.mango.application.service.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.present.mango.application.domain.jooq.generate.tables.pojos.TblUserMasterBean;
import com.present.mango.application.dto.token.TokenDTO;
import com.present.mango.application.form.sign.UserSignInForm;
import com.present.mango.application.form.sign.UserSignUpForm;
import com.present.mango.application.validation.UserValidation;
import com.present.mango.common.constant.code.TokenCode;
import com.present.mango.common.constant.code.UserCode;
import com.present.mango.common.constant.exception.UserConstException;
import com.present.mango.common.exception.CustomException;
import com.present.mango.common.token.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Component
public class WeatherFunc {

  public Map sendGet (String apiUrl, Map<String, String> parameters) throws IOException {
    apiUrl = String.format("%s?%s",apiUrl,ParameterStringBuilder.getParamsString(parameters));
    URL url = new URL(apiUrl);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("Content-Type", "application/json");
    con.setConnectTimeout(5000);
    con.setReadTimeout(5000);

    int responseCode = con.getResponseCode();
    log.info("Sending 'GET' request to URL : {}", url);
    log.info("Response Code : {}", responseCode);

    Charset charset = Charset.forName("UTF-8");
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),charset));
    String inputLine;
    StringBuffer response = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    log.info("조회결과 : {}", response.toString());
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(response.toString(), Map.class);
  }

  public static class ParameterStringBuilder {
    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
      StringBuilder result = new StringBuilder();

      for (Map.Entry<String, String> entry : params.entrySet()) {
        result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
        result.append("=");
        result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        result.append("&");
      }

      String resultString = result.toString();
      return resultString.length() > 0
              ? resultString.substring(0, resultString.length() - 1)
              : resultString;
    }
  }

}
