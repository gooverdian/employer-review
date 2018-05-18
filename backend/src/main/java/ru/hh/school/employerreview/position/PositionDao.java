package ru.hh.school.employerreview.position;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PositionDao {
  private final SessionFactory sessionFactory;

  public PositionDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional
  public void save(Position position) {
    getSession().save(position);
  }

  @Transactional(readOnly = true)
  public List<Position> findPositions(String searchTerm) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery criteria = builder.createQuery();
    Root<Position> positionRoot = criteria.from(Position.class);
    criteria.select(positionRoot);
    criteria.where(builder.like(builder.upper(positionRoot.get("name")), "%" + searchTerm.toUpperCase() + "%"));
    return getSession().createQuery(criteria).getResultList();
  }

  @Transactional(readOnly = true)
  public List<Position> getAll() {
    CriteriaQuery criteria = getSession().getCriteriaBuilder().createQuery();
    Root<Position> positionRoot = criteria.from(Position.class);
    criteria.select(positionRoot);
    return getSession().createQuery(criteria).getResultList();
  }

  @Transactional(readOnly = true)
  public Position getByName(String searchTerm) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<Position> criteria = builder.createQuery(Position.class);
    Root<Position> positionRoot = criteria.from(Position.class);
    criteria.select(positionRoot);
    criteria.where(builder.equal(builder.upper(positionRoot.get("name")), searchTerm.toUpperCase()));
    return getSession().createQuery(criteria).uniqueResultOptional().orElse(null);
  }

  @Transactional
  public void delete(Position position) {
    getSession().delete(position);
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
