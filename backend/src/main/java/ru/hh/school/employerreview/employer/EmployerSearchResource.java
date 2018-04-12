package ru.hh.school.employerreview.employer;

import ru.hh.errors.common.Errors;
import ru.hh.school.employerreview.PaginationHelper;
import ru.hh.school.employerreview.employer.dto.EmployerItem;
import ru.hh.school.employerreview.employer.dto.EmployersResponse;
import ru.hh.school.employerreview.rating.Rating;
import ru.hh.school.employerreview.rating.RatingDao;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/employers")
@Produces(MediaType.APPLICATION_JSON)
public class EmployerSearchResource {

  private final EmployerDao employerDao;
  private final RatingDao ratingDao;

  public EmployerSearchResource(EmployerDao employerDao, RatingDao ratingDao) {
    this.employerDao = employerDao;
    this.ratingDao = ratingDao;
  }

  @GET
  public EmployersResponse searchEmployers(@QueryParam("text") String text,
                                           @QueryParam("page") @DefaultValue("0") int page,
                                           @QueryParam("perPage") @DefaultValue("10") int perPage) {

    PaginationHelper.checkInputParameters(text, page, perPage);

    int rowCount = employerDao.getRowCountFilteredByEmployer(text);
    if (rowCount == 0) {
      return new EmployersResponse();
    }

    int pageCount = PaginationHelper.calculatePagesCount(rowCount, perPage);
    if (pageCount <= 0) {
      throw new Errors(Response.Status.BAD_REQUEST, "PAGINATION", "pageCount").toWebApplicationException();
    }
    if (page > pageCount - 1) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "page").toWebApplicationException();
    }

    List<Employer> resultsFromDB = employerDao.find(text, page, perPage);

    List<EmployerItem> employerItems = new ArrayList<>();
    for (int i = 0; i < resultsFromDB.size(); ++i) {
      EmployerItem employerItem = resultsFromDB.get(i).toEmployerItem();
      employerItem.setRating(ratingDao.getRating(resultsFromDB.get(i).getId()));
      employerItems.add(employerItem);
    }
    return new EmployersResponse(employerItems, page, perPage, rowCount, pageCount);
  }

  @GET
  @Path("/{employer_id}")
  public EmployerItem getEmployerById(@PathParam("employer_id") Integer employerId) {
    if (employerId == null) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "employerId").toWebApplicationException();
    }
    Employer employer = employerDao.getById(employerId);
    if (employer == null) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "employerId").toWebApplicationException();
    }
    EmployerItem employerItem = employer.toEmployerItem();
    Rating rating = ratingDao.getRating(employerId);
    if (rating != null) {
      employerItem.setRating(rating);
    }
    return employerItem;
  }
}
