package ru.hh.school.employerreview.search;

import ru.hh.errors.common.Errors;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.search.json.EmployersResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/search")
public class EmployerSearchResource {

  private static final int PER_PAGE_MAX_VALUE = 100;

  private final EmployerDao employerDao;

  public EmployerSearchResource(EmployerDao employerDao) {
    this.employerDao = employerDao;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response employersSearch(@QueryParam("text") @DefaultValue("") String text,
                                  @QueryParam("page") @DefaultValue("0") int page,
                                  @QueryParam("per_page") @DefaultValue("10") int perPage) {

    checkRequestParams(text, page, perPage);

    List<Employer> resultsFromDB = employerDao.find(text, page, perPage);

    int found = employerDao.getRowCountFilteredByEmployer(text);
    int pages = (int) Math.ceil((double) found / (double) perPage);
    if (page > pages - 1 && page != 0) {
      throw new Errors(Response.Status.BAD_REQUEST, "CURRENT PAGE IS MORE THAN AMOUNT OF PAGES", "").toWebApplicationException();
    }

    EmployersResponse employersResponse = new EmployersResponse(resultsFromDB, page, perPage, found, pages);
    return Response.ok().entity(employersResponse).build();
  }

  private void checkRequestParams(String text, int page, int perPage) {
    if (text.isEmpty()) {
      throw new Errors(Response.Status.BAD_REQUEST, "EMPTY REQUEST", "").toWebApplicationException();
    }

    if (perPage > PER_PAGE_MAX_VALUE || perPage < 0) {
      throw new Errors(Response.Status.BAD_REQUEST, "CURRENT PER PAGE IS MORE THAN LIMIT", "").toWebApplicationException();
    }

    if (page < 0) {
      throw new Errors(Response.Status.BAD_REQUEST, "CURRENT PAGE LESS THEN 0", "").toWebApplicationException();
    }
  }

  @GET
  @Path("/{idRequest}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getEmployerById(@PathParam("idRequest") String idRequest) {
    try {
      Employer employer = employerDao.getById(Integer.parseInt(idRequest));
      if (employer == null) {
        throw new Errors(Response.Status.BAD_REQUEST, "CURRENT USER IS ABSENT", "").toWebApplicationException();
      }
      return Response.ok().entity(employer.toJson()).build();
    } catch (NumberFormatException e) {
      throw new Errors(Response.Status.BAD_REQUEST, "SERVER CANT PARSE ID", e.getMessage()).toWebApplicationException();
    }

  }
}
