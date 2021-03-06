package ru.hh.school.employerreview.statistic;

import ru.hh.school.employerreview.specializations.ProfessionalField;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
public class CompositeEmployerProffFieldId implements Serializable {

  @Column(name = "employer_id")
  private Integer employerId;

  @OneToOne
  @JoinColumn(name = "proff_field_id")
  private ProfessionalField proffFieldId;

  public CompositeEmployerProffFieldId() {
  }

  public CompositeEmployerProffFieldId(Integer employerId, ProfessionalField proffFieldId) {
    this.employerId = employerId;
    this.proffFieldId = proffFieldId;
  }

  public Integer getEmployerId() {
    return employerId;
  }

  public ProfessionalField getProffField() {
    return proffFieldId;
  }
}
