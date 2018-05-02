package ru.hh.school.employerreview.employer.visit;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class EmployerVisitId implements Serializable {

  @Column(name = "employer_id")
  private Integer employerId;

  @Column(name = "date")
  private Date date;

  public EmployerVisitId() {
  }

  public EmployerVisitId(Integer employerId, Date date) {
    this.employerId = employerId;
    this.date = date;
  }

  public Integer getEmployerId() {
    return employerId;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null || getClass() != that.getClass()) {
      return false;
    }

    EmployerVisitId thatId = (EmployerVisitId) that;
    return Objects.equals(employerId, thatId.employerId)
        && Objects.equals(date, thatId.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employerId, date);
  }
}
