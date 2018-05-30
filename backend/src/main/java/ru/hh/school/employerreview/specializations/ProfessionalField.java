package ru.hh.school.employerreview.specializations;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proff_field")
public class ProfessionalField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "hh_id")
  private Integer hhId;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "professionalField", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<Specialization> specializations = new ArrayList<>();

  public ProfessionalField() {
  }

  public ProfessionalField(Integer hhId, String name) {
    this.hhId = hhId;
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getHhId() {
    return hhId;
  }

  public void setHhId(Integer hhId) {
    this.hhId = hhId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Specialization> getSpecializations() {
    return specializations;
  }
}
