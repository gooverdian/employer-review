package ru.hh.school.employerreview.search.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.employer.Employer;
import java.util.ArrayList;
import java.util.List;

public class EmployersResponse {

  public EmployersResponse(List<Employer> resultsFromDB, int page, int perPage, int found, int pages) {

    List<EmployerItem> items = new ArrayList<>();
    for (Employer item : resultsFromDB) {
      items.add(item.toJson());
    }
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

  public List<EmployerItem> getItems() {
    return items;
  }
}

