package ru.hh.school.employerreview.specializations;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SpecializationDaoTest extends SpecializationsCommonTest {

  @Test
  public void getByIdTest() {
    List<Specialization> specializations = specializationDao.getAll();
    assertNotNull(specializations);
    assertTrue(specializations.size() > 0);
    assertNotNull(specializationDao.getById(specializations.get(0).getId()));
  }

  @Test
  public void findSpecializationsTest() {
    List<Specialization> specializations = specializationDao.findSpecializations("а");
    assertNotNull(specializations);
    assertTrue(specializations.size() > 0);
  }

  @Test
  public void getAllTest() {
    List<Specialization> specializations = specializationDao.getAll();
    assertNotNull(specializations);
    assertTrue(specializations.size() > 0);
  }
}
