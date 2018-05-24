package ru.hh.school.employerreview.review;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.hh.school.employerreview.EmployerReviewTestBase;
import ru.hh.school.employerreview.TestConfig;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.statistic.main.MainPageStatisticDao;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
public class ReviewGenerationServiceTest extends EmployerReviewTestBase {

  @Inject
  private ReviewGenerationService reviewGenerationService;
  @Inject
  private AreaDao areaDao;
  @Inject
  private EmployerDao employerDao;
  @Inject
  private ReviewDao reviewDao;
  @Inject
  private MainPageStatisticDao mainPageStatisticDao;
  @Inject
  protected TestConfig.TestQueryExecutorDao testQueryExecutorDao;

  @Test
  public void testGenerateReview() {
    areaDao.save(area);
    employer.setArea(area);
    employerDao.save(employer);

    Response response = reviewGenerationService.generateReviews(10f, 0f, 1);

    Assert.assertEquals(10, reviewDao.countRows());
    Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

    reviewDao.deleteAllReviews();
    employerDao.deleteEmployer(employer);
    areaDao.deleteArea(area);
    mainPageStatisticDao.deleteEmployerWithReviewCount();
    mainPageStatisticDao.deleteReviewCount();
  }

  @Before
  @After
  public void deleteEntities() {
    testQueryExecutorDao.executeQuery("delete from Review");
    testQueryExecutorDao.executeQuery("delete from Employer");
    testQueryExecutorDao.executeQuery("delete from MainPageStatistic");
    testQueryExecutorDao.executeQuery("delete from Area");
  }
}
