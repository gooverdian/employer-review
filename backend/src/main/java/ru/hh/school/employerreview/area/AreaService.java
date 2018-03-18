package ru.hh.school.employerreview.area;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class AreaService {

  private final SessionFactory sessionFactory;
  private final AreaDao areaDao;

  public AreaService(SessionFactory sessionFactory, AreaDao areaDao) {
    this.sessionFactory = requireNonNull(sessionFactory);
    this.areaDao = requireNonNull(areaDao);
  }

  @Transactional
  public void save(Area area) {   areaDao.save(area);  }

  @Transactional
  public Optional<Area> get(int areaId) {    return areaDao.get(areaId);  }

  @Transactional
  public Set<Area> getAll() {return areaDao.getAll();}

  @Transactional
  public void update(Area area) { areaDao.update(area);}

  @Transactional
  public void changeName(int areaId, String name) {
    Optional<Area> optionalArea = areaDao.get(areaId);
    if (!optionalArea.isPresent()) {
      throw new IllegalArgumentException("there is no area with id " + areaId);
    }
    optionalArea.get().setName(name);
  }

  @Transactional
  public void delete(int areaId) { areaDao.delete(areaId);  }
}

