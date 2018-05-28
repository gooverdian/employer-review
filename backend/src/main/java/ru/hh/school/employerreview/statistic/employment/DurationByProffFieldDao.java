package ru.hh.school.employerreview.statistic.employment;

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

public class DurationByProffFieldDao {
  private final SessionFactory sessionFactory;

  public DurationByProffFieldDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  @Transactional
  public void deleteAllAverageEmploymentDuration() {
    getSession().createQuery("delete from DurationByProffField").executeUpdate();
  }

  @Transactional
  public void saveAverageDurationMaps(List<Employer> employers, List<Map<ProfessionalField, Float>> durationMaps) {
    for (int i = 0; i < employers.size(); i++) {
      for (Map.Entry<ProfessionalField, Float> entry : durationMaps.get(i).entrySet()) {
        getSession().save(new DurationByProffField(employers.get(i).getId(), entry.getKey(), entry.getValue()));
      }
    }
  }

  @Transactional(readOnly = true)
  public Map<String, Float> getAverageEmploymentDurationMap(Integer employerId) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<DurationByProffField> criteriaQuery = builder
        .createQuery(DurationByProffField.class);
    Root<DurationByProffField> root = criteriaQuery.from(DurationByProffField.class);
    criteriaQuery.select(root);
    criteriaQuery.where(builder.equal(root.get("compositeId").get("employerId"), employerId));
    Query<DurationByProffField> query = getSession().createQuery(criteriaQuery);

    Map<String, Float> durationMap = new HashMap<>();
    for (DurationByProffField averageEmploymentDuration : query.list()) {
      durationMap.put(averageEmploymentDuration.getProffField().getName(), averageEmploymentDuration.getDuration());
    }
    return durationMap;
  }
}
