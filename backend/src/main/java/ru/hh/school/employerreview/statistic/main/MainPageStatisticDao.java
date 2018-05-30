package ru.hh.school.employerreview.statistic.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.employerreview.specializations.ProfessionalField;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Map;
import java.util.stream.Collectors;

public class MainPageStatisticDao {

  private final SessionFactory sessionFactory;

  public MainPageStatisticDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true)
  public MainPageStatistic getReviewCount() {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<MainPageStatistic> criteria = builder.createQuery(MainPageStatistic.class);
    Root<MainPageStatistic> root = criteria.from(MainPageStatistic.class);
    criteria.select(root);
    criteria.where(builder.equal(root.get("key"), MainPageStatisticType.REVIEW_COUNT));
    try {
      return getSession().createQuery(criteria).getSingleResult();
    } catch (NoResultException e) {
      return new MainPageStatistic(MainPageStatisticType.REVIEW_COUNT);
    }
  }

  @Transactional
  public void addReviewCount() {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<MainPageStatistic> criteria = builder.createQuery(MainPageStatistic.class);
    Root<MainPageStatistic> root = criteria.from(MainPageStatistic.class);
    criteria.select(root);
    criteria.where(builder.equal(root.get("key"), MainPageStatisticType.REVIEW_COUNT));
    try {
      MainPageStatistic reviewCount = getSession().createQuery(criteria).getSingleResult();
      reviewCount.setValue(reviewCount.getValue() + 1);
      getSession().update(reviewCount);
    } catch (NoResultException e) {
      MainPageStatistic reviewCount = new MainPageStatistic(MainPageStatisticType.REVIEW_COUNT);
      reviewCount.setValue(1);
      getSession().save(reviewCount);
    }
  }

  @Transactional
  public void addEmployerWithReviewCount() {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<MainPageStatistic> criteria = builder.createQuery(MainPageStatistic.class);
    Root<MainPageStatistic> root = criteria.from(MainPageStatistic.class);
    criteria.select(root);
    try {
      criteria.where(builder.equal(root.get("key"), MainPageStatisticType.EMPLOYER_WITH_REVIEW_COUNT));
      MainPageStatistic mainPageStatistic = getSession().createQuery(criteria).getSingleResult();
      mainPageStatistic.setValue(mainPageStatistic.getValue() + 1);
      getSession().update(mainPageStatistic);
    } catch (NoResultException e1) {
      MainPageStatistic employerWithReviewCount = new MainPageStatistic(MainPageStatisticType.EMPLOYER_WITH_REVIEW_COUNT);
      employerWithReviewCount.setValue(1);
      getSession().save(employerWithReviewCount);
    }
  }
  @Transactional(readOnly = true)
  public MainPageStatistic getEmployerWithReviewCount() {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<MainPageStatistic> criteria = builder.createQuery(MainPageStatistic.class);
    Root<MainPageStatistic> root = criteria.from(MainPageStatistic.class);
    criteria.select(root);
    criteria.where(builder.equal(root.get("key"), MainPageStatisticType.EMPLOYER_WITH_REVIEW_COUNT));
    try {
      return (getSession().createQuery(criteria).getSingleResult());
    } catch (NoResultException e) {
      return new MainPageStatistic(MainPageStatisticType.EMPLOYER_WITH_REVIEW_COUNT);
    }
  }

  @Transactional
  public void deleteEmployerWithReviewCount() {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<MainPageStatistic> criteria = builder.createQuery(MainPageStatistic.class);
    Root<MainPageStatistic> root = criteria.from(MainPageStatistic.class);
    criteria.select(root);
    try {
      criteria.where(builder.equal(root.get("key"), MainPageStatisticType.EMPLOYER_WITH_REVIEW_COUNT));
      getSession().delete(getSession().createQuery(criteria).getSingleResult());
    } catch (NoResultException e) {
      throw new RuntimeException();
    }
  }

  @Transactional
  public void deleteReviewCount() {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<MainPageStatistic> criteria = builder.createQuery(MainPageStatistic.class);
    Root<MainPageStatistic> root = criteria.from(MainPageStatistic.class);
    criteria.select(root);
    try {
      criteria.where(builder.equal(root.get("key"), MainPageStatisticType.REVIEW_COUNT));
      getSession().delete(getSession().createQuery(criteria).getSingleResult());
    } catch (NoResultException e) {
      throw new RuntimeException();
    }
  }

  @Transactional
  public void saveMainPageEmploymentMap(Map<ProfessionalField, Float> employmentMap) {
    for (Map.Entry<ProfessionalField, Float> entry : employmentMap.entrySet()) {
      getSession().save(new MainPageEmployment(entry.getKey(), entry.getValue()));
    }
  }

  @Transactional
  public void saveMainPageSalaryMap(Map<ProfessionalField, Float> salaryMap) {
    for (Map.Entry<ProfessionalField, Float> entry : salaryMap.entrySet()) {
      getSession().save(new MainPageSalary(entry.getKey(), entry.getValue()));
    }
  }

  @Transactional
  public void deleteAllMainPageSalary() {
    getSession().createQuery("delete from MainPageSalary").executeUpdate();
  }

  @Transactional
  public void deleteAllMainPageEmployment() {
    getSession().createQuery("delete from MainPageEmployment").executeUpdate();
  }

  @Transactional(readOnly = true)
  public Map<String, Float> getMainPageEmploymentMap() {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<MainPageEmployment> criteria = builder.createQuery(MainPageEmployment.class);
    Root<MainPageEmployment> root = criteria.from(MainPageEmployment.class);
    criteria.select(root);
    return getSession().createQuery(criteria).getResultList()
        .stream().collect(Collectors.toMap(s -> s.getProfessionalField().getName(), s -> s.getDuration()));
  }

  @Transactional(readOnly = true)
  public Map<String, Float> getMainPageSalary() {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<MainPageSalary> criteria = builder.createQuery(MainPageSalary.class);
    Root<MainPageSalary> root = criteria.from(MainPageSalary.class);
    criteria.select(root);
    return getSession().createQuery(criteria).getResultList()
        .stream().collect(Collectors.toMap(s -> s.getProfessionalField().getName(), s -> s.getSalary()));
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
