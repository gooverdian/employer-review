package ru.hh.school.employerreview.employer;

import java.util.List;
import static java.util.Objects.requireNonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class EmployerDao {

  private final SessionFactory sessionFactory;

  public EmployerDao(SessionFactory sessionFactory) {
    this.sessionFactory = requireNonNull(sessionFactory);
  }

  @Transactional
  public void save(List<Employer> employers) {
    for (Employer employer: employers) {
      getSession().save(employer);
    }
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
