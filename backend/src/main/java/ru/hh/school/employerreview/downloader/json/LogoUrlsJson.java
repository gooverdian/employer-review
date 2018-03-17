package ru.hh.school.employerreview.downloader.json;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LogoUrlsJson {

  @JsonProperty("90")
  String logo90;

  @JsonProperty("240")
  String logo240;

  @JsonProperty("Original")
  String logoOriginal;
}
