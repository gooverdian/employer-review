package ru.hh.school.employerreview.statistic.salary;

import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.review.Review;
import ru.hh.school.employerreview.review.ReviewDao;
import ru.hh.school.employerreview.specializations.ProfessionalField;
import ru.hh.school.employerreview.specializations.ProfessionalFieldDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AverageSalaryEmployerByProffFieldCalculationWorker {

  private final AverageSalaryEmployerByProffFieldDao averageSalaryEmployerByProffFieldDao;
  private final ReviewDao reviewDao;
  private final ProfessionalFieldDao professionalFieldDao;
  private final EmployerDao employerDao;

  private static final int PER_PAGE = 1000;

  static class Salary {
    Float salarySum;
    Integer counter;
    Salary(Float salarySum, Integer counter) {
      this.salarySum = salarySum;
      this.counter = counter;
    }
  }

  public AverageSalaryEmployerByProffFieldCalculationWorker(
      AverageSalaryEmployerByProffFieldDao averageSalaryEmployerByProffFieldDao,
      ReviewDao reviewDao,
      ProfessionalFieldDao professionalFieldDao,
      EmployerDao employerDao) {
    this.reviewDao = reviewDao;
    this.professionalFieldDao = professionalFieldDao;
    this.averageSalaryEmployerByProffFieldDao = averageSalaryEmployerByProffFieldDao;
    this.employerDao = employerDao;
  }

  void doWork() {
    averageSalaryEmployerByProffFieldDao.deleteAllAverageSalaryEmployerByProffField();

    Integer maxEmployerSize = employerDao.countRows();
    int page = 0;
    while (PER_PAGE * page < maxEmployerSize) {
      List<Employer> employers = employerDao.findEmployers("", page, PER_PAGE, true);
      List<Map<ProfessionalField, Float>> salaryMaps = new ArrayList<>();

      // Employers are ordered by rating, when rating = null, we wont receive any reviews more
      if (!employers.isEmpty() && employers.get(0).getRating() == null) {
        System.exit(0);
      }

      for (Employer employer : employers) {
        salaryMaps.add(getAverageSalaryMap(employer));
      }
      averageSalaryEmployerByProffFieldDao.saveSalaryMaps(employers, salaryMaps);

      page += 1;
    }
  }

  private Map<ProfessionalField, Float> getAverageSalaryMap(Employer employer) {
    Map<Integer, Salary> salaryMap = new HashMap();

    Integer maxReviewSize = reviewDao.getRowCountFilteredByEmployer(employer.getId(), null).intValue();

    int page = 0;
    while (PER_PAGE * page < maxReviewSize) {
      calculateSalaryMap(reviewDao.getPaginatedFilteredByEmployerWithSpecializations(employer.getId(), page, PER_PAGE, null), salaryMap);
      page += 1;
    }
    return salaryMap.entrySet().stream()
        .collect(Collectors.toMap(
            s -> professionalFieldDao.getById(s.getKey()),
            s -> s.getValue().salarySum / s.getValue().counter
        ));
  }

  private static void calculateSalaryMap(List<Review> reviews, Map<Integer, Salary> salary) {
    for (Review review : reviews) {
      if (!review.getSpecializations().isEmpty() && review.getSalary() != null) {

        // it is supposed - all specialization are from one prof. field
        ProfessionalField professionalField = review.getSpecializations().get(0).getProfessionalField();
        if (!salary.containsKey(professionalField.getId())) {
          salary.put(professionalField.getId(), new Salary(review.getSalary().floatValue(), 1));
        } else {
          salary.get(professionalField.getId()).salarySum += review.getSalary();
          salary.get(professionalField.getId()).counter += 1;
        }
      }
    }
  }
}
