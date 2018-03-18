package ru.hh.school.employerreview.area;

import static java.util.Objects.requireNonNull;
import java.util.Optional;
import static java.util.Optional.ofNullable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class AreaDao {

  private final SessionFactory sessionFactory;

  public AreaDao(SessionFactory sessionFactory) {
    this.sessionFactory = requireNonNull(sessionFactory);
  }

  @Transactional
  public void save(Area area) {
    if (area.id() != null) {
      throw new IllegalArgumentException("can not save " + area + " with assigned id");
    }
    getSession().save(area);
  }

  @Transactional
  public void save(Area[] areas) {
    for (Area area: areas) {
      save(area);
    }
  }

  @Transactional
  public Optional<Area> get(int areaId) {
    return ofNullable(getSession().get(Area.class, areaId));
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
