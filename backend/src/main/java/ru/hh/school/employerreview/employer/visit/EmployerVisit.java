package ru.hh.school.employerreview.employer.visit;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "employer_visit")
public class EmployerVisit {

  @EmbeddedId
  private EmployerVisitId employerVisitId;

  @Column(name = "visit_counter")
  private Integer visitCounter;

  @Column(name = "visit_before_date_total_counter")
  private Integer visitBeforeDateTotalCounter;

  public EmployerVisit() {
    employerVisitId = new EmployerVisitId();
    visitCounter = 0;
    visitBeforeDateTotalCounter = 0;
  }

  public EmployerVisit(Integer employerId, Date date) {
    employerVisitId = new EmployerVisitId(employerId, date);
    visitCounter = 0;
    visitBeforeDateTotalCounter = 0;
  }

  public Integer getVisitCounter() {
    return visitCounter;
  }

  public void setVisitCounter(Integer visitCounter) {
    this.visitCounter = visitCounter;
  }

  public Integer getEmployerId() {
    return employerVisitId.getEmployerId();
  }

  public void setVisitBeforeDateTotalCounter(Integer visitBeforeDateTotalCounter) {
    this.visitBeforeDateTotalCounter = visitBeforeDateTotalCounter;
  }

  public Integer getVisitBeforeDateTotalCounter() {
    return visitBeforeDateTotalCounter;
  }
}
