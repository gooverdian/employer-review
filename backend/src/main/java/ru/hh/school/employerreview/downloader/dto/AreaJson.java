package ru.hh.school.employerreview.downloader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.area.Area;

public class AreaJson {

  @JsonProperty("name")
  private String name;

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("parent_id")
  private Integer parentId;

  @JsonProperty("areas")
  private AreaJson[] areas;

  public AreaJson() {
  }

  public AreaJson[] getAreas() {
    return areas;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setParentId(int parentId) {
    this.parentId = parentId;
  }

  public Area toArea() {
    if (parentId == null) {
      parentId = -1;
    }
    return new Area(name, id, parentId);
  }
}
