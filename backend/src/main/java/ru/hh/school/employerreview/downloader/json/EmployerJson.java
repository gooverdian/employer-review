package ru.hh.school.employerreview.downloader.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.employer.Employer;

public class EmployerJson {
  @JsonProperty("name")
  private String name;

  @JsonProperty("url")
  private String url;

  @JsonProperty("id")
  private String id;

  @JsonProperty("logo_urls")
  private LogoUrlsJson logo_urls;

  @JsonProperty("alternate_url")
  private String alternateUrl;

  @JsonProperty("vacancies_url")
  private String vacanciesUrl;

  @JsonProperty("open_vacancies")
  private String openVacancies;

  public Employer toHibernateObj(int area, String areaName){
    Employer employer = new Employer(name , url, Integer.parseInt(id));
    employer.setAlternateUrl(alternateUrl);
    employer.setLogoUrl90(logo_urls.getLogo90());
    employer.setLogoUrl240(logo_urls.getLogo240());
    employer.setLogoUrlOriginal(logo_urls.getLogoOriginal());
    employer.setAreaId(area);
    employer.setAreaName(areaName);
    return employer;
  }
}
