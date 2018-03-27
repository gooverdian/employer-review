package ru.hh.school.employerreview.review.dto;

public class ReviewDto {
  private Integer employerId;
  private Float rating;
  private String text;

  public ReviewDto(Integer employerId, Float rating, String text) {
    this.employerId = employerId;
    this.rating = rating;
    this.text = text;
  }

  public ReviewDto() {
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
    return "ReviewDto{"
        + "employerId=" + employerId
        + ", rating=" + rating
        + ", text='" + text
        + '\''
        + '}';
  }
}
