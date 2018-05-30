package ru.hh.school.employerreview.statistic.employment;

import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.review.Review;
import ru.hh.school.employerreview.review.ReviewDao;
import ru.hh.school.employerreview.specializations.ProfessionalField;
import ru.hh.school.employerreview.specializations.ProfessionalFieldDao;
import ru.hh.school.employerreview.statistic.main.MainPageStatisticDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DurationByProffFieldCalculationWorker {
  private final DurationByProffFieldDao employmentDurationDao;
  private final ReviewDao reviewDao;
  private final ProfessionalFieldDao professionalFieldDao;
  private final EmployerDao employerDao;
  private final MainPageStatisticDao mainPageStatisticDao;

  private static final int PER_PAGE = 1000;

  static class Duration {
    Float durationSum;
    Integer counter;
    Duration(Float durationSum, Integer counter) {
      this.durationSum = durationSum;
      this.counter = counter;
    }
  }

  public DurationByProffFieldCalculationWorker(
      DurationByProffFieldDao employmentDurationDao,
      ReviewDao reviewDao,
      ProfessionalFieldDao professionalFieldDao,
      EmployerDao employerDao,
      MainPageStatisticDao mainPageStatisticDao) {
    this.reviewDao = reviewDao;
    this.professionalFieldDao = professionalFieldDao;
    this.employmentDurationDao = employmentDurationDao;
    this.employerDao = employerDao;
    this.mainPageStatisticDao = mainPageStatisticDao;
  }

  void doWork() {
    employmentDurationDao.deleteAllAverageEmploymentDuration();

    Map<Integer, Duration> mainPageDurationMap = new HashMap<>();
    int maxEmployerSize = employerDao.countRows();
    int page = 0;
    while (PER_PAGE * page < maxEmployerSize) {
      List<Employer> employers = employerDao.findEmployers("", page, PER_PAGE, true);
      List<Map<ProfessionalField, Float>> durationMaps = new ArrayList<>();

      // Employers are ordered by rating, when rating = null, we wont receive any reviews more
      if (!employers.isEmpty() && employers.get(0).getRating() == null) {
        return;
      }

      for (Employer employer : employers) {
        durationMaps.add(getAverageDurationMap(employer, mainPageDurationMap));
      }
      employmentDurationDao.saveAverageDurationMaps(employers, durationMaps);
      mainPageStatisticDao.saveMainPageEmploymentMap(mainPageDurationMap.entrySet().stream()
          .collect(Collectors.toMap(
              s -> professionalFieldDao.getById(s.getKey()),
              s -> s.getValue().durationSum / s.getValue().counter
          )));

      page += 1;
    }
  }

  private Map<ProfessionalField, Float> getAverageDurationMap(Employer employer, Map<Integer, Duration> mainPageDurationMap) {
    Map<Integer, Duration> durationMap = new HashMap();

    int maxReviewSize = reviewDao.getRowCountFilteredByEmployer(employer.getId(), null).intValue();

    int page = 0;
    while (PER_PAGE * page < maxReviewSize) {
      calculateDurationMap(reviewDao.getPaginatedFilteredByEmployerWithSpecializations(employer.getId(), page, PER_PAGE, null),
          durationMap, mainPageDurationMap);
      page += 1;
    }
    return durationMap.entrySet().stream()
        .collect(Collectors.toMap(
            s -> professionalFieldDao.getById(s.getKey()),
            s -> s.getValue().durationSum / s.getValue().counter
        ));
  }

  private static void calculateDurationMap(List<Review> reviews,
                                           Map<Integer, Duration> durationMap,
                                           Map<Integer, Duration> mainPageDurationMap) {
    for (Review review : reviews) {
      if (!review.getSpecializations().isEmpty() && review.getEmploymentDuration() != null) {

        // it is supposed - all specialization are from one prof. field
        ProfessionalField professionalField = review.getSpecializations().get(0).getProfessionalField();

        addOrIncrement(durationMap, review, professionalField);
        addOrIncrement(mainPageDurationMap, review, professionalField);
      }
    }
  }

  private static void addOrIncrement(Map<Integer, Duration> durationMap, Review review, ProfessionalField professionalField) {
    if (!durationMap.containsKey(professionalField.getId())) {
      durationMap.put(professionalField.getId(), new Duration(review.getEmploymentDuration().floatValue(), 1));
    } else {
      durationMap.get(professionalField.getId()).durationSum += review.getEmploymentDuration();
      durationMap.get(professionalField.getId()).counter += 1;
    }
  }
}
