package ru.hh.school.employerreview.review;

import org.hibernate.annotations.DynamicInsert;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.position.Position;
import ru.hh.school.employerreview.specializations.Specialization;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

  @ManyToMany(cascade = CascadeType.REMOVE)
  @JoinTable(
      name = "review_specialization",
      joinColumns = {@JoinColumn(name = "review_id")},
      inverseJoinColumns = {@JoinColumn(name = "specialization_id")}
  )
  private List<Specialization> specializations = new ArrayList<>();

  @Column(name = "rating")
  private Float rating;

  @Column(name = "created_on")
  private Timestamp createdOn;

  @Column(name = "text")
  private String text;

  @Enumerated(EnumType.STRING)
  @Column(name = "review_type")
  private ReviewType reviewType;

  @ManyToOne
  @JoinColumn(name = "position_id")
  private Position position;

  @Column(name = "salary")
  private Integer salary;

  @Column(name = "employment_duration")
  private Short employmentDuration;

  @Column(name = "employment_terminated")
  private Boolean employmentTerminated;
  public Review() {
  }

  public Review(Employer employer, Float rating, ReviewType reviewType, String text) {
    this.employer = employer;
    this.rating = rating;
    this.text = text;
    this.reviewType = reviewType;
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

  public ReviewType getReviewType() {
    return reviewType;
  }

  public void setReviewType(ReviewType reviewType) {
    this.reviewType = reviewType;
  }

  public List<Specialization> getSpecializations() {
    return specializations;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Integer getSalary() {
    return salary;
  }

  public void setSalary(Integer salary) {
    this.salary = salary;
  }

  public Short getEmploymentDuration() {
    return employmentDuration;
  }

  public void setEmploymentDuration(Short employmentDuration) {
    this.employmentDuration = employmentDuration;
  }

  public Boolean getEmploymentTerminated() {
    return employmentTerminated;
  }

  public void setEmploymentTerminated(Boolean employmentTerminated) {
    this.employmentTerminated = employmentTerminated;
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
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + employer.hashCode();
    result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
    result = 31 * result + text.hashCode();
    return result;
  }
}
