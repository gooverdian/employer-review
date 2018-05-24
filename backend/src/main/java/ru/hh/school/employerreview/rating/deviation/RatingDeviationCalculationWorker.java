package ru.hh.school.employerreview.rating.deviation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.hh.school.employerreview.OfflineCalculationConfig;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.rating.RatingDao;

import java.util.List;
import java.util.Map;

import static ru.hh.nab.common.util.PropertiesUtils.setSystemPropertyIfAbsent;

public class RatingDeviationCalculationWorker {

  private static ApplicationContext applicationContext;
  private static final int PER_PAGE = 1000;
  private static final int MIN_SELECTION_SIZE = 3;
  private static RatingDao ratingDao;

  public static void main(String[] args) {
    if (applicationContext == null) {
      setSystemPropertyIfAbsent("settingsDir", "src/etc");
      applicationContext = new AnnotationConfigApplicationContext(OfflineCalculationConfig.class);
    }

    ratingDao = applicationContext.getBean(RatingDao.class);
    EmployerDao employerDao = applicationContext.getBean(EmployerDao.class);

    Integer maxEmployerSize = employerDao.countRows();
    ratingDao.deleteAllRatingDeviations();
    int page = 0;
    while (PER_PAGE * page < maxEmployerSize) {
      calculateDeviations(employerDao.findEmployers("", page, PER_PAGE, true));
      page += 1;
    }
  }

  private static void calculateDeviations(List<Employer> employers) {
    List<Map> stars = ratingDao.getStarsByEmployers(employers);
    for (int i = 0; i < employers.size(); i++) {
      if (employers.get(i).getRating() != null && employers.get(i).getRating().getPeopleRated() >= MIN_SELECTION_SIZE) {
        Float deviation = calculateDeviation(stars.get(i), employers.get(i).getRating().getRating());
        ratingDao.saveRatingDeviation(new RatingDeviation(employers.get(i), deviation));
      }
    }
  }

  public static Float calculateDeviation(Map<Float, Integer> stars, Float mean) {
    Float deviation = 0f;
    int selectionSize = 0;
    for (Map.Entry<Float, Integer> entry : stars.entrySet()) {
      deviation += (mean - entry.getKey()) * (mean - entry.getKey()) * entry.getValue() * entry.getValue();
      selectionSize += entry.getValue();
    }
    return deviation / selectionSize;
  }

  public static void setApplicationContext(ApplicationContext applicationContext) {
    RatingDeviationCalculationWorker.applicationContext = applicationContext;
  }
}
