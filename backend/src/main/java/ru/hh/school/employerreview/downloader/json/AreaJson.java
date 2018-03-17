package ru.hh.school.employerreview.downloader.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.employerreview.area.Area;

public class AreaJson {

  @JsonProperty("name")
  public String name;

  @JsonProperty("id")
  public String id;

  @JsonProperty("parent_id")
  public String parentId;

  @JsonProperty("areas")
  public AreaJson[] areas;

  public Area toArea(){
    if (parentId == null){
      parentId = "-1";
    }
    Area area = new Area(name, Integer.parseInt(parentId));
    return area;
  }
}
