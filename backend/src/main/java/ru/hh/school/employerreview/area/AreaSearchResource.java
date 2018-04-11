package ru.hh.school.employerreview.area;

import ru.hh.errors.common.Errors;
import ru.hh.school.employerreview.PaginationHelper;
import ru.hh.school.employerreview.area.dto.AreaResponse;
import ru.hh.school.employerreview.downloader.dto.AreaJson;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("/areas")
@Produces(MediaType.APPLICATION_JSON)
public class AreaSearchResource {

  private final AreaDao areaDao;

  public AreaSearchResource(AreaDao areaDao) {
    this.areaDao = areaDao;
  }

  @GET
  public Response areasSearch(@QueryParam("text") @DefaultValue("") String text,
                              @QueryParam("page") @DefaultValue("0") int page,
                              @QueryParam("perPage") @DefaultValue("10") int perPage) {

    if (text.isEmpty() || page < 0 || perPage <= 0) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "text").toWebApplicationException();
    }
    int rowCount = areaDao.getRowCountFilteredByArea(text);
    if (rowCount == 0) {
      return Response.ok().entity(new AreaResponse(Collections.emptyList(), page, perPage, rowCount, 0)).build();
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

    List<Area> resultsFromDB = areaDao.find(text, page, perPage);
    AreaResponse areaResponse = new AreaResponse(resultsFromDB, page, perPage, rowCount, pageCount);

    return Response.ok().entity(areaResponse).build();
  }

  @GET
  @Path("/{area_id}")
  public Response getAreaById(@PathParam("area_id") Integer areaId) {
    if (areaId == null) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "areaId").toWebApplicationException();
    }
    Area area = areaDao.getById(areaId);
    if (area == null) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "areaId").toWebApplicationException();
    }
    AreaJson areaJson = area.toAreaJson();
    return Response.ok().entity(areaJson).build();
  }
}
