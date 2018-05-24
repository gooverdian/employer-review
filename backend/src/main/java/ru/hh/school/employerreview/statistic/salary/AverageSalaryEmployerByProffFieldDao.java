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

public class AverageSalaryEmployerByProffFieldDao {
  private final SessionFactory sessionFactory;

  public AverageSalaryEmployerByProffFieldDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  @Transactional
  public void deleteAllAverageSalaryEmployerByProffField() {
    getSession().createQuery("delete from AverageSalaryEmployerByProffField").executeUpdate();
  }

  @Transactional
  public void saveSalaryMaps(List<Employer> employers, List<Map<ProfessionalField, Float>> salaryMaps) {
    for (int i = 0; i < employers.size(); i++) {
      for (Map.Entry<ProfessionalField, Float> entry : salaryMaps.get(i).entrySet()) {
        getSession().save(new AverageSalaryEmployerByProffField(employers.get(i).getId(), entry.getKey(), entry.getValue()));
      }
    }
  }

  @Transactional(readOnly = true)
  public Map<String, Float> getAverageSalaryEmployerByProffField(Integer employerId) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<AverageSalaryEmployerByProffField> criteriaQuery = builder.createQuery(AverageSalaryEmployerByProffField.class);
    Root<AverageSalaryEmployerByProffField> root = criteriaQuery.from(AverageSalaryEmployerByProffField.class);
    criteriaQuery.select(root);
    criteriaQuery.where(builder.equal(root.get("compositeId").get("employerId"), employerId));
    Query<AverageSalaryEmployerByProffField> query = getSession().createQuery(criteriaQuery);

    Map<String, Float> salaryMap = new HashMap<>();
    for (AverageSalaryEmployerByProffField averageSalaryEmployerByProffField : query.list()) {
      salaryMap.put(averageSalaryEmployerByProffField.getProffField().getName(), averageSalaryEmployerByProffField.getSalary());
    }
    return salaryMap;
  }
}
