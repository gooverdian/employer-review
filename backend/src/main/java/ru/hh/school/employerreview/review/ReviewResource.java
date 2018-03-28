package ru.hh.school.employerreview.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.review.dto.Error;
import ru.hh.school.employerreview.review.dto.ErrorType;
import ru.hh.school.employerreview.review.dto.Errors;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(ReviewResource.class);

  private final ReviewDao reviewDao;
  private final EmployerDao employerDao;

  public ReviewResource(ReviewDao reviewDao, EmployerDao employerDao) {
    this.reviewDao = reviewDao;
    this.employerDao = employerDao;
  }

  @POST
  public Response postReview(ReviewDto reviewDto) {
    Errors errors = new Errors();

    if (reviewDto.getEmployerId() == null) {
      errors.addError(new Error(ErrorType.MISSING_FIELD, "employerId"));
    }

    if (reviewDto.getRating() == null) {
      errors.addError(new Error(ErrorType.MISSING_FIELD, "rating"));
    }

    if (reviewDto.getRating() < 0.0 || reviewDto.getRating() > 5.0) {
      errors.addError(new Error(ErrorType.BAD_FIELD_VALUE, "rating"));
    }

    if (!errors.isEmpty()) {
      return Response.status(400).entity(errors).build();
    }

    try {
      Employer employer = employerDao.getEmployer(reviewDto.getEmployerId());
      if (employer == null || employer.getId() == null) {
        errors.addError(new Error(ErrorType.BAD_FIELD_VALUE, "employerId"));
        return Response.status(400).entity(errors).build();
      }

      Review review = new Review(
          employer,
          reviewDto.getRating(),
          reviewDto.getText());
      reviewDao.save(review);

      return Response.status(200).entity(new ResponseBodyReviewId(review.getId())).build();

    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      errors.addError(new Error(ErrorType.SAVE_ERROR, "review"));
      return Response.status(500).entity(errors).build();
    }
  }

  @GET
  public Response getReviews(
      @QueryParam("employerId") Integer employerId,
      @QueryParam("page") @DefaultValue("0") Integer page,
      @QueryParam("per_page") @DefaultValue("10") Integer perPage
  ) {
    Errors errors = new Errors();

    if (employerId == null) {
      errors.addError(new Error(ErrorType.MISSING_PARAMETER, "employerId"));
      return Response.status(400).entity(errors).build();
    }

    Integer rowCount = Math.toIntExact(reviewDao.getRowCountFilteredByEmployer(employerId));
    if (rowCount == 0) {
      errors.addError(new Error(ErrorType.NO_DATA, "reviews"));
      return Response.status(200).entity(errors).build();
    }
    Integer pageCount = null;

    if (perPage != null && perPage != 0) {
      pageCount = rowCount / perPage + (rowCount % perPage == 0 ? 0 : 1);
    }

    List<Review> reviews = null;
    try {

      if (pageCount != null && pageCount != 0) {
        reviews = reviewDao.getPaginatedFilteredByEmployer(employerId, page * perPage, perPage);
      } else {
        errors.addError(new Error(ErrorType.PAGINATION, "pageCount"));
        return Response.status(400).entity(errors).build();
      }
      if (reviews == null) {
        return Response.status(200)
            .entity(new ResponseBodyReviews(new ArrayList<>(), page, pageCount, perPage)).build();
      }

      List<ReviewDto> reviewDtos = new ArrayList<>();
      for (Review review : reviews) {
        ReviewDto reviewDto = new ReviewDto(employerId, review.getId(), review.getRating(), review.getText());
        reviewDtos.add(reviewDto);
      }
      return Response.status(200).entity(new ResponseBodyReviews(reviewDtos, page, pageCount, perPage)).build();

    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      errors.addError(new Error(ErrorType.DB_ERROR, "review"));
      return Response.status(500).entity(errors).build();
    }
  }
}
