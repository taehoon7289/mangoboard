package com.present.mango.application.form.weather;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class WeatherForm {
  @NotEmpty String lat;
  @NotEmpty String lon;
  @NotEmpty String exclude;
}
