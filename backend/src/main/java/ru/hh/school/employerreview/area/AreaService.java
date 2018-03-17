package ru.hh.school.employerreview.area;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class AreaService {

  private final SessionFactory sessionFactory;
  private final AreaDAO areaDAO;

  public AreaService(SessionFactory sessionFactory, AreaDAO areaDAO) {
    this.sessionFactory = requireNonNull(sessionFactory);
    this.areaDAO = requireNonNull(areaDAO);
  }

  public void save(Area area) {
    inTransaction(() -> areaDAO.save(area));
  }

  public Optional<Area> get(int areaId) {
    return inTransaction(() -> areaDAO.get(areaId));
  }

  public Set<Area> getAll() {
    return inTransaction(areaDAO::getAll);
  }

  public void update(Area area) {
    inTransaction(() -> areaDAO.update(area));
  }

  public void changeFirstName(int areaId, String name) {
    inTransaction(() -> {
      Optional<Area> optionalArea = areaDAO.get(areaId);
      if (!optionalArea.isPresent()) {
        throw new IllegalArgumentException("there is no area with id " + areaId);
      }
      optionalArea.get().setName(name);
    });
  }

  public void delete(int areaId) {
    inTransaction(() -> areaDAO.delete(areaId));
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

