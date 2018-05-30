package ru.hh.school.employerreview.statistic.main;

import ru.hh.school.employerreview.specializations.ProfessionalField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Table(name = "main_page_review_count")
public class MainPageReviewCounter implements Serializable {

  @Id
  @OneToOne
  @JoinColumn(name = "proff_field_id")
  ProfessionalField professionalField;

  @Column(name = "counter")
  private Integer counter;

  MainPageReviewCounter() {
  }

  MainPageReviewCounter(ProfessionalField professionalField, Integer counter) {
    this.counter = counter;
    this.professionalField = professionalField;
  }

  public ProfessionalField getProfessionalField() {
    return professionalField;
  }

  public Integer getCounter() {
    return counter;
  }
}
