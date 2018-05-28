package ru.hh.school.employerreview.statistic;

import ru.hh.school.employerreview.statistic.employment.DurationByProffFieldDao;
import ru.hh.school.employerreview.statistic.main.MainPageStatistic;
import ru.hh.school.employerreview.statistic.main.MainPageStatisticDao;
import ru.hh.school.employerreview.statistic.main.MainPageStatisticType;
import ru.hh.school.employerreview.statistic.salary.EmployerSalaryStatisticsDao;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/statistic")
@Produces(MediaType.APPLICATION_JSON)
public class StatisticResource {
  private final MainPageStatisticDao mainPageStatisticDao;
  private final EmployerSalaryStatisticsDao employerSalaryStatisticsDao;
  private final DurationByProffFieldDao durationByProffFieldDao;

  public StatisticResource(MainPageStatisticDao mainPageStatisticDao,
                           EmployerSalaryStatisticsDao employerSalaryStatisticsDao,
                           DurationByProffFieldDao durationByProffFieldDao) {
    this.mainPageStatisticDao = mainPageStatisticDao;
    this.employerSalaryStatisticsDao = employerSalaryStatisticsDao;
    this.durationByProffFieldDao = durationByProffFieldDao;
  }

  @GET
  public Map getMainPageStatistic() {
    Map<MainPageStatisticType, Integer> statistic = new HashMap<>();

    MainPageStatistic reviewCount = mainPageStatisticDao.getReviewCount();
    statistic.put(reviewCount.getKey(), reviewCount.getValue());

    MainPageStatistic employerCountWithReview = mainPageStatisticDao.getEmployerWithReviewCount();
    statistic.put(employerCountWithReview.getKey(), employerCountWithReview.getValue());

    return statistic;
  }

  @GET
  @Path("/salary/by_proff_field/{employer_id}")
  public Map<String, Float> getAverageSalaryEmployerByProffField(@PathParam("employer_id") Integer employerId) {
    return employerSalaryStatisticsDao.getAverageSalaryMapByProffField(employerId);
  }

  @GET
  @Path("/employment_duration/by_proff_field/{employer_id}")
  public Map<String, Float> getAverageEmploymentDurationEmployerByProffField(@PathParam("employer_id") Integer employerId) {
    return durationByProffFieldDao.getAverageEmploymentDurationMap(employerId);
  }
}
