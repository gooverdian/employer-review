package ru.hh.school.employerreview.employer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class EmployersResponse {

  @JsonProperty("per_page")
  private int perPage;

  @JsonProperty("pages")
  private int pages;

  @JsonProperty("items")
  private List<EmployerItem> items;

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

  public void setItems(List<EmployerItem> employerItems) {
    items = employerItems;
  }

  public void setFound(int found) {
    this.found = found;
  }

  public int getFound() {
    return found;
  }

  public EmployersResponse() {

  }

  public EmployersResponse(List<EmployerItem> items, int page, int perPage, int found, int pages) {
    setItems(items);
    setPerPage(perPage);
    setPage(page);
    setFound(found);
    setPages(pages);
  }
}

