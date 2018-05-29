package ru.hh.school.employerreview.review.generator;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/review")
public class ReviewGenerationService {

  private final ReviewsGenerator reviewsGenerator;

  public ReviewGenerationService(ReviewsGenerator reviewsGenerator) {
    this.reviewsGenerator = reviewsGenerator;
  }

  @POST
  @Path("/generate")
  public Response generateReviews(@QueryParam("mat_expected_review_count") @DefaultValue("10") Float mathExpectedReviewCount,
                                  @QueryParam("deviation_review_count") @DefaultValue("10") Float deviationReviewCount,
                                  @QueryParam("max_employer_size") @DefaultValue("1000") Integer maxEmployerSize) {
    reviewsGenerator.setPerEmployerAvgReviewsCount(mathExpectedReviewCount);
    reviewsGenerator.setPerEmployerReviewsCountDeviation(deviationReviewCount);
    reviewsGenerator.setEmployersToProcessLimit(maxEmployerSize);
    reviewsGenerator.setEmployersPerPage(100);
    reviewsGenerator.process();
    return Response.ok().build();
  }
}
