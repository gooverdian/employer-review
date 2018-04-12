package ru.hh.school.employerreview.area.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.downloader.dto.AreaJson;

import java.util.List;

public class AreaResponse {

  public AreaResponse() {
    found = 0;
    page = 0;
    pages = 0;
    perPage = 0;
  }

  public AreaResponse(List<AreaJson> items, int page, int perPage, int found, int pages) {
    setItems(items);
    setPerPage(perPage);
    setPage(page);
    setFound(found);
    setPages(pages);
  }

  @JsonProperty("per_page")
  private int perPage;

  @JsonProperty("pages")
  private int pages;

  @JsonProperty("items")
  private List<AreaJson> items;

  @JsonProperty("page")
  private int page;

  @JsonProperty("found")
  private int found;

  public void setPerPage(int perPage) {
    this.perPage = perPage;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public void setItems(List<AreaJson> items) {
    this.items = items;
  }

  public void setFound(int found) {
    this.found = found;
  }
}
