package ru.hh.school.employerreview.review.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Errors {
  private List<Error> errors;

  public void addError(Error error) {
    errors.add(error);
  }

  public Errors() {
    errors = new ArrayList<>();
  }

  public Error[] getErrors() {
    return errors.toArray(new Error[0]);
  }

  @JsonIgnore
  public boolean isEmpty() {
    return errors.isEmpty();
  }
}
