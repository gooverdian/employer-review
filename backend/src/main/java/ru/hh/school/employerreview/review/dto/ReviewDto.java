package ru.hh.school.employerreview.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.review.Review;
import ru.hh.school.employerreview.review.ReviewType;

public class ReviewDto {
  @JsonProperty("employer_id")
  private Integer employerId;

  @JsonProperty("review_id")
  private Integer reviewId;

  @JsonProperty("review_type")
  private ReviewType reviewType;

  private Float rating;
  private String text;

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

  public ReviewDto() {
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
    Employer employer = new Employer();
    employer.setId(employerId);
    review.setEmployer(employer);
    review.setId(reviewId);
    review.setType(reviewType);
    review.setRating(rating);
    review.setText(text);
    return review;
  }

}
