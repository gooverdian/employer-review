package ru.hh.school.employerdownloader.response;

import com.google.gson.annotations.SerializedName;
import ru.hh.school.employerreview.employer.Employer;

public class EmployerJSON {
  String name;
  String url;
  String id;
  String area;
  LogoUrlsJSON logo_urls;

  @SerializedName(value = "alternate_url")
  String alternateUrl;

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
