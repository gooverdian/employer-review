package ru.hh.school.employerreview.employer;

import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.employer.dto.EmployerItem;
import ru.hh.school.employerreview.employer.visit.EmployerVisitDto;
import ru.hh.school.employerreview.rating.Rating;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "employer")
public class Employer implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "hh_id", unique = true)
  private Integer hhId;

  @Column(name = "name")
  private String name;

  @Column(name = "site_url")
  private String siteUrl;

  @Column(name = "description")
  private String description;

  @Column(name = "alternate_url")
  private String alternateUrl;

  @Column(name = "logo_url_90")
  private String logoUrl90;

  @Column(name = "logo_url_240")
  private String logoUrl240;

  @Column(name = "logo_url_original")
  private String logoUrlOriginal;

  @ManyToOne
  @JoinColumn(name = "area_id")
  private Area area;

  @OneToOne
  @JoinColumn(name = "rating_id")
  private Rating rating;

  public Employer(String name, String siteUrl, Integer hhId) {
    this.name = name;
    this.siteUrl = siteUrl;
    this.hhId = hhId;
  }

  public Employer() {
  }

  public Employer(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setArea(Area area) {
    this.area = area;
  }

  public Area getArea() {
    return this.area;
  }

  public Integer getHhId() {
    return hhId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public String getAlternateUrl() {
    return alternateUrl;
  }

  public String getLogoUrl90() {
    return logoUrl90;
  }

  public String getLogoUrl240() {
    return logoUrl240;
  }

  public String getLogoUrlOriginal() {
    return logoUrlOriginal;
  }

  public String getName() {
    return name;
  }

  public String getSiteUrl() {
    return siteUrl;
  }

  public void setHhId(int hhId) {
    this.hhId = hhId;
  }

  public void setSiteUrl(String siteUrl) {
    this.siteUrl = siteUrl;
  }

  public void setAlternateUrl(String alternateUrl) {
    this.alternateUrl = alternateUrl;
  }

  public void setLogoUrl90(String logoUrl90) {
    this.logoUrl90 = logoUrl90;
  }

  public void setLogoUrl240(String logoUrl240) {
    this.logoUrl240 = logoUrl240;
  }

  public void setLogoUrlOriginal(String logoUrlOriginal) {
    this.logoUrlOriginal = logoUrlOriginal;
  }

  public void setRating(Rating rating) {
    this.rating = rating;
  }

  public Rating getRating() {
    return rating;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null || getClass() != that.getClass()) {
      return false;
    }

    Employer thatEmployer = (Employer) that;
    return Objects.equals(id, thatEmployer.id)
        && Objects.equals(name, thatEmployer.name)
        && Objects.equals(area, thatEmployer.area);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return String.format("%s{id=%d, Name='%s'}",
        getClass().getSimpleName(), id, name);
  }

  public EmployerItem toEmployerItem() {
    EmployerItem employerItem = new EmployerItem();
    employerItem.setHhId(hhId);
    employerItem.setName(name);
    employerItem.setLogoUrl(logoUrl90);
    employerItem.setUrl(siteUrl);
    employerItem.setId(id);
    if (area != null) {
      employerItem.setAreaId(area.getId());
      employerItem.setAreaName(area.getName());
    }
    if (rating != null) {
      employerItem.setRating(rating);
    }
    return employerItem;
  }

  public EmployerVisitDto toEmployerVisitDto() {
    EmployerVisitDto employerVisitDto = new EmployerVisitDto();
    employerVisitDto.setLogoUrl(logoUrl90);
    employerVisitDto.setId(id);
    employerVisitDto.setName(name);
    employerVisitDto.setUrl(siteUrl);
    return employerVisitDto;
  }
}
