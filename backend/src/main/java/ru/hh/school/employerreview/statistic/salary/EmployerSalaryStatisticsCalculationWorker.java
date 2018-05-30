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

    Map<Integer, Salary> mainPageSalaryMap = new HashMap<>();
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
        salaryMaps.add(getAverageSalaryMap(employer, mainPageSalaryMap));
      }
      employerSalaryStatisticsDao.saveSalaryMapsByProffField(employers, salaryMaps);
      mainPageStatisticDao.saveMainPageSalaryMap(mainPageSalaryMap.entrySet().stream()
          .collect(Collectors.toMap(
              s -> professionalFieldDao.getById(s.getKey()),
              s -> s.getValue().salarySum / s.getValue().counter
          )));

      page += 1;
    }
  }

  private Map<ProfessionalField, Float> getAverageSalaryMap(Employer employer, Map<Integer, Salary> mainPageSalaryMap) {
    Map<Integer, Salary> salaryMap = new HashMap();

    int maxReviewSize = reviewDao.getRowCountFilteredByEmployer(employer.getId(), null).intValue();

    int page = 0;
    while (PER_PAGE * page < maxReviewSize) {
      calculateSalaryMap(reviewDao.getPaginatedFilteredByEmployerWithSpecializations(employer.getId(), page, PER_PAGE, null),
          salaryMap, mainPageSalaryMap);
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
                                         Map<Integer, Salary> mainPageSalaryMap) {
    for (Review review : reviews) {
      if (!review.getSpecializations().isEmpty() && review.getSalary() != null) {

        // it is supposed - all specialization are from one prof. field
        ProfessionalField professionalField = review.getSpecializations().get(0).getProfessionalField();

        addOrIncrement(salaryMap, review, professionalField);
        addOrIncrement(mainPageSalaryMap, review, professionalField);
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
