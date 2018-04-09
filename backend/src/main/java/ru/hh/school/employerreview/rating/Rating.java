package ru.hh.school.employerreview.rating;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rating")
public class Rating {

  @Id
  @Column(name = "employer_id")
  private Integer employerId;

  @Column(name = "rating")
  private Float rating;

  @Column(name = "people_rated")
  private Integer peopleRated;

  @Column(name = "star1")
  private Integer star1;

  @Column(name = "star2")
  private Integer star2;

  @Column(name = "star3")
  private Integer star3;

  @Column(name = "star4")
  private Integer star4;

  @Column(name = "star5")
  private Integer star5;

  public void setRating(Float rating) {
    this.rating = rating;
  }

  public void setPeopleRated(Integer peopleRated) {
    this.peopleRated = peopleRated;
  }

  public Integer getPeopleRated() {
    return this.peopleRated;
  }

  public Float getRating() {
    return this.rating;
  }

  public Integer getStar1() {
    return star1;
  }

  public Integer getStar2() {
    return star2;
  }

  public Integer getStar3() {
    return star3;
  }

  public Integer getStar4() {
    return star4;
  }

  public Integer getStar5() {
    return star5;
  }

  public void setStar1(Integer star1) {
    this.star1 = star1;
  }

  public void setStar2(Integer star2) {
    this.star2 = star2;
  }

  public void setStar3(Integer star3) {
    this.star3 = star3;
  }

  public void setStar4(Integer star4) {
    this.star4 = star4;
  }

  public void setStar5(Integer star5) {
    this.star5 = star5;
  }

  Rating() {

  }

  Rating(Integer employerId) {
    this.employerId = employerId;
    this.peopleRated = 0;
    this.rating = 0.f;
    this.star1 = 0;
    this.star2 = 0;
    this.star3 = 0;
    this.star4 = 0;
    this.star5 = 0;
  }

}

