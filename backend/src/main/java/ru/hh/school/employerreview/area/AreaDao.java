package ru.hh.school.employerreview.area;

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

public class AreaDao {

  private final SessionFactory sessionFactory;

  public AreaDao(SessionFactory sessionFactory) {
    this.sessionFactory = requireNonNull(sessionFactory);
  }

  @Transactional
  public void save(Area area) {
    if (area.getId() == null) {
      throw new IllegalArgumentException("Area id must not be null!");
    }
    getSession().save(area);
  }

  @Transactional
  public void save(Area[] areas) {
    for (Area area : areas) {
      save(area);
    }
  }

  @Transactional(readOnly = true)
  public int getRowCountFilteredByArea(String text) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery criteria = builder.createQuery();
    Root<Area> arearRoot = criteria.from(Area.class);
    criteria.select(builder.count(arearRoot));
    criteria.where(builder.like(arearRoot.get("name"), "%" + text + "%"));
    Query<Long> query = getSession().createQuery(criteria);
    return query.getSingleResult().intValue();
  }

  @Transactional(readOnly = true)
  public List<Area> find(String text, int page, int perPage) {
    if (perPage <= 0 || page < 0) {
      return Collections.emptyList();
    }
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<Area> criteria = builder.createQuery(Area.class);
    Root<Area> arearRoot = criteria.from(Area.class);
    criteria.select(arearRoot);
    criteria.where(builder.like(arearRoot.get("name"), "%" + text + "%"));
    Query<Area> query = getSession().createQuery(criteria);
    query.setFirstResult(page * perPage);
    query.setMaxResults(perPage);
    return query.getResultList();
  }

  @Transactional(readOnly = true)
  public Area getById(int id) {
    return getSession().get(Area.class, id);
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
