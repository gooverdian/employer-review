package ru.hh.school.employerreview.employer;

import ru.hh.errors.common.Errors;
import ru.hh.school.employerreview.PaginationHelper;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.employer.dto.EmployerItem;
import ru.hh.school.employerreview.employer.dto.EmployersResponse;
import ru.hh.school.employerreview.employer.visit.EmployerVisitDto;
import ru.hh.school.employerreview.rating.RatingDao;

import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/employers")
@Produces(MediaType.APPLICATION_JSON)
public class EmployerSearchResource {

  private final EmployerDao employerDao;
  private final RatingDao ratingDao;
  private final AreaDao areaDao;

  private final static Integer MAX_TOP_SIZE = 50;

  public EmployerSearchResource(EmployerDao employerDao, RatingDao ratingDao, AreaDao areaDao) {
    this.employerDao = employerDao;
    this.ratingDao = ratingDao;
    this.areaDao = areaDao;
  }

  @GET
  public EmployersResponse searchEmployers(@QueryParam("text") String text,
                                           @QueryParam("page") @DefaultValue("0") int page,
                                           @QueryParam("per_page") @DefaultValue("10") int perPage) {

    PaginationHelper.checkInputParameters(text, page, perPage);

    int rowCount = employerDao.getRowCountFilteredByEmployer(text);
    if (rowCount <= 0) {
      return new EmployersResponse();
    }

    int pageCount = PaginationHelper.calculatePagesCount(rowCount, perPage);
    if (page > pageCount - 1) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "page").toWebApplicationException();
    }

    List<Employer> resultsFromDB = employerDao.findEmployers(text, page, perPage, true);

    List<Map> stars = ratingDao.getStarsByEmployers(resultsFromDB);

    List<EmployerItem> employerItems = new ArrayList<>();
    for (int i = 0; i < resultsFromDB.size(); i++) {
      EmployerItem employerItem = resultsFromDB.get(i).toEmployerItem();
      employerItem.setStars(stars.get(i));
      employerItems.add(employerItem);
    }
    return new EmployersResponse(employerItems, page, perPage, rowCount, pageCount);
  }

  @GET
  @Path("/{employer_id}")
  public EmployerItem getEmployerById(@PathParam("employer_id") Integer employerId,
                                      @QueryParam("add_visit_counter") @DefaultValue("false") Boolean addVisitCounter) {
    if (employerId == null) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "employerId").toWebApplicationException();
    }
    Employer employer = employerDao.getEmployer(employerId);
    if (employer == null) {
      throw new Errors(Response.Status.NOT_FOUND, "NOT_FOUND", "employerId").toWebApplicationException();
    }
    EmployerItem employerItem = employer.toEmployerItem();
    employerItem.setStars(ratingDao.getStarsInRatingMap(employerId));

    if (addVisitCounter) {
      employerDao.addEmployerVisitCounter(employer, new Date());
    }
    return employerItem;
  }

  @POST
  public EmployerItem postEmployer(EmployerItem employerItem) {
    if (employerItem == null || employerItem.getHhId() != null) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "employerItem").toWebApplicationException();
    }
    Employer employer = employerItem.toEmployer();
    if (employerItem.getAreaId() != null) {
      Area area = areaDao.getAreaById(employerItem.getAreaId());
      if (area == null) {
        throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "employerItem.areaId").toWebApplicationException();
      }
      employer.setArea(area);
    }
    employerDao.save(employer);
    return employer.toEmployerItem();
  }

  @GET
  @Path("/visits")
  public List<EmployerVisitDto> getTopEmployerVisited(@QueryParam("size") @DefaultValue("20") Integer size,
                                                      @QueryParam("interval") @DefaultValue("30") Integer interval) {
    if (size <= 0 || size > MAX_TOP_SIZE) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER",
          "size parameter must be from 1 to " + MAX_TOP_SIZE).toWebApplicationException();
    }
    return employerDao.getTopEmployerVisited(size, interval);
  }

  @GET
  @Path("/best")
  public List<EmployerItem> getTopBest(@QueryParam("size") @DefaultValue("20") Integer size) {
    return getTopEmployersByRating(size, true);
  }

  @GET
  @Path("/worst")
  public List<EmployerItem> getTopWorst(@QueryParam("size") @DefaultValue("20") Integer size) {
    return getTopEmployersByRating(size, false);
  }

  private List<EmployerItem> getTopEmployersByRating(Integer size, Boolean bestFirst) {
    if (size <= 0 || size > MAX_TOP_SIZE) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER",
          "size parameter must be from 1 to " + MAX_TOP_SIZE).toWebApplicationException();
    }
    List<Employer> resultsFromDB = employerDao.findEmployers("", 0, size, bestFirst);

    List<Map> stars = ratingDao.getStarsByEmployers(resultsFromDB);

    List<EmployerItem> employerItems = new ArrayList<>();
    for (int i = 0; i < resultsFromDB.size(); i++) {
      EmployerItem employerItem = resultsFromDB.get(i).toEmployerItem();
      employerItem.setStars(stars.get(i));
      employerItems.add(employerItem);
    }
    return employerItems;
  }

  @GET
  @Path("/disbalanced")
  public List<EmployerItem> getTopDisbalanced(@QueryParam("size") @DefaultValue("20") Integer size) {
    return getTopBalancedEmployerItems(size, false);
  }

  @GET
  @Path("/balanced")
  public List<EmployerItem> getTopBalanced(@QueryParam("size") @DefaultValue("20") Integer size) {
    return getTopBalancedEmployerItems(size, true);
  }

  private List<EmployerItem> getTopBalancedEmployerItems(Integer size, Boolean isBalanced) {
    if (size <= 0 || size > MAX_TOP_SIZE) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER",
          "size parameter must be from 1 to " + MAX_TOP_SIZE).toWebApplicationException();
    }
    List<Employer> employers = ratingDao.getTopBalanced(size, isBalanced);
    return employers.stream().map(employer -> employer.toEmployerItem()).collect(Collectors.toList());
  }
}
