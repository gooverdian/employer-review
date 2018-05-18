package ru.hh.school.employerreview.review;

import org.junit.Test;
import ru.hh.school.employerreview.EmployerReviewTestBase;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.specializations.Specialization;
import ru.hh.school.employerreview.specializations.SpecializationDao;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReviewDaoTest extends EmployerReviewTestBase {

  @Inject
  private SpecializationDao specializationDao;

  @Inject
  private ReviewDao reviewDao;

  @Inject
  private EmployerDao employerDao;

  @Test
  public void saveReviewWithSpecializationsTest() {
    Employer employer;
    Specialization specialization01;
    Specialization specialization02;

    employer = new Employer();
    employer.setName("Emplo 01");
    employerDao.save(employer);

    specialization01 = new Specialization();
    specialization01.setName("Spec 01");
    specializationDao.save(specialization01);

    specialization02 = new Specialization();
    specialization02.setName("Spec 02");
    specializationDao.save(specialization02);

    Review review = new Review();
    review.setEmployer(employer);
    review.setRating(2.0f);
    review.getSpecializations().add(new Specialization(specialization01.getId()));
    review.getSpecializations().add(new Specialization(specialization02.getId()));
    reviewDao.save(review);

    Integer id = review.getId();
    review = null;

    review = reviewDao.getByIdWithSpecializations(id);
    assertNotNull(review.getSpecializations());
    assertEquals(2, review.getSpecializations().size());

    Set<String> specializationsNames = new HashSet<>();
    review.getSpecializations().forEach(specialization -> specializationsNames.add(specialization.getName()));
    assertTrue(specializationsNames.contains("Spec 01"));
    assertTrue(specializationsNames.contains("Spec 02"));
  }
}
