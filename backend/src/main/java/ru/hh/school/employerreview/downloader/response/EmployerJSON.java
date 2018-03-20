package ru.hh.school.employerreview.downloader.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.employer.Employer;

public class EmployerJSON {
  @JsonProperty("name")
  String name;
  @JsonProperty("url")
  String url;
  @JsonProperty("id")
  String id;
  @JsonProperty("area")
  String area;
  @JsonProperty("logo_urls")
  LogoUrlsJSON logo_urls;
  @JsonProperty("alternate_url")
  String alternateUrl;
  @JsonProperty("vacancies_url")
  String vacanciesUrl;
  @JsonProperty("open_vacancies")
  String openVacancies;

  public Employer toHibernateObj(){
    area = "113";
    Employer employer = new Employer(name , url, Integer.parseInt(id));
    employer.setAlternateUrl(alternateUrl);
    employer.setLogoUrl90(logo_urls.logo90);
    employer.setLogoUrl240(logo_urls.logo240);
    employer.setLogoUrlOriginal(logo_urls.logoOriginal);
    //employer.setAreaId();
    //employer.setDescription();
    //......
    return employer;
  }
}
