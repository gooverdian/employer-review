package ru.hh.school.employerreview.webextractor;

import org.junit.Before;
import org.junit.Test;
import ru.hh.school.employerreview.EmployerReviewTestBase;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReviewExtractorTest extends EmployerReviewTestBase {
  private final static int THREADS = 1;
  private final static int REVIEWS_LIMIT = 10;
  private final static int REVIEW_LENGTH_THRESHOLD = 50;

  @Inject
  ExternalReviewDao externalReviewDao;

  @Before
  public void prepareDb() {
    ReviewExtractor.setApplicationContext(applicationContext);

    String[] args = {"threads=" + THREADS, "limit=" + REVIEWS_LIMIT, "length_threshold=" + REVIEW_LENGTH_THRESHOLD};
    ReviewExtractor.main(args);
  }

  @Test
  public void reviewsExtractionTest() {
    List<ExternalReview> externalReviews = externalReviewDao.getAll();

    assertNotNull(externalReviews);
    assertTrue(externalReviews.size() >= REVIEWS_LIMIT);
    externalReviews.forEach(externalReview -> {
      assertTrue(externalReview.getText().length() > REVIEW_LENGTH_THRESHOLD);
    });
  }
}
