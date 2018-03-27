package ru.hh.school.employerreview.downloader.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.area.Area;

public class AreaJson {

  @JsonProperty("name")
  private String name;

  @JsonProperty("id")
  private String id;

  @JsonProperty("parent_id")
  private String parentId;

  @JsonProperty("areas")
  private AreaJson[] areas;

  public AreaJson[] getAreas() {
    return areas;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return name;
  }

  public String getParentId() {
    return parentId;
  }

  public Area toArea() {
    if (parentId == null) {
      parentId = "-1";
    }
    return new Area(name, Integer.parseInt(id), Integer.parseInt(parentId));
  }
}
