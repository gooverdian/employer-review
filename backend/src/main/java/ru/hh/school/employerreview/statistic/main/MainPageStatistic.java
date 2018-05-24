package ru.hh.school.employerreview.statistic.main;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.EnumType;
import javax.persistence.Column;

@Entity
@Table(name = "main_page_statistic")
public class MainPageStatistic {

  @Id
  @Enumerated(EnumType.STRING)
  @Column(name = "key")
  private MainPageStatisticType key;

  @Column(name = "value")
  private Integer value;

  public MainPageStatistic() {
    value = 0;
  }

  public MainPageStatistic(MainPageStatisticType key) {
    value = 0;
    this.key = key;
  }

  public void setKey(MainPageStatisticType key) {
    this.key = key;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }

  public MainPageStatisticType getKey() {
    return key;
  }
}
