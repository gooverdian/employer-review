package ru.hh.school.employerreview.review;

import ru.hh.errors.common.Errors;
import ru.hh.school.employerreview.PaginationHelper;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.rating.RatingDao;
import ru.hh.school.employerreview.review.dto.ResponseBodyReviewId;
import ru.hh.school.employerreview.review.dto.ResponseBodyReviews;
import ru.hh.school.employerreview.review.dto.ReviewDto;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/review")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReviewResource {

  private final ReviewDao reviewDao;
  private final EmployerDao employerDao;
  private final RatingDao ratingDao;

  public ReviewResource(ReviewDao reviewDao, EmployerDao employerDao, RatingDao ratingDao) {
    this.reviewDao = reviewDao;
    this.employerDao = employerDao;
    this.ratingDao = ratingDao;
  }

  @POST
  public Response postReview(ReviewDto reviewDto) {
    Errors errors = new Errors(Response.Status.BAD_REQUEST);

    if (reviewDto.getEmployerId() == null) {
      errors.add("MISSING_FIELD", "employer_id");
    }

    if (reviewDto.getRating() == null) {
      errors.add("MISSING_FIELD", "rating");
    }

    if (reviewDto.getRating() < 0.0 || reviewDto.getRating() > 5.0) {
      errors.add("BAD_FIELD_VALUE", "rating");
    }
    if (errors.hasErrors()) {
      throw errors.toWebApplicationException();
    }

    Employer employer = employerDao.getEmployer(reviewDto.getEmployerId());
    if (employer == null || employer.getId() == null) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_FIELD_VALUE", "employer_id").toWebApplicationException();
    }

    Review review = new Review(
        employer,
        reviewDto.getRating(),
        reviewDto.getReviewType(),
        reviewDto.getText());
    reviewDao.save(review);

    ratingDao.addNewEstimate(employer.getId(), reviewDto.getRating());

    return Response.status(200).entity(new ResponseBodyReviewId(review.getId())).build();
  }

  @GET
  public Response getReviews(
      @QueryParam("employer_id") int employerId,
      @QueryParam("page") @DefaultValue("0") int page,
      @QueryParam("per_page") @DefaultValue("10") int perPage
  ) {
    Errors errors = new Errors(Response.Status.BAD_REQUEST);

    Integer rowCount = Math.toIntExact(reviewDao.getRowCountFilteredByEmployer(employerId));
    if (rowCount == 0) {
      throw new Errors(Response.Status.BAD_REQUEST, "NO_DATA", "review").toWebApplicationException();
    }
    if (page < 0) {
      errors.add("BAD_REQUEST_PARAMETER", "page");
    }
    if (perPage <= 0) {
      errors.add("BAD_REQUEST_PARAMETER", "per_page");
    }
    if (errors.hasErrors()) {
      throw errors.toWebApplicationException();
    }

    int pageCount = PaginationHelper.calculatePagesCount(rowCount, perPage);
    if (page > pageCount - 1) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "page").toWebApplicationException();
    }

    List<Review> reviews = reviewDao.getPaginatedFilteredByEmployer(employerId, page * perPage, perPage);
    if (reviews == null) {
      return Response.status(200)
          .entity(new ResponseBodyReviews(new ArrayList<>(), page, pageCount, perPage)).build();
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

    List<ReviewDto> reviewDtos = new ArrayList<>();
    for (Review review : reviews) {
      ReviewDto reviewDto = new ReviewDto(employerId, review.getId(), review.getRating(), review.getReviewType(), review.getText());
      reviewDtos.add(reviewDto);
    }
    return Response.status(200).entity(new ResponseBodyReviews(reviewDtos, page, pageCount, perPage)).build();

  }
}
