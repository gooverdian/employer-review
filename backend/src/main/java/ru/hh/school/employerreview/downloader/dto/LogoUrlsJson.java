package ru.hh.school.employerreview.downloader.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LogoUrlsJson {

  @JsonProperty("90")
  private String logo90;

  @JsonProperty("240")
  private String logo240;

  @JsonProperty("Original")
  private String logoOriginal;

  public String getLogo90() {
    return logo90;
  }

  public String getLogo240() {
    return logo240;
  }

  public String getLogoOriginal() {
    return logoOriginal;
  }
}
