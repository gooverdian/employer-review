package ru.hh.school.employerreview.area;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class AreaDao {

  private final SessionFactory sessionFactory;

  public AreaDao(SessionFactory sessionFactory) {
    this.sessionFactory = requireNonNull(sessionFactory);
  }

  public void save(Area area) {
    if (area.id() != null) {
        throw new IllegalArgumentException("can not save " + area + " with assigned id");
    }
    session().save(area); // see also saveOrUpdate and persist
  }

  public Optional<Area> get(int areaId) {
    Area area = (Area) session().get(Area.class, areaId);
    return Optional.ofNullable(area);
  }

  public Set<Area> getAll() {
    Criteria criteria = session().createCriteria(Area.class); // Criteria query

    List<Area> areas = criteria.list();
    return new HashSet<>(areas);
  }

  public void update(Area area) {
    session().update(area);
  }

  public void delete(int areaId) {
    session().createQuery("DELETE area WHERE id = :id") // HQL
            .setInteger("id", areaId)
            .executeUpdate();
  }

  private Session session() {
        return sessionFactory.getCurrentSession();
    }
}


