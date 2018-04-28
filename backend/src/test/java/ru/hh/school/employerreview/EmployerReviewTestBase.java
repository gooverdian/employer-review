package ru.hh.school.employerreview;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.employer.Employer;

@ContextConfiguration(classes = {TestConfig.class})
abstract public class EmployerReviewTestBase extends AbstractJUnit4SpringContextTests {
  protected final int testAreaId = 113;
  protected final String testEmployerName = "Test";
  protected final String testAreaName = "Россия";
  protected final Area area = new Area(testAreaName, testAreaId, -1);
  protected final Employer employer = new Employer(testEmployerName, "url", 1);
  protected final Employer employer2 = new Employer(testEmployerName, "url", 2);
}
