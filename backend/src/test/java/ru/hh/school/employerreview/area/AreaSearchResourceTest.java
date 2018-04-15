package ru.hh.school.employerreview.area;

import org.junit.Assert;
import org.junit.Test;
import ru.hh.school.employerreview.TestBase;
import ru.hh.school.employerreview.area.dto.AreaResponse;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

public class AreaSearchResourceTest extends TestBase {

  @Inject
  private AreaSearchResource resource;
  @Inject
  private AreaDao areaDao;

  @Test
  public void testAreaSearch() {
    Area area = new Area(testAreaName, testAreaId, -1);
    areaDao.save(area);
    AreaResponse areaResponse = resource.searchAreas(testAreaName, 0, 10);
    Assert.assertEquals(areaResponse.getFound(), 1);
    Assert.assertEquals(areaResponse.getItems().get(0).toArea(), area);
    areaDao.delete(area);
  }

  @Test
  public void testGetAreaById() {
    Area area = new Area(testAreaName, testAreaId, -1);
    areaDao.save(area);
    Assert.assertEquals(resource.getAreaById(area.getId()).toArea(), area);
    areaDao.delete(area);
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
