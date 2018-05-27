package ru.hh.school.employerreview.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ResponseBodyReviews {
  @JsonProperty("reviews")
  private List<ReviewDto> reviews = new ArrayList<>();
  @JsonProperty("page")
  private Integer page = 0;
  @JsonProperty("pages")
  private Integer pages = 0;
  @JsonProperty("per_page")
  private Integer perPage = 0;
  @JsonProperty("found")
  private Integer found = 0;

  public ResponseBodyReviews() {
  }

  public ResponseBodyReviews(List<ReviewDto> reviews, Integer page, Integer pages, Integer perPage, Integer found) {
    this.reviews = reviews;
    this.page = page;
    this.pages = pages;
    this.perPage = perPage;
    this.found = found;
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
