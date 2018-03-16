package ru.hh.school.employerreview.downloader.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseJSON {
  @JsonProperty("per_page")
  public int perPage;
  @JsonProperty("items")
  public EmployerJSON[] items;
  @JsonProperty("page")
  public int page;
  @JsonProperty("pages")
  public int pages;
  @JsonProperty("found")
  public int found;
}
