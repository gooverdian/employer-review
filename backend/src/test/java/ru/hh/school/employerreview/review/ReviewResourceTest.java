package ru.hh.school.employerreview.review;

import org.junit.Assert;
import org.junit.Test;
import ru.hh.school.employerreview.TestBase;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.review.dto.ReviewDto;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

public class ReviewResourceTest extends TestBase {

  @Inject
  private ReviewResource resource;
  @Inject
  private AreaDao areaDao;
  @Inject
  private EmployerDao employerDao;
  @Inject
  private ReviewDao reviewDao;

  @Test
  public void testPostReview() {
    Area area = new Area(testAreaName, testAreaId, -1);
    areaDao.save(area);

    Employer employer = new Employer(testEmployerName, "url", 1);
    employer.setArea(area);
    employerDao.save(employer);

    ReviewDto reviewDto = new ReviewDto();
    reviewDto.setEmployerId(employer.getId());
    reviewDto.setRating(2.5f);
    reviewDto.setText("good");
    reviewDto.setReviewType(ReviewType.EMPLOYEE);


    Review postedReview = reviewDto.toReview();
    Review reviewFromDB = reviewDao.getById(resource.postReview(reviewDto).getReviewId());

    Assert.assertEquals(postedReview.getText(), reviewFromDB.getText());
    Assert.assertEquals(postedReview.getRating(), reviewFromDB.getRating());
    Assert.assertEquals(postedReview.getReviewType(), reviewFromDB.getReviewType());
    Assert.assertEquals(postedReview.getEmployer().getId(), reviewFromDB.getEmployer().getId());

    reviewDao.delete(reviewFromDB);
    employerDao.delete(employer);
    areaDao.delete(area);
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
