package ru.hh.school.employerreview.review;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import ru.hh.school.employerreview.EmployerReviewTest;
import ru.hh.school.employerreview.TestConfig;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.review.dto.ReviewDto;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

@ContextConfiguration(classes = {TestConfig.class})
public class ReviewResourceTest extends EmployerReviewTest {

  @Inject
  private ReviewResource resource;
  @Inject
  private AreaDao areaDao;
  @Inject
  private EmployerDao employerDao;

  @Test
  public void testPostReview() {

    Area area = areaDao.getById(testAreaId);
    if (area == null) {
      areaDao.save(new Area(testAreaName, testAreaId, -1));
    }

    Employer employer = employerDao.getEmployer(testEmployerId);
    if (employer == null) {
      employer = new Employer(testEmployerName, "url", 1);
      employer.setArea(area);
      employerDao.save(employer);
    }

    ReviewDto reviewDto = new ReviewDto();
    reviewDto.setEmployerId(employer.getId());
    reviewDto.setRating(2.5f);
    reviewDto.setText("good");

    resource.postReview(reviewDto);
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
