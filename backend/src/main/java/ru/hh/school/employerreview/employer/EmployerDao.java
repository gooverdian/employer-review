package ru.hh.school.employerreview.employer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class EmployerDao {

  private final SessionFactory sessionFactory;

  public EmployerDao(SessionFactory sessionFactory) {
    this.sessionFactory = requireNonNull(sessionFactory);
  }

  @Transactional
  public Employer getByHhId(int hhId) {
    return getSession()
        .createQuery("FROM Employer E WHERE E.hhId = :hhid", Employer.class)
        .setParameter("hhid", hhId)
        .uniqueResult();
  }

  @Transactional(readOnly = true)
  public Employer getById(int id) {
    return getSession().get(Employer.class, id);
  }

  @Transactional(readOnly = true)
  public Employer getEmployer(int employerId) {
    return getSession().get(Employer.class, employerId);
  }

  @Transactional
  public void save(List<Employer> employers) {
    for (Employer employer : employers) {
      save(employer);
    }
  }

  @Transactional
  public void save(Employer employer) {
    Employer employerFromDB = getByHhId(employer.getHhId());
    if (employerFromDB == null) {
      getSession().save(employer);
    } else if (employer.getArea().getId() > employerFromDB.getArea().getId()) {
      employerFromDB.setArea(employer.getArea());
      getSession().update(employerFromDB);
    }
  }

  @Transactional(readOnly = true)
  public int getRowCountFilteredByEmployer(String text) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery criteria = builder.createQuery();
    Root<Employer> employerRoot = criteria.from(Employer.class);
    criteria.select(builder.count(employerRoot));
    criteria.where(builder.like(employerRoot.get("name"), "%" + text + "%"));
    Query<Long> query = getSession().createQuery(criteria);
    return query.getSingleResult().intValue();
  }

  @Transactional(readOnly = true)
  public List<Employer> find(String text, int page, int perPage) {
    if (perPage <= 0 || page < 0) {
      return Collections.emptyList();
    }
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<Employer> criteria = builder.createQuery(Employer.class);
    Root<Employer> employerRoot = criteria.from(Employer.class);
    criteria.select(employerRoot);
    criteria.where(builder.like(employerRoot.get("name"), "%" + text + "%"));
    Query<Employer> query = getSession().createQuery(criteria);
    query.setFirstResult(page * perPage);
    query.setMaxResults(perPage);
    return query.getResultList();
  }

  @Transactional(readOnly = true)
  public int countRows() {
    return ((Long) getSession().createQuery("select count(*) from Employer").uniqueResult()).intValue();
  }

  @Transactional
  public int truncate() {
    Query query = getSession().createQuery(String.format("delete from Employer"));
    return query.executeUpdate();
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
