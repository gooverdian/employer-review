package ru.hh.school.employerreview.statistic.salary;

import ru.hh.school.employerreview.specializations.ProfessionalField;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "average_salary_in_employer_by_proff_field")
public class AverageSalaryEmployerByProffField {

  @EmbeddedId
  private AverageSalaryEmployerByProffFieldId compositeId;

  @Column(name = "salary")
  private Float salary;

  public Float getSalary() {
    return salary;
  }

  public Integer getEmployerId() {
    return compositeId.getEmployerId();
  }

  public ProfessionalField getProffField() {
    return compositeId.getProffField();
  }

  public AverageSalaryEmployerByProffField() {
  }

  public AverageSalaryEmployerByProffField(Integer employerId, ProfessionalField proffField, Float salary) {
    compositeId = new AverageSalaryEmployerByProffFieldId(employerId, proffField);
    this.salary = salary;
  }
}
