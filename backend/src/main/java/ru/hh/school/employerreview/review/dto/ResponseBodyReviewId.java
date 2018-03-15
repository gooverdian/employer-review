package ru.hh.school.employerreview.review.dto;

public class ResponseBodyReviewId {
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
