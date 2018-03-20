package ru.hh.school.employerreview.downloader.json;

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
    return found;
  }

  public int getPage() {
    return page;
  }

  public int getPages() {
    return pages;
  }

  public int getPerPage() {
    return perPage;
  }

  public EmployerJson[] getItems() {
    return items;
  }
}
