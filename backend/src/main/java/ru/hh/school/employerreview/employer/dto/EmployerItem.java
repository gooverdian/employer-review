package ru.hh.school.employerreview.employer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployerItem {

  @JsonProperty("name")
  private String name;

  @JsonProperty("url")
  private String url;

  @JsonProperty("hh_id")
  private Integer hhId;

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("logo_url")
  private String logoUrl;

  @JsonProperty("area_name")
  private String areaName;

  @JsonProperty("area_id")
  private Integer areaId;

  @JsonProperty("people_rated")
  private Integer peopleRated;

  @JsonProperty("score")
  private Float score;

  public void setId(Integer id) {
    this.id = id;
  }

  public void setScore(Float score) {
    this.score = score;
  }

  public void setPeopleRated(Integer peopleRated) {
    this.peopleRated = peopleRated;
  }

  public void setName(String name) {
      this.name = name;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setHhId(Integer hhId) {
    this.hhId = hhId;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public void setAreaId(Integer areaId) {
    this.areaId = areaId;
  }
}
