package ru.hh.school.employerreview.statistic.employment;

import ru.hh.school.employerreview.specializations.ProfessionalField;
import ru.hh.school.employerreview.statistic.CompositeEmployerProffFieldId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "duration_by_proff_field")
public class DurationByProffField {

  @EmbeddedId
  private CompositeEmployerProffFieldId compositeId;

  @Column(name = "duration")
  private Float duration;

  DurationByProffField(Integer employerId, ProfessionalField proffField, Float duration) {
    compositeId = new CompositeEmployerProffFieldId(employerId, proffField);
    this.duration = duration;
  }

  DurationByProffField() {
  }

  public Float getDuration() {
    return duration;
  }

  public Integer getEmployerId() {
    return compositeId.getEmployerId();
  }

  public ProfessionalField getProffField() {
    return compositeId.getProffField();
  }
}
