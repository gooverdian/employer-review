package ru.hh.school.employerreview.specializations;

import org.junit.After;
import org.junit.Before;
import ru.hh.school.employerreview.EmployerReviewTestBase;
import ru.hh.school.employerreview.TestConfig;
import ru.hh.school.employerreview.downloader.AbstractDownloader;
import ru.hh.school.employerreview.downloader.SpecializationsDownloader;

import javax.inject.Inject;

public abstract class SpecializationsCommonTest extends EmployerReviewTestBase {

  @Inject
  protected SpecializationDao specializationDao;
  @Inject
  protected TestConfig.TestQueryExecutorDao testQueryExecutorDao;

  @Before
  public void prepareDb() {
    AbstractDownloader.setApplicationContext(applicationContext);
    SpecializationsDownloader.main();
  }

  @After
  public void deleteEntities() {
    testQueryExecutorDao.executeQuery("delete from Specialization");
    testQueryExecutorDao.executeQuery("delete from ProfessionalField");
    testQueryExecutorDao.executeQuery("delete from Area");
  }
}
