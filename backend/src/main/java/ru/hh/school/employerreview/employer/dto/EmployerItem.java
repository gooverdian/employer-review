package ru.hh.school.employerreview.employer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.rating.Rating;

import java.util.Map;

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
  private Float averageRating;

  @JsonProperty("stars")
  private Map<Float, Integer> stars;

  public EmployerItem() {
  }

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
    if (rating != null) {
      averageRating = rating.getRating();
      peopleRated = rating.getPeopleRated();
    }
  }

  public Employer toEmployer() {
    Employer employer = new Employer(name, url, hhId);
    if (areaId != null) {
      employer.setArea(new Area(areaName, areaId, -1));
    }
    employer.setLogoUrl90(logoUrl);
    employer.setId(id);
    return employer;
  }

  public Integer getAreaId() {
    return areaId;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public Integer getHhId() {
    return hhId;
  }

  public Float getAverageRating() {
    return averageRating;
  }

  public void setStars(Map stars) {
    this.stars = stars;
  }
}
