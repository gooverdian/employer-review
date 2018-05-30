package ru.hh.school.employerreview.statistic.salary;

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

public class EmployerSalaryStatisticsCalculationWorker {

  private final EmployerSalaryStatisticsDao employerSalaryStatisticsDao;
  private final ReviewDao reviewDao;
  private final ProfessionalFieldDao professionalFieldDao;
  private final EmployerDao employerDao;
  private final MainPageStatisticDao mainPageStatisticDao;

  private static final int PER_PAGE = 1000;

  static class Salary {
    Float salarySum;
    Integer counter;
    Salary(Float salarySum, Integer counter) {
      this.salarySum = salarySum;
      this.counter = counter;
    }
  }

  public EmployerSalaryStatisticsCalculationWorker(
      EmployerSalaryStatisticsDao employerSalaryStatisticsDao,
      ReviewDao reviewDao,
      ProfessionalFieldDao professionalFieldDao,
      EmployerDao employerDao,
      MainPageStatisticDao mainPageStatisticDao) {
    this.reviewDao = reviewDao;
    this.professionalFieldDao = professionalFieldDao;
    this.employerSalaryStatisticsDao = employerSalaryStatisticsDao;
    this.employerDao = employerDao;
    this.mainPageStatisticDao = mainPageStatisticDao;
  }

  void doWork() {
    employerSalaryStatisticsDao.deleteAllAverageSalariesByProffField();
    mainPageStatisticDao.deleteAllMainPageReviewCounter();
    mainPageStatisticDao.deleteAllMainPageSalary();

    Map<Integer, Salary> mainPageSalaryMap = new HashMap<>();
    Map<Integer, Integer> mainPageReviewCounterMap = new HashMap<>();
    int maxEmployerSize = employerDao.countRows();
    int page = 0;
    while (PER_PAGE * page < maxEmployerSize) {
      List<Employer> employers = employerDao.findEmployers("", page, PER_PAGE, true);
      List<Map<ProfessionalField, Float>> salaryMaps = new ArrayList<>();

      // Employers are ordered by rating, when rating = null, we wont receive any reviews more
      if (!employers.isEmpty() && employers.get(0).getRating() == null) {
        return;
      }

      for (Employer employer : employers) {
        salaryMaps.add(getAverageSalaryMap(employer, mainPageSalaryMap, mainPageReviewCounterMap));
      }
      employerSalaryStatisticsDao.saveSalaryMapsByProffField(employers, salaryMaps);
      mainPageStatisticDao.saveMainPageSalaryMap(mainPageSalaryMap.entrySet().stream()
          .collect(Collectors.toMap(
              s -> professionalFieldDao.getById(s.getKey()),
              s -> s.getValue().salarySum / s.getValue().counter
          )));
      mainPageStatisticDao.saveMainPageReviewCounterMap(mainPageReviewCounterMap.entrySet().stream()
          .collect(Collectors.toMap(
              s -> professionalFieldDao.getById(s.getKey()),
              s -> s.getValue()
          )));

      page += 1;
    }
  }

  private Map<ProfessionalField, Float> getAverageSalaryMap(Employer employer,
                                                            Map<Integer, Salary> mainPageSalaryMap,
                                                            Map<Integer, Integer> mainPageReviewCounterMap) {
    Map<Integer, Salary> salaryMap = new HashMap();

    int maxReviewSize = reviewDao.getRowCountFilteredByEmployer(employer.getId(), null).intValue();

    int page = 0;
    while (PER_PAGE * page < maxReviewSize) {
      calculateSalaryMap(reviewDao.getPaginatedFilteredByEmployerWithSpecializations(employer.getId(), page, PER_PAGE, null),
          salaryMap, mainPageSalaryMap, mainPageReviewCounterMap);
      page += 1;
    }
    return salaryMap.entrySet().stream()
        .collect(Collectors.toMap(
            s -> professionalFieldDao.getById(s.getKey()),
            s -> s.getValue().salarySum / s.getValue().counter
        ));
  }

  private static void calculateSalaryMap(List<Review> reviews,
                                         Map<Integer, Salary> salaryMap,
                                         Map<Integer, Salary> mainPageSalaryMap,
                                         Map<Integer, Integer> mainPageReviewCounterMap) {
    for (Review review : reviews) {
      if (!review.getSpecializations().isEmpty()) {

        // it is supposed - all specialization are from one prof. field
        ProfessionalField professionalField = review.getSpecializations().get(0).getProfessionalField();

        if (review.getSalary() != null) {
          addOrIncrement(salaryMap, review, professionalField);
          addOrIncrement(mainPageSalaryMap, review, professionalField);
        }

        if (!mainPageReviewCounterMap.containsKey(professionalField.getId())) {
          mainPageReviewCounterMap.put(professionalField.getId(), 1);
        } else {
          mainPageReviewCounterMap.put(professionalField.getId(), mainPageReviewCounterMap.get(professionalField.getId()) + 1);
        }
      }
    }
  }

  private static void addOrIncrement(Map<Integer, Salary> salaryMap, Review review, ProfessionalField professionalField) {
    if (!salaryMap.containsKey(professionalField.getId())) {
      salaryMap.put(professionalField.getId(), new Salary(review.getSalary().floatValue(), 1));
    } else {
      salaryMap.get(professionalField.getId()).salarySum += review.getSalary();
      salaryMap.get(professionalField.getId()).counter += 1;
    }
  }
}
