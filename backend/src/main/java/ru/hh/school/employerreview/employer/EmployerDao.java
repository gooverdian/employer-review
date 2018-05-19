package ru.hh.school.employerreview.employer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.employerreview.employer.visit.EmployerVisit;
import ru.hh.school.employerreview.employer.visit.EmployerVisitDto;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Collections;

import static java.util.Objects.requireNonNull;

public class EmployerDao {

  private final SessionFactory sessionFactory;

  public EmployerDao(SessionFactory sessionFactory) {
    this.sessionFactory = requireNonNull(sessionFactory);
  }

  @Transactional
  public Employer getEmployerByHhId(int hhId) {
    return getSession()
        .createQuery("FROM Employer E WHERE E.hhId = :hhid", Employer.class)
        .setParameter("hhid", hhId)
        .uniqueResult();
  }

  @Transactional(readOnly = true)
  public Employer getEmployer(int employerId) {
    return getSession().get(Employer.class, employerId);
  }

  @Transactional
  public void save(List<Employer> employers) {
    for (Employer employer : employers) {
      save(employer);
    }
  }

  @Transactional
  public void save(Employer employer) {
    if (employer.getHhId() != null) { // for employers from hh database
      Employer employerFromDB = getEmployerByHhId(employer.getHhId());
      if (employerFromDB == null) {
        getSession().save(employer);
      } else if (employer.getArea().getId() > employerFromDB.getArea().getId()) {
        // Check if the new employer.area is the child of employer's area from DB to go down to the lowest area level
        employerFromDB.setArea(employer.getArea());
        getSession().update(employerFromDB);
      }
    } else {
      getSession().save(employer);
    }
  }

  @Transactional(readOnly = true)
  public int getRowCountFilteredByEmployer(String text) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery criteria = builder.createQuery();
    Root<Employer> employerRoot = criteria.from(Employer.class);
    criteria.select(builder.count(employerRoot));
    criteria.where(builder.like(builder.upper(employerRoot.get("name")), "%" + text.toUpperCase() + "%"));
    Query<Long> query = getSession().createQuery(criteria);
    return query.getSingleResult().intValue();
  }

  @Transactional(readOnly = true)
  public List<Employer> findEmployers(String text, int page, int perPage, boolean bestFirst) {
    if (perPage <= 0 || page < 0) {
      return Collections.emptyList();
    }
    Query<Employer> query = getSession().createQuery(
        "SELECT e " +
            "FROM Employer e LEFT JOIN Rating r " +
            "ON e.rating.id = r.id " +
            "WHERE UPPER(e.name) LIKE :text " +
            "ORDER BY r.rating " +
            (bestFirst ? "DESC" : "ASC") +
            " NULLS LAST, r.peopleRated DESC NULLS LAST"
    ).setParameter("text", "%" + text.toUpperCase() + "%");
    query.setFirstResult(page * perPage);
    query.setMaxResults(perPage);
    return query.getResultList();
  }

  @Transactional(readOnly = true)
  public int countRows() {
    return ((Number) getSession().createCriteria(Employer.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();
  }

  @Transactional
  public void deleteEmployer(Employer employer) {
    getSession().delete(employer);
  }

  @Transactional(readOnly = true)
  public EmployerVisit findLastEmployerVisitBeforeDate(Employer employer, Date maxDate) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<EmployerVisit> criteriaQuery = builder.createQuery(EmployerVisit.class);
    Root<EmployerVisit> root = criteriaQuery.from(EmployerVisit.class);
    criteriaQuery.select(root);
    criteriaQuery.where(builder.equal(root.get("employerVisitId").get("employerId"), employer.getId()),
        builder.lessThan(root.get("employerVisitId").get("date"), maxDate));
    criteriaQuery.orderBy(builder.desc(root.get("employerVisitId").get("employerId")));

    return getSession().createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
  }

  private EmployerVisit createNewEmployerVisit(Employer employer, Date date) {
    EmployerVisit employerVisit = new EmployerVisit(employer.getId(), date);
    try {
      EmployerVisit previousVisits = findLastEmployerVisitBeforeDate(employer, date);
      employerVisit.setVisitBeforeDateTotalCounter(previousVisits.getVisitBeforeDateTotalCounter() + previousVisits.getVisitCounter());
    } catch (NoResultException e) {
      employerVisit.setVisitBeforeDateTotalCounter(0);
    }
    return employerVisit;
  }

  @Transactional
  public void addEmployerVisitCounter(Employer employer, Date date) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<EmployerVisit> criteriaQuery = builder.createQuery(EmployerVisit.class);
    Root<EmployerVisit> root = criteriaQuery.from(EmployerVisit.class);
    criteriaQuery.select(root);
    criteriaQuery.where(builder.equal(root.get("employerVisitId").get("employerId"), employer.getId()),
        builder.equal(root.get("employerVisitId").get("date"), date));

    EmployerVisit employerVisit;
    try {
      employerVisit = getSession().createQuery(criteriaQuery).getSingleResult();
    } catch (NoResultException e) {
      employerVisit = createNewEmployerVisit(employer, date);
    }
    employerVisit.setVisitCounter(employerVisit.getVisitCounter() + 1);
    getSession().saveOrUpdate(employerVisit);
  }

  @Transactional(readOnly = true)
  public List<EmployerVisitDto> getTopEmployerVisited(Integer size, Integer interval) {

    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -interval);

    Query<Object> query = getSession().createQuery(
        "SELECT ev, SUM(ev.visitCounter) AS counter FROM EmployerVisit AS ev " +
        "WHERE ev.employerVisitId.date > :date " +
        "GROUP BY ev.employerVisitId.employerId, ev.employerVisitId.date " +
        "ORDER BY counter DESC").setMaxResults(size).setParameter("date", calendar.getTime());

    List<EmployerVisitDto> top = new ArrayList<>();
    for (Object result : query.list()) {
      Object[] dividedResult = (Object[]) result;
      EmployerVisit employerVisit = (EmployerVisit) dividedResult[0];

      EmployerVisitDto employerVisitDto = getEmployer(employerVisit.getEmployerId()).toEmployerVisitDto();
      employerVisitDto.setPeopleVisited(((Long) dividedResult[1]).intValue());
      top.add(employerVisitDto);
    }
    return top;
  }

  @Transactional
  public void deleteAllEmployerVisits() {
    getSession().createQuery("delete from EmployerVisit").executeUpdate();
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
