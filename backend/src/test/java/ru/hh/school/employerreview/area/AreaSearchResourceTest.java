package ru.hh.school.employerreview.area;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import ru.hh.school.employerreview.TestConfig;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

@ContextConfiguration(classes = {TestConfig.class})
public class AreaSearchResourceTest extends AbstractJUnit4SpringContextTests {

  @Inject
  private AreaSearchResource resource;

  @Test(expected = WebApplicationException.class)
  public void testEmptyRequest() {
    resource.areasSearch("", 0, 10);
  }

  @Test(expected = WebApplicationException.class)
  public void testPageNegativeValue() {
    resource.areasSearch("d", -1, 10);
  }

  @Test(expected = WebApplicationException.class)
  public void testPerPageValue() {
    resource.areasSearch("d", 0, -1);
  }

  @Test(expected = WebApplicationException.class)
  public void testGetEmployerById() {
    resource.getAreaById(null);
  }
}
