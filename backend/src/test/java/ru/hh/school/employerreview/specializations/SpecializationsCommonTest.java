package ru.hh.school.employerreview.specializations;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.hh.school.employerreview.TestConfig;
import ru.hh.school.employerreview.downloader.AbstractDownloader;
import ru.hh.school.employerreview.downloader.SpecializationsDownloader;

public class SpecializationsCommonTest {

  protected ApplicationContext applicationContext;
  protected SpecializationDao specializationDao;

  @Before
  public void prepareDb() {
    applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);
    AbstractDownloader.setApplicationContext(applicationContext);
    SpecializationsDownloader.main();
    specializationDao = applicationContext.getBean(SpecializationDao.class);
  }
}
