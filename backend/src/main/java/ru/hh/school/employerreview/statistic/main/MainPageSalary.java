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
@Table(name = "main_page_salary")
public class MainPageSalary implements Serializable {

  @Id
  @OneToOne
  @JoinColumn(name = "proff_field_id")
  private ProfessionalField professionalField;

  @Column(name = "salary")
  private Float salary;

  MainPageSalary() {
  }

  MainPageSalary(ProfessionalField professionalField, Float salary) {
    this.professionalField = professionalField;
    this.salary = salary;
  }

  public Float getSalary() {
    return salary;
  }

  public ProfessionalField getProfessionalField() {
    return professionalField;
  }
}
