package ru.hh.school.employerreview.area;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import ru.hh.school.employerreview.EmployerReviewTest;
import ru.hh.school.employerreview.TestConfig;
import ru.hh.school.employerreview.area.dto.AreaResponse;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

@ContextConfiguration(classes = {TestConfig.class})
public class AreaSearchResourceTest extends EmployerReviewTest {

  @Inject
  private AreaSearchResource resource;
  @Inject
  private AreaDao areaDao;

  @Test
  public void testAreaSearch() {
    Area area = areaDao.getById(testAreaId);
    if (area == null) {
      areaDao.save(new Area(testAreaName, testAreaId, -1));
    }
    AreaResponse areaResponse = resource.searchAreas(testAreaName, 0, 10);
    Assert.assertEquals(areaResponse.getFound(), 1);
  }

  @Test
  public void testGetAreaById() {
    Area area = areaDao.getById(testAreaId);
    if (area == null) {
      areaDao.save(new Area(testAreaName, testAreaId, -1));
    }
    Assert.assertNotNull(resource.getAreaById(testAreaId));
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
