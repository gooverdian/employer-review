package ru.hh.school.employerreview.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.position.Position;
import ru.hh.school.employerreview.review.Review;
import ru.hh.school.employerreview.review.ReviewType;
import ru.hh.school.employerreview.specializations.Specialization;
import ru.hh.school.employerreview.specializations.dto.SpecializationDto;

import java.util.ArrayList;
import java.util.List;

public class ReviewDto {
  @JsonProperty("employer_id")
  private Integer employerId;

  @JsonProperty("review_id")
  private Integer reviewId;

  @JsonProperty("review_type")
  private ReviewType reviewType;

  private Float rating;
  private String text;

  @JsonProperty("position_id")
  private Integer positionId;

  private Integer salary;

  @JsonProperty("employment_duration")
  private Short employmentDuration;

  @JsonProperty("employment_terminated")
  private Boolean employmentTerminated;

  private List<SpecializationDto> specializations = new ArrayList<>();

  public ReviewDto() {
  }

  public ReviewDto(Integer employerId, Integer reviewId, Float rating, ReviewType reviewType, String text) {
    this.employerId = employerId;
    this.reviewId = reviewId;
    this.rating = rating;
    this.text = text;
    this.reviewType = reviewType;
  }

  public ReviewType getReviewType() {
    return reviewType;
  }

  public void setReviewType(ReviewType reviewType) {
    this.reviewType = reviewType;
  }

  public Integer getReviewId() {
    return reviewId;
  }

  public void setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
  }

  public Integer getEmployerId() {
    return employerId;
  }

  public void setEmployerId(Integer employerId) {
    this.employerId = employerId;
  }

  public Float getRating() {
    return rating;
  }

  public void setRating(Float rating) {
    this.rating = rating;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Integer getPositionId() {
    return positionId;
  }

  public void setPositionId(Integer positionId) {
    this.positionId = positionId;
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

  public List<SpecializationDto> getSpecializations() {
    return specializations;
  }

  public void setSpecializations(List<SpecializationDto> specializations) {
    this.specializations = specializations;
  }

  @Override
  public String toString() {
    return "ReviewDto{" +
        "employerId=" + employerId +
        ", reviewId=" + reviewId +
        ", reviewType=" + reviewType +
        ", rating=" + rating +
        ", text='" + text + '\'' +
        '}';
  }

  public Review toReview() {
    Review review = new Review();
    review.setId(reviewId);
    review.setReviewType(reviewType);
    review.setRating(rating);
    review.setText(text);
    review.setEmploymentDuration(employmentDuration);
    review.setEmploymentTerminated(employmentTerminated);
    review.setSalary(salary);

    review.setEmployer(new Employer(employerId));
    if (positionId != null) {
      review.setPosition(new Position(positionId));
    }

    List<Specialization> specializations = new ArrayList<>();
    this.specializations.forEach(specializationDto ->
        specializations.add(new Specialization(specializationDto.getSpecializationId())));
    review.getSpecializations().addAll(specializations);

    return review;
  }
}
