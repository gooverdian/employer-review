package ru.hh.school.employerreview.specializations.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.specializations.Specialization;

public class SpecializationDto {

  @JsonProperty("specialization_id")
  private Integer specializationId;

  private String name;

  public SpecializationDto() {
  }

  public SpecializationDto(Integer specializationId, String name) {
    this.specializationId = specializationId;
    this.name = name;
  }

  public Integer getSpecializationId() {
    return specializationId;
  }

  public void setSpecializationId(Integer specializationId) {
    this.specializationId = specializationId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static SpecializationDto fromSpecialization(Specialization specialization) {
    return new SpecializationDto(specialization.getId(), specialization.getName());
  }
}
