package ru.hh.school.employerreview.statistic.salary;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.hh.school.employerreview.EmployerReviewTestBase;
import ru.hh.school.employerreview.TestConfig;
import ru.hh.school.employerreview.downloader.AbstractDownloader;
import ru.hh.school.employerreview.downloader.SpecializationsDownloader;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.review.ReviewResource;
import ru.hh.school.employerreview.review.ReviewType;
import ru.hh.school.employerreview.review.dto.ReviewDto;
import ru.hh.school.employerreview.specializations.SpecializationDao;
import ru.hh.school.employerreview.specializations.dto.SpecializationDto;
import ru.hh.school.employerreview.statistic.StatisticResource;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EmployerSalaryStatisticsCalculationWorkerTest extends EmployerReviewTestBase {

  @Inject
  private TestConfig.TestQueryExecutorDao testQueryExecutorDao;
  @Inject
  private EmployerDao employerDao;
  @Inject
  private ReviewResource reviewResource;
  @Inject
  private StatisticResource statisticResource;
  @Inject
  private SpecializationDao specializationDao;

  private ReviewDto reviewDtoCreator(List<SpecializationDto> specializationDtos, Integer salary) {
    ReviewDto reviewDto = new ReviewDto();
    reviewDto.setEmployerId(employer.getId());
    reviewDto.setRating(1f);
    reviewDto.setText("good");
    reviewDto.setReviewType(ReviewType.EMPLOYEE);
    reviewDto.setSalary(salary);
    reviewDto.setSpecializations(specializationDtos);
    return reviewDto;
  }

  @Before
  public void beforeTest() {
    applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);

    EmployerSalaryStatisticsCalculationLauncher.setApplicationContext(applicationContext);

    AbstractDownloader.setApplicationContext(applicationContext);
    SpecializationsDownloader.main();

    employerDao.save(employer);
  }

  @Test
  public void calculateAverageSalaryTest() {

    int specialization1Id = 1;
    int specialization2Id = 150;

    List<SpecializationDto> specializations1 = new ArrayList<>();
    specializations1.add(new SpecializationDto(specialization1Id, "test"));
    List<SpecializationDto> specializations2 = new ArrayList<>();
    specializations2.add(new SpecializationDto(specialization2Id, "test"));

    ReviewDto reviewDto1 = reviewDtoCreator(specializations1, 1000);
    ReviewDto reviewDto2 = reviewDtoCreator(specializations1, 2000);
    ReviewDto reviewDto3 = reviewDtoCreator(specializations1, 3000);

    ReviewDto reviewDto4 = reviewDtoCreator(specializations2, 3000);
    ReviewDto reviewDto5 = reviewDtoCreator(specializations2, 4000);
    ReviewDto reviewDto6 = reviewDtoCreator(specializations2, 5000);

    reviewResource.postReview(reviewDto1);
    reviewResource.postReview(reviewDto2);
    reviewResource.postReview(reviewDto3);
    reviewResource.postReview(reviewDto4);
    reviewResource.postReview(reviewDto5);
    reviewResource.postReview(reviewDto6);

    EmployerSalaryStatisticsCalculationLauncher.main();

    Map<String, Float> result = statisticResource.getAverageSalaryEmployerByProffField(employer.getId());
    Assert.assertEquals(2, result.size());
    Assert.assertEquals(2000, result.get(specializationDao.getById(specialization1Id).getProfessionalField().getName()).intValue());
    Assert.assertEquals(4000, result.get(specializationDao.getById(specialization2Id).getProfessionalField().getName()).intValue());

    result = statisticResource.getAverageSalaryByProffField();
    Assert.assertEquals(2, result.size());
    Assert.assertEquals(2000, result.get(specializationDao.getById(specialization1Id).getProfessionalField().getName()).intValue());
    Assert.assertEquals(4000, result.get(specializationDao.getById(specialization2Id).getProfessionalField().getName()).intValue());

    Map<String, Integer> reviewCounterMap = statisticResource.getReviewCounterByProffField();
    Assert.assertEquals(2, reviewCounterMap.size());
    Assert.assertEquals(3, reviewCounterMap.get(specializationDao.getById(specialization1Id).getProfessionalField().getName()).intValue());
    Assert.assertEquals(3, reviewCounterMap.get(specializationDao.getById(specialization2Id).getProfessionalField().getName()).intValue());

    reviewCounterMap = statisticResource.getEmployerReviewCounterByProffField(employer.getId());
    Assert.assertEquals(2, reviewCounterMap.size());
    Assert.assertEquals(3, reviewCounterMap.get(specializationDao.getById(specialization1Id).getProfessionalField().getName()).intValue());
    Assert.assertEquals(3, reviewCounterMap.get(specializationDao.getById(specialization2Id).getProfessionalField().getName()).intValue());
  }

  @After
  public void afterTest() {
    testQueryExecutorDao.executeQuery("delete from MainPageReviewCounter");
    testQueryExecutorDao.executeQuery("delete from MainPageSalary");
    testQueryExecutorDao.executeQuery("delete from EmployerSalaryStatistics");
    testQueryExecutorDao.executeQuery("delete from Review");
    testQueryExecutorDao.executeQuery("delete from Specialization");
    testQueryExecutorDao.executeQuery("delete from ProfessionalField");
    testQueryExecutorDao.executeQuery("delete from MainPageStatistic");
    testQueryExecutorDao.executeQuery("delete from Employer");
    testQueryExecutorDao.executeQuery("delete from Rating");
  }
}
