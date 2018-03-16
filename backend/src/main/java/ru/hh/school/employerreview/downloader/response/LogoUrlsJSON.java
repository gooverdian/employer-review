package ru.hh.school.employerreview.downloader.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class LogoUrlsJSON {

  @JsonProperty("90")
  String logo90;

  @JsonProperty("240")
  String logo240;

  @JsonProperty("Original")
  String logoOriginal;
}
