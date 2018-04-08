package ru.hh.school.employerreview.employer;

import ru.hh.errors.common.Errors;
import ru.hh.school.employerreview.PaginationHelper;
import ru.hh.school.employerreview.employer.dto.EmployersResponse;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/employers")
@Produces(MediaType.APPLICATION_JSON)
public class EmployerSearchResource {

  private final EmployerDao employerDao;

  public EmployerSearchResource(EmployerDao employerDao) {
    this.employerDao = employerDao;
  }

  @GET
  public Response employersSearch(@QueryParam("text") @DefaultValue("") String text,
                                  @QueryParam("page") @DefaultValue("0") int page,
                                  @QueryParam("perPage") @DefaultValue("10") int perPage) {

    if (text.isEmpty()) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "text").toWebApplicationException();
    }

    int rowCount = employerDao.getRowCountFilteredByEmployer(text);
    if (rowCount == 0) {
      return Response.ok().entity(new EmployersResponse(null, page, perPage, rowCount, 0)).build();
    }
    Errors errors = new Errors(Response.Status.BAD_REQUEST);
    if (page < 0) {
      errors.add("BAD_REQUEST_PARAMETER", "page");
    }
    if (perPage <= 0) {
      errors.add("BAD_REQUEST_PARAMETER", "perPage");
    }
    if (errors.hasErrors()) {
      throw errors.toWebApplicationException();
    }

    int pageCount = PaginationHelper.calculatePagesCount(rowCount, perPage);
    if (pageCount <= 0) {
      throw new Errors(Response.Status.BAD_REQUEST, "PAGINATION", "pageCount").toWebApplicationException();
    }
    if (page > pageCount - 1) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "page").toWebApplicationException();
    }

    List<Employer> resultsFromDB = employerDao.find(text, page, perPage);
    EmployersResponse employersResponse = new EmployersResponse(resultsFromDB, page, perPage, rowCount, pageCount);
    return Response.ok().entity(employersResponse).build();
  }

  @GET
  @Path("/{employer_id}")
  public Response getEmployerById(@PathParam("employer_id") Integer employerId) {
    Employer employer = employerDao.getById(employerId);
    if (employer == null) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "employerId").toWebApplicationException();
    }
    return Response.ok().entity(employer.toEmployerItem()).build();
  }
}