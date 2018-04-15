package ru.hh.school.employerreview.downloader.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfessionalFieldJson {
  private int id;
  private String name;
  private List<SpecializationJson> specializations;

  public ProfessionalFieldJson() {
    this.specializations = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<SpecializationJson> getSpecializations() {
    return specializations;
  }

  public void setSpecializations(List<SpecializationJson> specializations) {
    this.specializations = specializations;
  }
}
