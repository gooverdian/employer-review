package ru.hh.school.employerreview.specializations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class SpecializationDao {

  private final SessionFactory sessionFactory;

  public SpecializationDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional
  public void save(Specialization specialization) {
    getSession().save(specialization);
  }

  @Transactional(readOnly = true)
  public Specialization getById(Integer id) {
    return getSession().get(Specialization.class, id);
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
