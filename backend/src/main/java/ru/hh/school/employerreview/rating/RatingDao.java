package ru.hh.school.employerreview.rating;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.rating.deviation.RatingDeviation;
import ru.hh.school.employerreview.rating.stars.StarsInRating;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class RatingDao {

  private final SessionFactory sessionFactory;
  private static final int MAX_ESTIMATE = 5;
  private static final float MIN_ESTIMATE = 0.5f;

  public RatingDao(SessionFactory sessionFactory) {
    this.sessionFactory = requireNonNull(sessionFactory);
  }

  @Transactional(readOnly = true)
  public Map getStarsInRatingMap(Integer employerId) {

    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<StarsInRating> criteriaQuery = builder.createQuery(StarsInRating.class);
    Root<StarsInRating> root = criteriaQuery.from(StarsInRating.class);
    criteriaQuery.select(root);
    criteriaQuery.where(builder.equal(root.get("starsInRatingId").get("employerId"), employerId));
    Query<StarsInRating> query = getSession().createQuery(criteriaQuery);

    Map<Float, Integer> estimates = new HashMap<>();
    for (StarsInRating starsInRating : query.list()) {
      estimates.put(starsInRating.getStarValue(), starsInRating.getStarCounter());
    }
    return estimates;
  }

  @Transactional(readOnly = true)
  public StarsInRating getStarsInRating(Integer employerId, Float starValue) {

    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<StarsInRating> criteriaQuery = builder.createQuery(StarsInRating.class);
    Root<StarsInRating> root = criteriaQuery.from(StarsInRating.class);
    criteriaQuery.select(root);
    criteriaQuery.where(builder.equal(root.get("starsInRatingId").get("employerId"), employerId),
        builder.equal(root.get("starsInRatingId").get("starValue"), starValue));
    Query<StarsInRating> query = getSession().createQuery(criteriaQuery);

    try {
      return query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Transactional
  public void addNewEstimate(Employer employer, float newEstimate) {
    if (newEstimate <= MAX_ESTIMATE && newEstimate >= MIN_ESTIMATE) {
      Rating rating = employer.getRating();
      if (rating == null) {
        rating = new Rating();
      }
      rating.setRating((rating.getRating() * rating.getPeopleRated() + newEstimate) / (rating.getPeopleRated() + 1));
      rating.setPeopleRated(rating.getPeopleRated() + 1);
      getSession().saveOrUpdate(rating);
      employer.setRating(rating);
      getSession().update(employer);

      addStar(employer.getId(), newEstimate);
    }
  }

  @Transactional
  public void addStar(Integer employerId, float newEstimate) {
    StarsInRating starsInRating = getStarsInRating(employerId, newEstimate);
    if (starsInRating == null) {
      starsInRating = new StarsInRating();
      starsInRating.setEmployerId(employerId);
      starsInRating.setStarValue(newEstimate);
    }
    starsInRating.setStarCounter(starsInRating.getStarCounter() + 1);
    getSession().saveOrUpdate(starsInRating);
  }

  @Transactional(readOnly = true)
  public List<Map> getStarsByEmployers(List<Employer> employers) {
    List<Map> result = new ArrayList<>();
    for (Employer employer : employers) {
      result.add(getStarsInRatingMap(employer.getId()));
    }
    return result;
  }

  @Transactional
  public void deleteAllRatingDeviations() {
    getSession().createQuery("delete from RatingDeviation").executeUpdate();
  }

  @Transactional(readOnly = true)
  public List<Employer> getTopBalanced(Integer size, Boolean balancedFirst) {
    Query<Employer> query = getSession().createQuery(
        "SELECT rd.employer " +
            "FROM RatingDeviation rd  " +
            "ORDER BY rd.deviation " +
            (balancedFirst ? "ASC" : "DESC")
    ).setMaxResults(size);
    return query.getResultList();
  }

  @Transactional
  public void saveRatingDeviation(RatingDeviation ratingDeviation) {
    getSession().save(ratingDeviation);
  }

  @Transactional
  public void deleteRating(Rating rating) {
    getSession().delete(rating);
  }

  @Transactional
  public void deleteStarsInRating(StarsInRating starsInRating) {
    getSession().delete(starsInRating);
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
