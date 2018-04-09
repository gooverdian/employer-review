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

  @JsonProperty("rating")
  private Float rating;

  @JsonProperty("star1")
  private Integer star1;

  @JsonProperty("star2")
  private Integer star2;

  @JsonProperty("star3")
  private Integer star3;

  @JsonProperty("star4")
  private Integer star4;

  @JsonProperty("star5")
  private Integer star5;

  public void setId(Integer id) {
    this.id = id;
  }

  public void setRating(Float rating) {
    this.rating = rating;
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

  public void setStar1(Integer star1) {
    this.star1 = star1;
  }

  public void setStar2(Integer star2) {
    this.star2 = star2;
  }

  public void setStar3(Integer star3) {
    this.star3 = star3;
  }

  public void setStar4(Integer star4) {
    this.star4 = star4;
  }

  public void setStar5(Integer star5) {
    this.star5 = star5;
  }
}
