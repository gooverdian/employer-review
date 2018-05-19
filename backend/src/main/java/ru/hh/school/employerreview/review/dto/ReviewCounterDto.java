package ru.hh.school.employerreview.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewCounterDto {
  @JsonProperty("employer_id")
  private Integer employerId;

  @JsonProperty("counter")
  private Integer counter;

  public ReviewCounterDto() {
  }

  public ReviewCounterDto(Integer employerId, Integer counter) {
    this.counter = counter;
    this.employerId = employerId;
  }

  public Integer getCounter() {
    return counter;
  }

  public Integer getEmployerId() {
    return employerId;
  }
}
