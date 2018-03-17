package ru.hh.school.employerreview.downloader.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseJson {
  @JsonProperty("per_page")
  public int perPage;

  @JsonProperty("items")
  public EmployerJson[] items;

  @JsonProperty("page")
  public int page;

  @JsonProperty("pages")
  public int pages;

  @JsonProperty("found")
  public int found;
}
