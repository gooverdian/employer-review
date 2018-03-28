package ru.hh.school.employerreview.search.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployerItem {

  @JsonProperty("name")
  String name;

  @JsonProperty("url")
  String url;

  @JsonProperty("hh_id")
  int hhId;

  @JsonProperty("logo_url")
  String logoUrl;

  @JsonProperty("area_name")
  String areaName;

  @JsonProperty("area_id")
  int areaId;

  @JsonProperty("people_rated")
  private int peopleRated;

  @JsonProperty("score")
  private Float score;

  public void setScore(Float score) {
    this.score = score;
  }

  public void setPeopleRated(int peopleRated) {
    this.peopleRated = peopleRated;
  }

  public void setName(String name) {
      this.name = name;
  }

  public  void setUrl(String url) {
    this.url = url;
  }

  public  void setHhId(int hhId) {
    this.hhId = hhId;
  }

  public  void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public void setAreaId(int areaId) {
    this.areaId = areaId;
  }
}
