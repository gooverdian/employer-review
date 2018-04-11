package ru.hh.school.employerreview.area;

import ru.hh.school.employerreview.downloader.dto.AreaJson;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "area")
public class Area {

  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "parent_id")
  private Integer parentId;

  @Column(name = "name")
  private String name;

  public Area(String name, int id, int parentId) {
    this.name = name;
    this.id = id;
    this.parentId = parentId;
  }

  Area() {

  }

  public Integer getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void  setParentId(int parentId) {
    this.parentId = parentId;
  }

  public Integer getParentId() {
    return parentId;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null || getClass() != that.getClass()) {
      return false;
    }

    Area thatUser = (Area) that;
    return Objects.equals(id, thatUser.id)
        && Objects.equals(name, thatUser.name)
        && Objects.equals(parentId, thatUser.parentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, parentId, name);
  }

  @Override
  public String toString() {
    return String.format("%s{id=%d, Name='%s', ParentId='%d'}",
            getClass().getSimpleName(), id, name, parentId);
  }

  public AreaJson toAreaJson() {
    AreaJson areaJson = new AreaJson();
    areaJson.setId(this.id);
    areaJson.setName(this.name);
    areaJson.setParentId(this.parentId);
    return areaJson;
  }
}

