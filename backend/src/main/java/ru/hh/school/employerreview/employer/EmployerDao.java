package ru.hh.school.employerreview.employer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class EmployerDao {

  private final SessionFactory sessionFactory;

  public EmployerDao(SessionFactory sessionFactory) {
    this.sessionFactory = requireNonNull(sessionFactory);
  }

  @Transactional
  public Employer getByHhId(int hhId){
    return getSession()
        .createQuery("FROM Employer E WHERE E.hhId = :hhid", Employer.class)
        .setParameter("hhid", hhId)
        .uniqueResult();
  }

  @Transactional
  public void save(List<Employer> employers) {
    for (Employer employer: employers) {
      Employer employerFromDB = getByHhId(employer.getHhId());
      if (employerFromDB == null) {
        getSession().save(employer);

      } else if (employer.getAreaId() > employerFromDB.getAreaId()) {
        employerFromDB.setAreaId(employer.getAreaId());
        getSession().update(employerFromDB);
      }
    }
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
