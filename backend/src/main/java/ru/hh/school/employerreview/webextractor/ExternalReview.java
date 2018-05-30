package ru.hh.school.employerreview.webextractor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "external_review")
public class ExternalReview {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(name = "positivity")
  private ExternalReviewPositivity externalReviewPositivity;

  @Column(name = "text")
  private String text;

  public ExternalReview() {
  }

  public ExternalReview(ExternalReviewPositivity externalReviewPositivity, String text) {
    this.externalReviewPositivity = externalReviewPositivity;
    this.text = text;
  }

  public ExternalReviewPositivity getExternalReviewPositivity() {
    return externalReviewPositivity;
  }

  public void setExternalReviewPositivity(ExternalReviewPositivity externalReviewPositivity) {
    this.externalReviewPositivity = externalReviewPositivity;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Integer getId() {
    return id;
  }
}
