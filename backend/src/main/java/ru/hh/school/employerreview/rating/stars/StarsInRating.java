package ru.hh.school.employerreview.rating.stars;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "stars_in_rating")
public class StarsInRating implements Serializable {

  @EmbeddedId
  private StarsInRatingId starsInRatingId;

  @Column(name = "star_counter")
  private int starCounter;

  public StarsInRating() {
    starsInRatingId = new StarsInRatingId();
    starCounter = 0;
  }

  public Integer getStarCounter() {
    return starCounter;
  }

  public Float getStarValue() {
    return starsInRatingId.getStarValue();
  }

  public void setStarValue(Float starValue) {
    starsInRatingId.setStarValue(starValue);
  }

  public void setEmployerId(Integer employerId) {
    starsInRatingId.setEmployerId(employerId);
  }

  public void setStarCounter(Integer starCounter) {
    this.starCounter = starCounter;
  }
}
