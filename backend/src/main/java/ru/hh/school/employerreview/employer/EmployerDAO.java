package ru.hh.school.employerreview.employer;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class EmployerDAO {

  private final SessionFactory sessionFactory;

  public EmployerDAO(SessionFactory sessionFactory) {
    this.sessionFactory = requireNonNull(sessionFactory);
  }

  public void save(Employer employer) {
    if (employer.id() != null) {
        throw new IllegalArgumentException("can not save " + employer + " with assigned id");
    }
    session().save(employer); // see also saveOrUpdate and persist
  }

  public Optional<Employer> get(int employerId) {
    Employer employer = (Employer) session().get(Employer.class, employerId);
    return Optional.ofNullable(employer);
  }

  public Set<Employer> getAll() {
    Criteria criteria = session().createCriteria(Employer.class); // Criteria query

    List<Employer> employers = criteria.list();
    return new HashSet<>(employers);
  }

  public void update(Employer employer) {
    session().update(employer);
  }

  public void delete(int employerId) {
    session().createQuery("DELETE employer WHERE id = :id") // HQL
            .setInteger("id", employerId)
            .executeUpdate();
  }

  private Session session() {
        return sessionFactory.getCurrentSession();
    }
}


