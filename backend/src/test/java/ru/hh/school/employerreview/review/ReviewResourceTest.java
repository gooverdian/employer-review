package ru.hh.school.employerreview.review;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.hh.school.employerreview.EmployerReviewTestBase;
import ru.hh.school.employerreview.TestConfig;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.rating.RatingDao;
import ru.hh.school.employerreview.rating.stars.StarsInRating;
import ru.hh.school.employerreview.review.dto.ReviewCounterDto;
import ru.hh.school.employerreview.review.dto.ReviewDto;
import ru.hh.school.employerreview.statistic.main.MainPageStatisticDao;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.sql.Timestamp;
import java.util.Calendar;

public class ReviewResourceTest extends EmployerReviewTestBase {

  @Inject
  private ReviewResource resource;
  @Inject
  private AreaDao areaDao;
  @Inject
  private EmployerDao employerDao;
  @Inject
  private ReviewDao reviewDao;
  @Inject
  private RatingDao ratingDao;
  @Inject
  private MainPageStatisticDao mainPageStatisticDao;
  @Inject
  protected TestConfig.TestQueryExecutorDao testQueryExecutorDao;

  @Test
  public void testPostReview() {
    areaDao.save(area);
    employer.setArea(area);
    employerDao.save(employer);

    float reviewEstimate = 2.5f;

    ReviewDto reviewDto = new ReviewDto();
    reviewDto.setEmployerId(employer.getId());
    reviewDto.setRating(reviewEstimate);
    reviewDto.setText("good");
    reviewDto.setReviewType(ReviewType.EMPLOYEE);

    Review postedReview = reviewDto.toReview();
    Review reviewFromDB = reviewDao.getById(resource.postReview(reviewDto).getReviewId());

    Assert.assertEquals(postedReview.getText(), reviewFromDB.getText());
    Assert.assertEquals(postedReview.getRating(), reviewFromDB.getRating());
    Assert.assertEquals(postedReview.getReviewType(), reviewFromDB.getReviewType());
    Assert.assertEquals(postedReview.getEmployer().getId(), reviewFromDB.getEmployer().getId());

    Employer employerFromDB = employerDao.getEmployer(employer.getId());
    Assert.assertEquals(1, employerFromDB.getRating().getRating(), postedReview.getRating());
    Assert.assertEquals(1, employerFromDB.getRating().getPeopleRated().intValue());

    StarsInRating starsInRating = ratingDao.getStarsInRating(employerFromDB.getId(), reviewEstimate);
    Assert.assertEquals(1, starsInRating.getStarCounter().intValue());

    Assert.assertEquals(1, mainPageStatisticDao.getReviewCount().getValue().intValue());
    Assert.assertEquals(1, mainPageStatisticDao.getEmployerWithReviewCount().getValue().intValue());
  }

  @Test(expected = WebApplicationException.class)
  public void testEmptyRequest() {
    resource.getReviews(null, 0, 1, null);
  }

  @Test(expected = WebApplicationException.class)
  public void testNegativePerPageRequest() {
    resource.getReviews(1, 0, -1, null);
  }

  @Test(expected = WebApplicationException.class)
  public void testNegativePageRequest() {
    resource.getReviews(1, -1, 10, null);
  }

  @Test(expected = WebApplicationException.class)
  public void testZeroPerPageRequest() {
    resource.getReviews(1, 0, 0, null);
  }

  @Test
  public void testCountRecentReviews() {
    employerDao.save(employer);

    Calendar calendar = Calendar.getInstance();

    Review review = new Review();
    review.setEmployer(employer);
    review.setRating(2f);
    review.setReviewType(ReviewType.EMPLOYEE);
    review.setText("Yeah");
    review.setCreatedOn(new Timestamp(calendar.getTime().getTime()));

    reviewDao.save(review);

    calendar.add(Calendar.DATE, -2);
    review.setCreatedOn(new Timestamp(calendar.getTime().getTime()));

    reviewDao.save(review);

    ReviewCounterDto reviewCounterDto = resource.getRecentEmployerReview(employer.getId(), 1);

    Assert.assertEquals(employer.getId(), reviewCounterDto.getEmployerId());
    Assert.assertEquals((Integer) 1, reviewCounterDto.getCounter());
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
