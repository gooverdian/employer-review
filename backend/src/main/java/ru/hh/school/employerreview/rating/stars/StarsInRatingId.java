package ru.hh.school.employerreview.rating.stars;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StarsInRatingId implements Serializable {

  @Column(name = "employer_id")
  private Integer employerId;

  @Column(name = "star_value")
  private Float starValue;

  public StarsInRatingId() {
  }

  public StarsInRatingId(Integer employerId, Float starValue) {
    this.starValue = starValue;
    this.employerId = employerId;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null || getClass() != that.getClass()) {
      return false;
    }

    StarsInRatingId thatId = (StarsInRatingId) that;
    return Objects.equals(employerId, thatId.employerId)
        && Objects.equals(starValue, thatId.starValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employerId, starValue);
  }

  public Float getStarValue() {
    return starValue;
  }

  public Integer getEmployerId() {
    return employerId;
  }

  public void setEmployerId(Integer employerId) {
    this.employerId = employerId;
  }

  public void setStarValue(Float starValue) {
    this.starValue = starValue;
  }
}
