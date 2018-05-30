package ru.hh.school.employerreview.specializations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SpecializationDao {

  private final SessionFactory sessionFactory;

  public SpecializationDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true)
  public List<Specialization> findSpecializations(String searchTerm) {
    return getSession().createQuery(
        "SELECT s " +
            "FROM Specialization s LEFT JOIN ProfessionalField pf " +
            "ON s.professionalField.id = pf.id " +
            "WHERE UPPER(s.name) LIKE :searchTerm " +
            "OR UPPER(pf.name) LIKE :searchTerm " +
            "ORDER BY pf.name, s.name"
    ).setParameter("searchTerm", "%" + searchTerm.toUpperCase() + "%").getResultList();
  }

  @Transactional(readOnly = true)
  public List<Specialization> getAll() {
    CriteriaQuery criteria = getSession().getCriteriaBuilder().createQuery();
    Root<Specialization> specializationRoot = criteria.from(Specialization.class);
    criteria.select(specializationRoot);
    return getSession().createQuery(criteria).getResultList();
  }

  @Transactional
  public void save(Specialization specialization) {
    getSession().save(specialization);
  }

  @Transactional
  public void delete(Specialization specialization) {
    getSession().delete(specialization);
  }

  @Transactional(readOnly = true)
  public Specialization getById(Integer id) {
    return getSession().get(Specialization.class, id);
  }

  @Transactional(readOnly = true)
  public int getMaxId() {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<Integer> criteria = builder.createQuery(Integer.class);
    Root<Specialization> reviewRoot = criteria.from(Specialization.class);
    criteria.select(builder.max(reviewRoot.get("id").as(Integer.class)));
    Query<Integer> query = getSession().createQuery(criteria);
    return query.uniqueResultOptional().orElse(0);
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
