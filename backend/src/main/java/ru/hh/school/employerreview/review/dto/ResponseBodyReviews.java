package ru.hh.school.employerreview.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseBodyReviews {
  private List<ReviewDto> reviews;
  Integer page;
  Integer pages;
  @JsonProperty("per_page")
  Integer perPage;

  public ResponseBodyReviews() {
  }

  public ResponseBodyReviews(List<ReviewDto> reviews, Integer page, Integer pages, Integer perPage) {
    this.reviews = reviews;
    this.page = page;
    this.pages = pages;
    this.perPage = perPage;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getPages() {
    return pages;
  }

  public void setPages(Integer pages) {
    this.pages = pages;
  }

  public Integer getPerPage() {
    return perPage;
  }

  public void setPerPage(Integer perPage) {
    this.perPage = perPage;
  }

  public List<ReviewDto> getReviews() {
    return reviews;
  }

  public void setReviews(List<ReviewDto> reviews) {
    this.reviews = reviews;
  }
}
