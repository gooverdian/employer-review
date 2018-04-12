package ru.hh.school.employerreview.employer;

import org.junit.Assert;
import org.junit.Test;
import ru.hh.school.employerreview.EmployerReviewTestBase;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.employer.dto.EmployerItem;
import ru.hh.school.employerreview.employer.dto.EmployersResponse;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

public class EmployerSearchResourceTest extends EmployerReviewTestBase {

  @Inject
  private EmployerSearchResource resource;
  @Inject
  private AreaDao areaDao;
  @Inject
  private EmployerDao employerDao;

  @Test
  public void testEmployerSearch() {
    areaDao.save(area);
    employer.setArea(area);
    employerDao.save(employer);

    EmployersResponse employersResponse = resource.searchEmployers(testEmployerName, 0, 10);
    Assert.assertEquals(employersResponse.getFound(), 1);
    Assert.assertEquals(employersResponse.getItems().get(0).toEmployer(), employer);

    employerDao.deleteEmployer(employer);
    areaDao.deleteArea(area);
  }

  @Test
  public void testGetEmployerById() {
    areaDao.save(area);
    employer.setArea(area);
    employerDao.save(employer);

    Assert.assertEquals(resource.getEmployerById(employer.getId()).toEmployer(), employer);

    employerDao.deleteEmployer(employer);
    areaDao.deleteArea(area);
  }

  @Test
  public void testAddNewEmployer() {
    areaDao.save(area);
    EmployerItem employerItem = new EmployerItem();
    employerItem.setAreaId(testAreaId);
    employerItem.setUrl("url");
    employerItem.setName(testEmployerName);

    Employer employer = employerDao.getEmployer(resource.postEmployer(employerItem).getId());

    Assert.assertEquals(employer.getName(), employerItem.getName());
    Assert.assertEquals(employer.getArea().getId(), employerItem.getAreaId());
    Assert.assertEquals(employer.getSiteUrl(), employerItem.getUrl());
    Assert.assertNull(employer.getHhId());

    employerDao.deleteEmployer(employer);
    areaDao.deleteArea(area);
  }

  @Test
  public void testAddNewEmployerWithEmptyArea() {
    EmployerItem employerItem = new EmployerItem();
    employerItem.setUrl("url");
    employerItem.setName(testEmployerName);

    Employer employer = employerDao.getEmployer(resource.postEmployer(employerItem).getId());

    Assert.assertEquals(employer.getName(), employerItem.getName());
    Assert.assertEquals(employer.getSiteUrl(), employerItem.getUrl());
    Assert.assertNull(employer.getHhId());

    employerDao.deleteEmployer(employer);
  }

  @Test(expected = WebApplicationException.class)
  public void testAddNullEmployer() {
    resource.postEmployer(null);
  }

  @Test(expected = WebApplicationException.class)
  public void testAddNewEmployerWithWrongArea() {
    EmployerItem employerItem = new EmployerItem();
    employerItem.setAreaId(-1);
    employerItem.setUrl("url");
    employerItem.setName(testEmployerName);

    resource.postEmployer(employerItem);
  }

  @Test(expected = WebApplicationException.class)
  public void testAddEmployerAlreadyInHH() {
    EmployerItem employerItem = new EmployerItem();
    employerItem.setAreaId(testAreaId);
    employerItem.setUrl("url");
    employerItem.setName(testEmployerName);
    employerItem.setHhId(5254);
    resource.postEmployer(employerItem);
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
