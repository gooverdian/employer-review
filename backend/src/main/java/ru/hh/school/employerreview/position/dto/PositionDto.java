package ru.hh.school.employerreview.position.dto;

import ru.hh.school.employerreview.position.Position;

public class PositionDto {

  private Integer id;

  private String name;

  public PositionDto() {
  }

  public PositionDto(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public PositionDto(String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static PositionDto positionToDto(Position position) {
    return new PositionDto(position.getId(), position.getName());
  }
}
