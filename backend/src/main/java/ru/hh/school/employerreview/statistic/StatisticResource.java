package ru.hh.school.employerreview.statistic;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/statistic")
@Produces(MediaType.APPLICATION_JSON)
public class StatisticResource {
  private final MainPageStatisticDao mainPageStatisticDao;

  public StatisticResource(MainPageStatisticDao mainPageStatisticDao) {
    this.mainPageStatisticDao = mainPageStatisticDao;
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
}
