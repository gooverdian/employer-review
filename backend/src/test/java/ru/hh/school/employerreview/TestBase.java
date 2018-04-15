package ru.hh.school.employerreview;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(classes = {TestConfig.class})
abstract public class TestBase extends AbstractJUnit4SpringContextTests {
  protected final int testAreaId = 113;
  protected final String testAreaName = "Россия";
  protected final String testEmployerName = "Test";
}
