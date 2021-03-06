package ru.hh.school.employerreview.review;

import ru.hh.errors.common.Errors;
import ru.hh.school.employerreview.PaginationHelper;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.rating.RatingDao;
import ru.hh.school.employerreview.review.dto.ResponseBodyReviewId;
import ru.hh.school.employerreview.review.dto.ResponseBodyReviews;
import ru.hh.school.employerreview.review.dto.ReviewCounterDto;
import ru.hh.school.employerreview.review.dto.ReviewDto;
import ru.hh.school.employerreview.specializations.dto.SpecializationDto;
import ru.hh.school.employerreview.statistic.main.MainPageStatisticDao;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/review")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReviewResource {
  private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");

  private final ReviewDao reviewDao;
  private final EmployerDao employerDao;
  private final RatingDao ratingDao;
  private final MainPageStatisticDao mainPageStatisticDao;

  public ReviewResource(ReviewDao reviewDao,
                        EmployerDao employerDao,
                        RatingDao ratingDao,
                        MainPageStatisticDao mainPageStatisticDao) {
    this.reviewDao = reviewDao;
    this.employerDao = employerDao;
    this.ratingDao = ratingDao;
    this.mainPageStatisticDao = mainPageStatisticDao;
  }

  @POST
  public ResponseBodyReviewId postReview(ReviewDto reviewDto) {
    Errors errors = new Errors(Response.Status.BAD_REQUEST);

    if (reviewDto.getEmployerId() == null) {
      errors.add("MISSING_FIELD", "employer_id");
    }

    if (reviewDto.getRating() == null) {
      errors.add("MISSING_FIELD", "rating");
    }

    validateRatingMatchesBoundaries(reviewDto.getRating(), errors);
    validateRatingMultiplicityToPointFive(reviewDto.getRating(), errors);

    if (errors.hasErrors()) {
      throw errors.toWebApplicationException();
    }

    Employer employer = employerDao.getEmployer(reviewDto.getEmployerId());
    if (employer == null || employer.getId() == null) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_FIELD_VALUE", "employer_id").toWebApplicationException();
    }

    Review review = reviewDto.toReview();
    reviewDao.save(review);
    mainPageStatisticDao.addReviewCount();
    if (employer.getRating() == null) {
      mainPageStatisticDao.addEmployerWithReviewCount();
    }
    ratingDao.addNewEstimate(employer, reviewDto.getRating());

    return new ResponseBodyReviewId(review.getId());
  }

  @GET
  public ResponseBodyReviews getReviews(@QueryParam("employer_id") Integer employerId,
                                        @QueryParam("page") @DefaultValue("0") int page,
                                        @QueryParam("per_page") @DefaultValue("10") int perPage,
                                        @QueryParam("review_type") ReviewType reviewType) {
    PaginationHelper.checkInputParameters(Objects.toString(employerId, ""), page, perPage);

    Integer rowCount = Math.toIntExact(reviewDao.getRowCountFilteredByEmployer(employerId, reviewType));
    if (rowCount == 0) {
      return new ResponseBodyReviews(Collections.emptyList(), 0, 0, 0, 0);
    }

    int pageCount = PaginationHelper.calculatePagesCount(rowCount, perPage);
    if (page > pageCount - 1) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "page").toWebApplicationException();
    }

    List<Review> reviews = reviewDao.getPaginatedFilteredByEmployerWithSpecializations(employerId, page * perPage, perPage, reviewType);
    if (reviews == null) {
      return new ResponseBodyReviews();
    }

    reviews.sort((review01, review02) -> {
      if (review02.getId() > review01.getId()) {
        return 1;
      }
      if (review02.getId() < review01.getId()) {
        return -1;
      }
      return 0;
    });

    List<ReviewDto> reviewDtos = reviews.stream().map(review -> {
      ReviewDto reviewDto = new ReviewDto(employerId, review.getId(), review.getRating(), review.getReviewType(), review.getText());
      reviewDto.setSalary(review.getSalary());
      reviewDto.setEmploymentTerminated(review.getEmploymentTerminated());
      reviewDto.setEmploymentDuration(review.getEmploymentDuration());
      reviewDto.setPositionId(review.getPosition() == null ? null : review.getPosition().getId());
      reviewDto.getSpecializations().addAll(review.getSpecializations()
          .stream().map(SpecializationDto::fromSpecialization).collect(Collectors.toList()));
      reviewDto.setCreatedOn(DATE_FORMAT.format(new Date(review.getCreatedOn().getTime())));
      return reviewDto;
    }).collect(Collectors.toList());

    return new ResponseBodyReviews(reviewDtos, page, pageCount, perPage, rowCount);
  }

  @GET
  @Path("/count/{employer_id}")
  public ReviewCounterDto getRecentEmployerReview(@PathParam("employer_id") int employerId,
                                                  @QueryParam("interval") @DefaultValue("1") int interval) {
    if (employerDao.getEmployer(employerId) == null) {
      throw new Errors(Response.Status.NOT_FOUND, "NOT_FOUND", "employer_id").toWebApplicationException();
    }
    return new ReviewCounterDto(employerId, reviewDao.countRecentReviews(employerId, interval));
  }

  private static void validateRatingMatchesBoundaries(float rating, Errors errors) {
    if (rating < 0.0 || rating > 5.0) {
      errors.add("BAD_FIELD_VALUE", "rating");
    }
  }

  private static void validateRatingMultiplicityToPointFive(float rating, Errors errors) {
    if (Float.valueOf(rating * 10).intValue() % 5 != 0) {
      errors.add("BAD_FIELD_VALUE", "rating");
    }
  }

}
