package ru.hh.school.employerreview.employer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.rating.Rating;

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

  @JsonProperty("star1_5")
  private Integer star15;

  @JsonProperty("star2_5")
  private Integer star25;

  @JsonProperty("star3_5")
  private Integer star35;

  @JsonProperty("star4_5")
  private Integer star45;

  @JsonProperty("star0_5")
  private Integer star05;

  public void setId(Integer id) {
    this.id = id;
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

  public void setRating(Rating rating) {
    this.rating = rating.getRating();
    this.peopleRated = rating.getPeopleRated();
    this.star1 = rating.getStar1();
    this.star2 = rating.getStar2();
    this.star3 = rating.getStar3();
    this.star4 = rating.getStar4();
    this.star5 = rating.getStar5();
    this.star15 = rating.getStar15();
    this.star25 = rating.getStar25();
    this.star35 = rating.getStar35();
    this.star45 = rating.getStar45();
    this.star05 = rating.getStar05();
  }
}
