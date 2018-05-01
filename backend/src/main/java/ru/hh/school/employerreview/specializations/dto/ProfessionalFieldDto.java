package ru.hh.school.employerreview.specializations.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.specializations.ProfessionalField;
import ru.hh.school.employerreview.specializations.Specialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfessionalFieldDto {

  @JsonProperty("professional_field_id")
  private Integer professionalFieldId;

  private String name;

  private List<SpecializationDto> specializations = new ArrayList<>();

  public ProfessionalFieldDto() {
  }

  public ProfessionalFieldDto(Integer professionalFieldId, String name) {
    this.professionalFieldId = professionalFieldId;
    this.name = name;
  }

  public Integer getProfessionalFieldId() {
    return professionalFieldId;
  }

  public void setProfessionalFieldId(Integer professionalFieldId) {
    this.professionalFieldId = professionalFieldId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<SpecializationDto> getSpecializations() {
    return specializations;
  }

  public void setSpecializations(List<SpecializationDto> specializations) {
    this.specializations = specializations;
  }

  public static ProfessionalFieldDto fromProfessionalField(ProfessionalField professionalField) {
    return new ProfessionalFieldDto(professionalField.getId(), professionalField.getName());
  }

  public static List<ProfessionalFieldDto> specializationsToProfessionalFieldDtos(List<Specialization> specializations) {
    Map<Integer, ProfessionalFieldDto> professionalFieldDtos = new HashMap<>();

    for (Specialization specialization : specializations) {
      Integer id = specialization.getProfessionalField().getId();

      ProfessionalFieldDto professionalFieldDto = professionalFieldDtos.get(id);
      if (professionalFieldDto == null) {
        professionalFieldDto = ProfessionalFieldDto.fromProfessionalField(specialization.getProfessionalField());
        professionalFieldDtos.put(id, professionalFieldDto);
      }

      professionalFieldDto.getSpecializations().add(SpecializationDto.fromSpecialization(specialization));
    }

    return new ArrayList<ProfessionalFieldDto>(professionalFieldDtos.values());
  }
}
