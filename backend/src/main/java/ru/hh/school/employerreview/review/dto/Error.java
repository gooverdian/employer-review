package ru.hh.school.employerreview.review.dto;

public class Error {
  private ErrorType type;
  private String value;

  public ErrorType getType() {
    return type;
  }

  public void setType(ErrorType type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Error() {
  }

  public Error(ErrorType type, String value) {
    this.type = type;
    this.value = value;
  }

  @Override
  public String toString() {
    return "Error{"
        + "type='" + type
        + '\''
        + ", value='" + value
        + '\''
        + '}';
  }
}
