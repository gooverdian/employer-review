package ru.hh.school.employerreview.downloader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import ru.hh.school.employerreview.TestConfig;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.employer.EmployerDao;

import javax.inject.Inject;

@ContextConfiguration(classes = {TestConfig.class})
public class DownloadMainTest extends AbstractJUnit4SpringContextTests {

  @Inject
  private EmployerDao employerDao;
  @Inject
  private AreaDao areaDao;

  @Before
  public void prepareData() {
    areaDao.truncate();
    employerDao.truncate();
  }

  @After
  public void clearData() {
    areaDao.truncate();
    employerDao.truncate();
  }

  @Test(expected = NumberFormatException.class)
  public void testNoNumericArgument() {
    String [] args = {"d", "r"};
    DownloadMain.main(args);
    Assert.assertEquals(employerDao.countRows(), 0);
    Assert.assertEquals(areaDao.countRows(), 0);
  }
}
