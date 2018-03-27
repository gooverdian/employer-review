package ru.hh.school.employerreview.review;

import org.hibernate.annotations.DynamicInsert;
import ru.hh.school.employerreview.employer.Employer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "review")
@DynamicInsert
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "employer_id")
  private Employer employer;

  @Column(name = "rating")
  private Float rating;

  @Column(name = "created_on")
  private Timestamp createdOn;

  @Column(name = "text")
  private String text;

  public Review() {
  }

  public Review(Employer employer, Float rating, String text) {
    this.employer = employer;
    this.rating = rating;
    this.text = text;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Employer getEmployer() {
    return employer;
  }

  public void setEmployer(Employer employer) {
    this.employer = employer;
  }

  public Float getRating() {
    return rating;
  }

  public void setRating(Float rating) {
    this.rating = rating;
  }

  public Timestamp getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Timestamp createdOn) {
    this.createdOn = createdOn;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null
        || getClass() != o.getClass()) {
      return false;
    }

    Review review = (Review) o;

    if (!id.equals(review.id)) {
      return false;
    }
    return createdOn
        .equals(review.createdOn);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + createdOn.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Review{"
        + "id=" + id
        + ", employer=" + employer
        + ", rating=" + rating
        + ", createdOn=" + createdOn
        + ", text='" + text + '\''
        + '}';
  }
}
