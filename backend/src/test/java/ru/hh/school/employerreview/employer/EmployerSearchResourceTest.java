package ru.hh.school.employerreview.employer;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import ru.hh.school.employerreview.EmployerReviewTest;
import ru.hh.school.employerreview.TestConfig;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.employer.dto.EmployersResponse;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

@ContextConfiguration(classes = {TestConfig.class})
public class EmployerSearchResourceTest extends EmployerReviewTest {

  @Inject
  private EmployerSearchResource resource;
  @Inject
  private AreaDao areaDao;
  @Inject
  private EmployerDao employerDao;

  @Test
  public void testEmployerSearch() {
    Area area = areaDao.getById(testAreaId);
    if (area == null) {
      area = new Area(testAreaName, testAreaId, -1);
      areaDao.save(area);
    }

    Employer employer = employerDao.getEmployer(testEmployerId);
    if (employer == null) {
      employer = new Employer(testEmployerName, "url", 1);
      employer.setArea(area);
      employerDao.save(employer);
    }

    EmployersResponse employersResponse = resource.searchEmployers(testEmployerName, 0, 10);
    Assert.assertEquals(employersResponse.getFound(), 1);
  }

  @Test
  public void testGetEmployerById() {
    Area area = areaDao.getById(testAreaId);
    if (area == null) {
      area = new Area(testAreaName, testAreaId, -1);
      areaDao.save(area);
    }

    Employer employer = employerDao.getEmployer(testEmployerId);
    if (employer == null) {
      employer = new Employer(testEmployerName, "url", 1);
      employer.setArea(area);
      employerDao.save(employer);
    }
    Assert.assertNotNull(resource.getEmployerById(testEmployerId));
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
