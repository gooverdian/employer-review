package ru.hh.school.employerreview.rating.deviation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.hh.school.employerreview.EmployerReviewTestBase;
import ru.hh.school.employerreview.TestConfig;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.employer.EmployerSearchResource;
import ru.hh.school.employerreview.employer.dto.EmployerItem;
import ru.hh.school.employerreview.review.ReviewResource;
import ru.hh.school.employerreview.review.ReviewType;
import ru.hh.school.employerreview.review.dto.ReviewDto;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingDeviationCalculationWorkerTest extends EmployerReviewTestBase {

  @Inject
  protected TestConfig.TestQueryExecutorDao testQueryExecutorDao;

  @Test
  public void calculateDeviationTest() {
    Map<Float, Integer> estimates = new HashMap<>();
    estimates.put(1f, 5);

    Assert.assertEquals((Float) 0f, RatingDeviationCalculationWorker.calculateDeviation(estimates, 1f));

    estimates.clear();
    estimates.put(1f, 1);
    estimates.put(2f, 1);
    estimates.put(3f, 1);
    estimates.put(4f, 1);
    estimates.put(5f, 1);
    Assert.assertEquals((Float) 2f, RatingDeviationCalculationWorker.calculateDeviation(estimates, 3f));
  }

  @Test
  public void integrationTest() {
    applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);
    EmployerDao employerDao = applicationContext.getBean(EmployerDao.class);
    employerDao.save(employer);
    employerDao.save(employer2);

    ReviewResource reviewResource = applicationContext.getBean(ReviewResource.class);

    ReviewDto reviewDto1 = new ReviewDto();
    reviewDto1.setEmployerId(employer.getId());
    reviewDto1.setRating(1f);
    reviewDto1.setText("good");
    reviewDto1.setReviewType(ReviewType.EMPLOYEE);

    ReviewDto reviewDto2 = new ReviewDto();
    reviewDto2.setEmployerId(employer.getId());
    reviewDto2.setRating(2f);
    reviewDto2.setText("good");
    reviewDto2.setReviewType(ReviewType.EMPLOYEE);

    ReviewDto reviewDto3 = new ReviewDto();
    reviewDto3.setEmployerId(employer.getId());
    reviewDto3.setRating(3f);
    reviewDto3.setText("good");
    reviewDto3.setReviewType(ReviewType.EMPLOYEE);

    ReviewDto reviewDto4 = new ReviewDto();
    reviewDto4.setEmployerId(employer2.getId());
    reviewDto4.setRating(1f);
    reviewDto4.setText("good");
    reviewDto4.setReviewType(ReviewType.EMPLOYEE);

    ReviewDto reviewDto5 = new ReviewDto();
    reviewDto5.setEmployerId(employer2.getId());
    reviewDto5.setRating(3f);
    reviewDto5.setText("good");
    reviewDto5.setReviewType(ReviewType.EMPLOYEE);

    ReviewDto reviewDto6 = new ReviewDto();
    reviewDto6.setEmployerId(employer2.getId());
    reviewDto6.setRating(5f);
    reviewDto6.setText("good");
    reviewDto6.setReviewType(ReviewType.EMPLOYEE);

    reviewResource.postReview(reviewDto1);
    reviewResource.postReview(reviewDto2);
    reviewResource.postReview(reviewDto3);
    reviewResource.postReview(reviewDto4);
    reviewResource.postReview(reviewDto5);
    reviewResource.postReview(reviewDto6);

    RatingDeviationCalculationWorker.setApplicationContext(applicationContext);
    String[] strings = {};
    RatingDeviationCalculationWorker.main(strings);

    EmployerSearchResource employerSearchResource = applicationContext.getBean(EmployerSearchResource.class);
    List<EmployerItem> top = employerSearchResource.getTopBalanced(2);

    Assert.assertEquals(2, top.size());
    Assert.assertEquals(employer.getId(), top.get(0).getId());
    Assert.assertEquals(employer2.getId(), top.get(1).getId());

    top = employerSearchResource.getTopDisbalanced(2);

    Assert.assertEquals(2, top.size());
    Assert.assertEquals(employer.getId(), top.get(1).getId());
    Assert.assertEquals(employer2.getId(), top.get(0).getId());
  }

  @After
  public void deleteEntities() {
    testQueryExecutorDao.executeQuery("delete from RatingDeviation");
    testQueryExecutorDao.executeQuery("delete from Review");
    testQueryExecutorDao.executeQuery("delete from Employer");
    testQueryExecutorDao.executeQuery("delete from Rating");
    testQueryExecutorDao.executeQuery("delete from Area");
  }
}
