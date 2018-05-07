package ru.hh.school.employerreview.area;

import org.junit.Assert;
import org.junit.Test;
import ru.hh.school.employerreview.EmployerReviewTestBase;
import ru.hh.school.employerreview.area.dto.AreaResponse;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

public class AreaSearchResourceTest extends EmployerReviewTestBase {

  @Inject
  private AreaSearchResource resource;
  @Inject
  private AreaDao areaDao;

  @Test
  public void testAreaSearch() {
    areaDao.save(area);
    AreaResponse areaResponse = resource.searchAreas(testAreaName, 0, 10);
    Assert.assertEquals(1, areaResponse.getFound());
    Assert.assertEquals(area, areaResponse.getItems().get(0).toArea());
    areaDao.deleteArea(area);
  }

  @Test
  public void testGetAreaById() {
    areaDao.save(area);
    Assert.assertEquals(area, resource.getAreaById(area.getId()).toArea());
    areaDao.deleteArea(area);
  }

  @Test(expected = WebApplicationException.class)
  public void testEmptyRequest() {
    resource.searchAreas("", 0, 10);
  }

  @Test(expected = WebApplicationException.class)
  public void testNegativePageValue() {
    resource.searchAreas("d", -1, 10);
  }

  @Test(expected = WebApplicationException.class)
  public void testNegativePerPageValue() {
    resource.searchAreas("d", 0, -1);
  }

  @Test(expected = WebApplicationException.class)
  public void testGetAreaByNullValue() {
    resource.getAreaById(null);
  }
}
