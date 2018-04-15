package ru.hh.school.employerreview.specializations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class ProfessionalFieldDao {

  private final SessionFactory sessionFactory;

  public ProfessionalFieldDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional
  public void save(ProfessionalField professionalField) {
    getSession().save(professionalField);
  }

  @Transactional(readOnly = true)
  public ProfessionalField getById(Integer id) {
    return getSession().get(ProfessionalField.class, id);
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
