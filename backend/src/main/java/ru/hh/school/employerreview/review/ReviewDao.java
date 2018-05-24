package ru.hh.school.employerreview.review;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.List;

public class ReviewDao {

  private final SessionFactory sessionFactory;

  public ReviewDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional
  public void save(Review review) {
    getSession().save(review);
  }

  @Transactional(readOnly = true)
  public Long getRowCountFilteredByEmployer(Integer employerId, ReviewType reviewType) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery criteria = builder.createQuery();
    Root<Review> reviewRoot = criteria.from(Review.class);
    criteria.select(builder.count(reviewRoot));
    if (reviewType == null) {
      criteria.where(builder.equal(reviewRoot.get("employer").get("id"), employerId));
    } else {
      criteria.where(builder.and(
          builder.equal(reviewRoot.get("employer").get("id"), employerId),
          builder.equal(reviewRoot.get("reviewType"), reviewType)
      ));
    }
    Query<Long> query = getSession().createQuery(criteria);
    return query.getSingleResult();
  }

  @Transactional(readOnly = true)
  public List<Review> getPaginatedFilteredByEmployer(Integer employerId, Integer page, Integer perPage, ReviewType reviewType) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<Review> criteria = builder.createQuery(Review.class);
    Root<Review> reviewRoot = criteria.from(Review.class);
    criteria.select(reviewRoot);
    if (reviewType == null) {
      criteria.where(builder.equal(reviewRoot.get("employer").get("id"), employerId));
    } else {
      criteria.where(builder.and(
          builder.equal(reviewRoot.get("employer").get("id"), employerId),
          builder.equal(reviewRoot.get("reviewType"), reviewType)
      ));
    }    Query<Review> query = getSession().createQuery(criteria);

    query.setFirstResult(page);
    query.setMaxResults(perPage);
    return query.getResultList();
  }

  @Transactional(readOnly = true)
  public List<Review> getPaginatedFilteredByEmployerWithSpecializations(Integer employerId, Integer page, Integer perPage, ReviewType reviewType) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<Review> criteria = builder.createQuery(Review.class);
    Root<Review> reviewRoot = criteria.from(Review.class);
    criteria.select(reviewRoot);
    reviewRoot.fetch("specializations");
    if (reviewType == null) {
      criteria.where(builder.equal(reviewRoot.get("employer").get("id"), employerId));
    } else {
      criteria.where(builder.and(
          builder.equal(reviewRoot.get("employer").get("id"), employerId),
          builder.equal(reviewRoot.get("reviewType"), reviewType)
      ));
    }
    Query<Review> query = getSession().createQuery(criteria);

    query.setFirstResult(page);
    query.setMaxResults(perPage);
    return query.getResultList();
  }

  @Transactional(readOnly = true)
  public Review getById(int id) {
    return getSession().get(Review.class, id);
  }

  @Transactional(readOnly = true)
  public Review getByIdWithSpecializations(int id) {
    return (Review) getSession().createQuery(
        "SELECT r FROM Review r " +
            "JOIN FETCH r.specializations " +
            "WHERE r.id = :id"
    ).setParameter("id", id).getSingleResult();
  }

  @Transactional
  public void deleteReview(Review review) {
    getSession().delete(review);
  }

  @Transactional(readOnly = true)
  public int countRows() {
    return ((Number) getSession().createCriteria(Review.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();
  }

  @Transactional
  public void deleteAllReviews() {
    getSession().createQuery("delete from Review").executeUpdate();
  }

  @Transactional(readOnly = true)
  public Integer countRecentReviews(int employerId, int interval) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -interval);

    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery criteria = builder.createQuery();
    Root<Review> reviewRoot = criteria.from(Review.class);
    criteria.select(builder.count(reviewRoot));
    criteria.where(builder.equal(reviewRoot.get("employer").get("id"), employerId),
        builder.greaterThan(reviewRoot.get("createdOn"), calendar.getTime()));
    Query<Long> query = getSession().createQuery(criteria);
    return query.getSingleResult().intValue();
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
