package ru.hh.school.employerreview.employer.visit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployerVisitDto {

  @JsonProperty("name")
  private String name;

  @JsonProperty("url")
  private String url;

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("people_visited")
  private Integer peopleVisited;

  @JsonProperty("logo_url")
  private String logoUrl;

  public EmployerVisitDto() {
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setPeopleVisited(Integer peopleVisited) {
    this.peopleVisited = peopleVisited;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public Integer getPeopleVisited() {
    return peopleVisited;
  }
}
