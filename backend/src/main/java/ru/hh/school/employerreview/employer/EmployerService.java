package ru.hh.school.employerreview.employer;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class EmployerService {

  private final SessionFactory sessionFactory;
  private final EmployerDAO employerDAO;

  public EmployerService(SessionFactory sessionFactory, EmployerDAO employerDAO) {
    this.sessionFactory = requireNonNull(sessionFactory);
    this.employerDAO = requireNonNull(employerDAO);
  }

  public void save(Employer employer) {
    inTransaction(() -> employerDAO.save(employer));
  }

  public Optional<Employer> get(int employerId) {
    return inTransaction(() -> employerDAO.get(employerId));
  }

  public Set<Employer> getAll() {
    return inTransaction(employerDAO::getAll);
  }

  public void update(Employer employer) {
    inTransaction(() -> employerDAO.update(employer));
  }

  public void changeFirstName(int employerId, String name) {
    inTransaction(() -> {
      Optional<Employer> optionalEmployer = employerDAO.get(employerId);
      if (!optionalEmployer.isPresent()) {
        throw new IllegalArgumentException("there is no employer with id " + employerId);
      }
      optionalEmployer.get().setName(name);
    });
  }

  public void delete(int employerId) {
    inTransaction(() -> employerDAO.delete(employerId));
  }

  private <T> T inTransaction(Supplier<T> supplier) {
    Optional<Transaction> transaction = beginTransaction();
    try {
      T result = supplier.get();
      transaction.ifPresent(Transaction::commit);
      return result;
    } catch (RuntimeException e) {
      transaction.ifPresent(Transaction::rollback);
      throw e;
    }
  }

  private void inTransaction(Runnable runnable) {
    inTransaction(() -> {
      runnable.run();
      return null;
    });
  }

  private Optional<Transaction> beginTransaction() {
    Transaction transaction = sessionFactory.getCurrentSession().getTransaction();
    if (!transaction.isActive()) {
      transaction.begin();
      return Optional.of(transaction);
    }
    return Optional.empty();
  }
}

