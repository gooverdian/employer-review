package ru.hh.school.employerreview.review;

import ru.hh.errors.common.Errors;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.rating.RatingDao;
import ru.hh.school.employerreview.statistic.main.MainPageStatisticDao;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Path("/review")
public class ReviewGenerationService {

  private final ReviewDao reviewDao;
  private final EmployerDao employerDao;
  private final RatingDao ratingDao;
  private final MainPageStatisticDao mainPageStatisticDao;

  private static final int PER_PAGE = 1000;
  private static final int MAX_DATE_INTERVAL = 30;
  private static final int MAX_TEXT_LENGTH = 100;
  private static final int MAX_ESTIMATE = 5;
  private static final float MIN_ESTIMATE = 0.5f;
  private static final String CANDIDATE_CHARS = " ABC DEF GHI JKL MNO PQR STU VWX YZ ";

  private final Random randomGenerator = new Random();

  public ReviewGenerationService(ReviewDao reviewDao, EmployerDao employerDao, RatingDao ratingDao, MainPageStatisticDao mainPageStatisticDao) {
    this.reviewDao = reviewDao;
    this.ratingDao = ratingDao;
    this.employerDao = employerDao;
    this.mainPageStatisticDao = mainPageStatisticDao;
  }

  @POST
  @Path("/generate")
  public Response generateReviews(@QueryParam("mat_expected_review_count") @DefaultValue("10") Float mathExpectedReviewCount,
                                  @QueryParam("deviation_review_count") @DefaultValue("10") Float deviationReviewCount,
                                  @QueryParam("max_employer_size") @DefaultValue("1000") Integer maxEmployerSize) {

    if (employerDao.countRows() < maxEmployerSize) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST", "max_employer_size").toWebApplicationException();
    }
    int page = 0;
    while (PER_PAGE * page < maxEmployerSize) {
      generateReviewsOnCurrentPage(employerDao.findEmployers("", page, PER_PAGE, true),
          mathExpectedReviewCount,
          deviationReviewCount);
      page += 1;
    }
    return Response.ok().build();
  }

  private void generateReviewsOnCurrentPage(List<Employer> employers, Float mathExpectedReviewCount, Float deviationReviewCount) {
    for (Employer employer : employers) {
      generateReviewsOnCurrentEmployer(employer,
          getReviewCount(mathExpectedReviewCount, deviationReviewCount),
          getEmployerMeanEstimate());
    }
  }

  private void generateReviewsOnCurrentEmployer(Employer employer, int reviewCount, Float mathExpectedEstimate) {
    for (int i = 0; i < reviewCount; i++) {
      mainPageStatisticDao.addReviewCount();
      if (employer.getRating() == null) {
        mainPageStatisticDao.addEmployerWithReviewCount();
      }
      reviewDao.save(getReview(employer, mathExpectedEstimate));
      ratingDao.addNewEstimate(employer, getEstimate(mathExpectedEstimate));
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(Calendar.DATE, -randomGenerator.nextInt(MAX_DATE_INTERVAL));
      employerDao.addEmployerVisitCounter(employer, calendar.getTime());
    }
  }

  private float getEstimate(float mathExpected) {
    float estimate = (float) randomGenerator.nextGaussian() + mathExpected;
    float result = 2.5f;
    float minDifference = Float.MAX_VALUE;

    for (float starValue = MIN_ESTIMATE; starValue <= MAX_ESTIMATE; starValue += 0.5f) {
      if (minDifference > Math.abs(starValue - estimate)) {
        minDifference = Math.abs(starValue - estimate);
        result = starValue;
      }
    }
    return result;
  }

  private ReviewType getReviewType() {
    return randomGenerator.nextBoolean() ? ReviewType.EMPLOYEE : ReviewType.INTERVIEWEE;
  }

  private String getText() {
    return generateRandomChars(randomGenerator.nextInt(MAX_TEXT_LENGTH));
  }

  private String generateRandomChars(int length) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int randomArrayIndex = randomGenerator.nextInt(CANDIDATE_CHARS.length());
      sb.append(CANDIDATE_CHARS.charAt(randomArrayIndex));
    }
    return sb.toString();
  }

  private int getReviewCount(float mathExpected, float deviation) {
    int reviewCount = (int) (randomGenerator.nextFloat() * deviation + mathExpected);
    return reviewCount > 0 ? reviewCount : 0;
  }

  private float getEmployerMeanEstimate() {
    return randomGenerator.nextFloat() * MAX_ESTIMATE;
  }

  private Review getReview(Employer employer, float mathExpectedEstimate) {
    return new Review(employer, getEstimate(mathExpectedEstimate), getReviewType(), getText());
  }
}
