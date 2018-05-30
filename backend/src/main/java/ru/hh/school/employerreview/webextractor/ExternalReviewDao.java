package ru.hh.school.employerreview.webextractor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ExternalReviewDao {
  private final SessionFactory sessionFactory;

  public ExternalReviewDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional
  public void save(ExternalReview externalReview) {
    getSession().save(externalReview);
  }

  @Transactional(readOnly = true)
  public int getMaxId() {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<Integer> criteria = builder.createQuery(Integer.class);
    Root<ExternalReview> reviewRoot = criteria.from(ExternalReview.class);
    criteria.select(builder.max(reviewRoot.get("id").as(Integer.class)));
    Query<Integer> query = getSession().createQuery(criteria);
    return query.uniqueResultOptional().orElse(0);
  }

  @Transactional(readOnly = true)
  public List<ExternalReview> getAll() {
    CriteriaQuery criteria = getSession().getCriteriaBuilder().createQuery();
    Root<ExternalReview> externalReviewRoot = criteria.from(ExternalReview.class);
    criteria.select(externalReviewRoot);
    return getSession().createQuery(criteria).getResultList();
  }

  @Transactional(readOnly = true)
  public ExternalReview getById(Integer id) {
    return getSession().get(ExternalReview.class, id);
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
