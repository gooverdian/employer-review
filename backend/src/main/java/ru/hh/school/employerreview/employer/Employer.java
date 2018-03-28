package ru.hh.school.employerreview.employer;

import ru.hh.school.employerreview.search.json.EmployerItem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "employer")
public class Employer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "hh_id", unique = true)
  private Integer hhId;

  @Column(name = "name")
  private String name;

  @Column(name = "site_url")
  private String siteUrl;

  @Column(name = "description")
  private String description;

  @Column(name = "score")
  private Float score;

  @Column(name = "people_rated")
  private Integer peopleRated;

  @Column(name = "alternate_url")
  private String alternateUrl;

  @Column(name = "logo_url_90")
  private String logoUrl90;

  @Column(name = "logo_url_240")
  private String logoUrl240;

  @Column(name = "logo_url_original")
  private String logoUrlOriginal;

  @Column(name = "area_id")
  private Integer areaId;

  @Column(name = "area_name")
  private String areaName;

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public String getAreaName() {
    return areaName;
  }

  public Employer(String name, String siteUrl, int hhId) {
    this.name = name;
    this.siteUrl = siteUrl;
    this.hhId = hhId;
    peopleRated = 0;
  }

  Employer() {
  }

  public Integer getId() {
    return id;
  }

  public void setAreaId(Integer areaId) {
    this.areaId = areaId;
  }

  public Integer getAreaId() {
    return this.areaId;
  }

  public Integer getHhId() {
    return hhId;
  }

  public void setName(String name) {
    this.name = name;
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

  public void  setHhId(int hhId) {
    this.hhId = hhId;
  }

  public void  setSiteUrl(String siteUrl) {
    this.siteUrl = siteUrl;
  }

  public void  setAlternateUrl(String alternateUrl) {
    this.alternateUrl = alternateUrl;
  }

  public void  setLogoUrl90(String logoUrl90) {
    this.logoUrl90 = logoUrl90;
  }

  public void  setLogoUrl240(String logoUrl240) {
    this.logoUrl240 = logoUrl240;
  }

  public void  setLogoUrlOriginal(String logoUrlOriginal) {
    this.logoUrlOriginal = logoUrlOriginal;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null || getClass() != that.getClass()) {
      return false;
    }

    Employer thatUser = (Employer) that;
    return Objects.equals(id, thatUser.id)
        && Objects.equals(name, thatUser.name)
        && Objects.equals(areaId, thatUser.areaId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, areaId, name);
  }

  @Override
  public String toString() {
    return String.format("%s{id=%d, Name='%s', Area='%d'}",
            getClass().getSimpleName(), id, name, areaId);
  }

  public EmployerItem toJson() {
    EmployerItem employerItem = new EmployerItem();
    employerItem.setAreaId(areaId);
    employerItem.setHhId(hhId);
    employerItem.setName(name);
    employerItem.setLogoUrl(logoUrl90);
    employerItem.setUrl(siteUrl);
    employerItem.setAreaName(areaName);
    return employerItem;
  }
}

