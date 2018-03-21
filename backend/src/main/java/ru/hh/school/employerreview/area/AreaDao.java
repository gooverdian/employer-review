package ru.hh.school.employerreview.area;

import static java.util.Objects.requireNonNull;
import java.util.Optional;
import static java.util.Optional.ofNullable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

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
