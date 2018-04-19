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
import java.util.List;
import java.util.stream.Collectors;

@Path("/areas")
@Produces(MediaType.APPLICATION_JSON)
public class AreaSearchResource {

  private final AreaDao areaDao;

  public AreaSearchResource(AreaDao areaDao) {
    this.areaDao = areaDao;
  }

  @GET
  public AreaResponse searchAreas(@QueryParam("text") String text,
                                  @QueryParam("page") @DefaultValue("0") int page,
                                  @QueryParam("perPage") @DefaultValue("10") int perPage) {

    PaginationHelper.checkInputParameters(text, page, perPage);

    int rowCount = areaDao.getRowCountFilteredByArea(text);
    if (rowCount <= 0) {
      return new AreaResponse();
    }

    int pageCount = PaginationHelper.calculatePagesCount(rowCount, perPage);
    if (page > pageCount - 1) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "page").toWebApplicationException();
    }

    List<Area> resultsFromDB = areaDao.findAreas(text, page, perPage);

    List<AreaJson> items = resultsFromDB.stream().map(Area::toAreaJson).collect(Collectors.toList());

    return new AreaResponse(items, page, perPage, rowCount, pageCount);
  }

  @GET
  @Path("/{area_id}")
  public AreaJson getAreaById(@PathParam("area_id") Integer areaId) {
    if (areaId == null) {
      throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "areaId").toWebApplicationException();
    }
    Area area = areaDao.getAreaById(areaId);
    if (area == null) {
      throw new Errors(Response.Status.NOT_FOUND, "NOT_FOUND", "areaId").toWebApplicationException();
    }
    return area.toAreaJson();
  }
}
