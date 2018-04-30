package ru.hh.school.employerreview.rating;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rating")
public class Rating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "rating")
  private Float rating;

  @Column(name = "people_rated")
  private Integer peopleRated;

  public Rating() {
    this.peopleRated = 0;
    this.rating = 0.f;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

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
}
