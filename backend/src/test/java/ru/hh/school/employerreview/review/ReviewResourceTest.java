package ru.hh.school.employerreview.review;

import org.junit.Assert;
import org.junit.Test;
import ru.hh.school.employerreview.EmployerReviewTestBase;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.rating.RatingDao;
import ru.hh.school.employerreview.rating.stars.StarsInRating;
import ru.hh.school.employerreview.review.dto.ReviewDto;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

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
    Assert.assertEquals(employerFromDB.getRating().getRating(), postedReview.getRating());
    Assert.assertTrue(employerFromDB.getRating().getPeopleRated() == 1);

    StarsInRating starsInRating = ratingDao.getStarsInRating(employerFromDB.getId(), reviewEstimate);
    Assert.assertTrue(starsInRating.getStarCounter() == 1);

    reviewDao.deleteReview(reviewFromDB);
    employerDao.deleteEmployer(employer);
    ratingDao.deleteRating(employerFromDB.getRating());
    ratingDao.deleteStarsInRating(starsInRating);
    areaDao.deleteArea(area);
  }

  @Test(expected = WebApplicationException.class)
  public void testEmptyRequest() {
    resource.getReviews(null, 0, 1);
  }

  @Test(expected = WebApplicationException.class)
  public void testNegativePerPageRequest() {
    resource.getReviews(1, 0, -1);
  }

  @Test(expected = WebApplicationException.class)
  public void testNegativePageRequest() {
    resource.getReviews(1, -1, 10);
  }

  @Test(expected = WebApplicationException.class)
  public void testZeroPerPageRequest() {
    resource.getReviews(1, 0, 0);
  }
}
