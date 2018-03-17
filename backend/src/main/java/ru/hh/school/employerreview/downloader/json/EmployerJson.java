package ru.hh.school.employerreview.downloader.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.employer.Employer;

public class EmployerJson {
  @JsonProperty("name")
  String name;

  @JsonProperty("url")
  String url;

  @JsonProperty("id")
  String id;

  @JsonProperty("logo_urls")
  LogoUrlsJson logo_urls;

  @JsonProperty("alternate_url")
  String alternateUrl;

  @JsonProperty("vacancies_url")
  String vacanciesUrl;

  @JsonProperty("open_vacancies")
  String openVacancies;

  public Employer toHibernateObj(int area){
    Employer employer = new Employer(name , url, Integer.parseInt(id));
    employer.setAlternateUrl(alternateUrl);
    employer.setLogoUrl90(logo_urls.logo90);
    employer.setLogoUrl240(logo_urls.logo240);
    employer.setLogoUrlOriginal(logo_urls.logoOriginal);
    employer.setAreaId(area);
    return employer;
  }
}
