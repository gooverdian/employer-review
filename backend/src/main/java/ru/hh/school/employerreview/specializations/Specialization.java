package ru.hh.school.employerreview.specializations;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "specialization")
public class Specialization {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "hh_id")
  private String hhId;

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "proff_field_id")
  private ProfessionalField professionalField;

  public Specialization() {
  }

  public Specialization(String hhId, String name, ProfessionalField professionalField) {
    this.hhId = hhId;
    this.name = name;
    this.professionalField = professionalField;
  }

  public Specialization(Integer id) {
    this.id = id;
  }

  public Specialization(String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getHhId() {
    return hhId;
  }

  public void setHhId(String hhId) {
    this.hhId = hhId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProfessionalField getProfessionalField() {
    return professionalField;
  }

  public void setProfessionalField(ProfessionalField professionalField) {
    this.professionalField = professionalField;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Specialization that = (Specialization) o;

    return id != null ? id.equals(that.id) : that.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
