package ru.hh.school.employerreview.statistic.salary;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.specializations.ProfessionalField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployerSalaryStatisticsDao {
  private final SessionFactory sessionFactory;

  public EmployerSalaryStatisticsDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  @Transactional
  public void deleteAllAverageSalariesByProffField() {
    getSession().createQuery("delete from EmployerSalaryStatistics").executeUpdate();
  }

  @Transactional
  public void saveSalaryMapsByProffField(List<Employer> employers, List<Map<ProfessionalField, Float>> salaryMaps) {
    for (int i = 0; i < employers.size(); i++) {
      for (Map.Entry<ProfessionalField, Float> entry : salaryMaps.get(i).entrySet()) {
        getSession().save(new EmployerSalaryStatistics(employers.get(i).getId(), entry.getKey(), entry.getValue()));
      }
    }
  }

  @Transactional(readOnly = true)
  public Map<String, Float> getAverageSalaryMapByProffField(Integer employerId) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<EmployerSalaryStatistics> criteriaQuery = builder.createQuery(EmployerSalaryStatistics.class);
    Root<EmployerSalaryStatistics> root = criteriaQuery.from(EmployerSalaryStatistics.class);
    criteriaQuery.select(root);
    criteriaQuery.where(builder.equal(root.get("compositeId").get("employerId"), employerId));
    Query<EmployerSalaryStatistics> query = getSession().createQuery(criteriaQuery);

    Map<String, Float> salaryMap = new HashMap<>();
    for (EmployerSalaryStatistics employerSalaryStatistics : query.list()) {
      salaryMap.put(employerSalaryStatistics.getProffField().getName(), employerSalaryStatistics.getSalary());
    }
    return salaryMap;
  }
}
