package ru.hh.school.employerreview.downloader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseJson {

  @JsonProperty("per_page")
  private int perPage;

  @JsonProperty("items")
  private EmployerJson[] items;

  @JsonProperty("page")
  private int page;

  @JsonProperty("pages")
  private int pages;

  @JsonProperty("found")
  private int found;

  public int getFound() {
    return this.found;
  }

  public int getPage() {
    return this.page;
  }

  public int getPages() {
    return this.pages;
  }

  public int getPerPage() {
    return this.perPage;
  }

  public EmployerJson[] getItems() {
    return this.items;
  }
}
