package ru.hh.school.employerreview.statistic.salary;

import ru.hh.school.employerreview.specializations.ProfessionalField;
import ru.hh.school.employerreview.statistic.CompositeEmployerProffFieldId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "employer_salary_statistics")
public class EmployerSalaryStatistics {

  @EmbeddedId
  private CompositeEmployerProffFieldId compositeId;

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

  public EmployerSalaryStatistics() {
  }

  public EmployerSalaryStatistics(Integer employerId, ProfessionalField proffField, Float salary) {
    compositeId = new CompositeEmployerProffFieldId(employerId, proffField);
    this.salary = salary;
  }
}
