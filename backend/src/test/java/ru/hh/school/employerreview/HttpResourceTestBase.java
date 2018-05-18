package ru.hh.school.employerreview;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.context.ContextConfiguration;
import ru.hh.nab.testbase.JerseyTest;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.position.PositionDao;
import ru.hh.school.employerreview.review.ReviewDao;
import ru.hh.school.employerreview.specializations.SpecializationDao;

import javax.inject.Inject;

@ContextConfiguration(classes = {TestConfig.class})
public abstract class HttpResourceTestBase extends JerseyTest {
  protected final static String HOST = "http://localhost:8081/";
  protected final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Inject
  protected EmployerDao employerDao;

  @Inject
  protected ReviewDao reviewDao;

  @Inject
  protected PositionDao positionDao;

  @Inject
  protected AreaDao areaDao;

  @Inject
  protected SpecializationDao specializationDao;

  @Inject
  protected TestConfig.TestQueryExecutorDao testQueryExecutorDao;
}
