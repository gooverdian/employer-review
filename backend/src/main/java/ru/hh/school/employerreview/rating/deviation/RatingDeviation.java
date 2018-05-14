package ru.hh.school.employerreview.rating.deviation;

import ru.hh.school.employerreview.employer.Employer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Column;
import java.io.Serializable;

@Entity
@Table(name = "rating_deviation")
public class RatingDeviation implements Serializable {

  @Id
  @OneToOne
  @JoinColumn(name = "employer_id")
  Employer employer;

  @Column(name = "deviation")
  Float deviation;

  RatingDeviation() {
  }

  RatingDeviation(Employer employer, Float deviation) {
    this.employer = employer;
    this.deviation = deviation;
  }
}
