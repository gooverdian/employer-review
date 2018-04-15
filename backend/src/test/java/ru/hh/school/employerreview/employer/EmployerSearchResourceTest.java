package ru.hh.school.employerreview.employer;

import org.junit.Assert;
import org.junit.Test;
import ru.hh.school.employerreview.TestBase;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.employer.dto.EmployersResponse;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

public class EmployerSearchResourceTest extends TestBase {

  @Inject
  private EmployerSearchResource resource;
  @Inject
  private AreaDao areaDao;
  @Inject
  private EmployerDao employerDao;

  @Test
  public void testEmployerSearch() {
    Area area = new Area(testAreaName, testAreaId, -1);
    areaDao.save(area);

    Employer employer = new Employer(testEmployerName, "url", 1);
    employer.setArea(area);
    employerDao.save(employer);

    EmployersResponse employersResponse = resource.searchEmployers(testEmployerName, 0, 10);
    Assert.assertEquals(employersResponse.getFound(), 1);
    Assert.assertEquals(employersResponse.getItems().get(0).toEmployer(), employer);

    employerDao.delete(employer);
    areaDao.delete(area);
  }

  @Test
  public void testGetEmployerById() {
    Area area = new Area(testAreaName, testAreaId, -1);
    areaDao.save(area);

    Employer employer = new Employer(testEmployerName, "url", 1);
    employer.setArea(area);
    employerDao.save(employer);

    Assert.assertEquals(resource.getEmployerById(employer.getId()).toEmployer(), employer);

    employerDao.delete(employer);
    areaDao.delete(area);
  }

  @Test(expected = WebApplicationException.class)
  public void testEmptyRequest() {
    resource.searchEmployers("", 0, 10);
  }

  @Test(expected = WebApplicationException.class)
  public void testNegativePageValue() {
    resource.searchEmployers("d", -1, 10);
  }

  @Test(expected = WebApplicationException.class)
  public void testNegativePerPageValue() {
    resource.searchEmployers("d", 0, -1);
  }

  @Test(expected = WebApplicationException.class)
  public void testGetEmployerByNullValue() {
    resource.getEmployerById(null);
  }
}
