package ru.hh.school.employerreview.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseBodyReviewId {
  @JsonProperty("review_id")
  private Integer reviewId;

  public ResponseBodyReviewId() {
  }

  public Integer getReviewId() {
    return reviewId;
  }

  public ResponseBodyReviewId(Integer reviewId) {
    this.reviewId = reviewId;
  }

  public void setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
  }
}
