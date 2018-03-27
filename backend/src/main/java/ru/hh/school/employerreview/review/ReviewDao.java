package ru.hh.school.employerreview.review;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
  public Long getRowCountFilteredByEmployer(Integer employerId) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery criteria = builder.createQuery();
    Root<Review> reviewRoot = criteria.from(Review.class);
    criteria.select(builder.count(reviewRoot));
    criteria.where(builder.equal(reviewRoot.get("employer").get("id"), employerId));

    Query<Long> query = getSession().createQuery(criteria);
    return query.getSingleResult();
  }

  @Transactional(readOnly = true)
  public List<Review> getPaginatedFilteredByEmployer(Integer employerId, Integer page, Integer perPage) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<Review> criteria = builder.createQuery(Review.class);
    Root<Review> reviewRoot = criteria.from(Review.class);
    criteria.select(reviewRoot);
    criteria.where(builder.equal(reviewRoot.get("employer").get("id"), employerId));
    Query<Review> query = getSession().createQuery(criteria);

    query.setFirstResult(page);
    query.setMaxResults(perPage);
    return query.getResultList();
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
