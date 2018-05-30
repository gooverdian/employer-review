package ru.hh.school.employerreview.statistic.main;

import ru.hh.school.employerreview.specializations.ProfessionalField;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import java.io.Serializable;

@Entity
@Table(name = "main_page_employment")
public class MainPageEmployment implements Serializable {

  @Id
  @OneToOne
  @JoinColumn(name = "proff_field_id")
  private ProfessionalField professionalField;

  @Column(name = "duration")
  private Float duration;

  MainPageEmployment() {
  }

  MainPageEmployment(ProfessionalField professionalField, Float duration) {
    this.professionalField = professionalField;
    this.duration = duration;
  }

  public Float getDuration() {
    return duration;
  }

  public ProfessionalField getProfessionalField() {
    return professionalField;
  }
}
