package ru.hh.school.employerreview.statistic;

import ru.hh.school.employerreview.statistic.main.MainPageStatistic;
import ru.hh.school.employerreview.statistic.main.MainPageStatisticDao;
import ru.hh.school.employerreview.statistic.main.MainPageStatisticType;
import ru.hh.school.employerreview.statistic.salary.AverageSalaryEmployerByProffFieldDao;

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
  private final AverageSalaryEmployerByProffFieldDao averageSalaryEmployerByProffFieldDao;

  public StatisticResource(MainPageStatisticDao mainPageStatisticDao,
                           AverageSalaryEmployerByProffFieldDao averageSalaryEmployerByProffFieldDao) {
    this.mainPageStatisticDao = mainPageStatisticDao;
    this.averageSalaryEmployerByProffFieldDao = averageSalaryEmployerByProffFieldDao;
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
    return averageSalaryEmployerByProffFieldDao.getAverageSalaryEmployerByProffField(employerId);
  }
}
