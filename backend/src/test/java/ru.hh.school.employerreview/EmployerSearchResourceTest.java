package ru.hh.school.employerreview;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import ru.hh.school.employerreview.employer.EmployerSearchResource;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

@ContextConfiguration(classes = {TestConfig.class})
public class EmployerSearchResourceTest extends AbstractJUnit4SpringContextTests {

  @Inject
  private EmployerSearchResource resource;

  @Test(expected = WebApplicationException.class)
  public void testEmptyRequest() {
    resource.employersSearch("", 0, 10);
  }

  @Test(expected = WebApplicationException.class)
  public void testPageNegativeValue() {
    resource.employersSearch("d", -1, 10);
  }

  @Test(expected = WebApplicationException.class)
  public void testPerPageValue() {
    resource.employersSearch("d", 0, -1);
  }

  @Test(expected = WebApplicationException.class)
  public void testGetEmployerById() {
    resource.getEmployerById(null);
  }
}
