package ru.hh.school.employerreview.rating;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

public class RatingDao {

  private final SessionFactory sessionFactory;
  private static final int MAX_ESTIMATE = 5;
  private static final int MIN_ESTIMATE = 0;
  private static final float ESTIMATE_RECOGNISE_LIMIT = 0.25f;

  public RatingDao(SessionFactory sessionFactory) {
    this.sessionFactory = requireNonNull(sessionFactory);
  }

  @Transactional(readOnly = true)
  public Rating getRating(Integer employerId) {
    return getSession().get(Rating.class, employerId);
  }

  @Transactional
  public void addNewEstimate(Integer employerId, float newEstimate) {
    if (newEstimate <= MAX_ESTIMATE && newEstimate >= MIN_ESTIMATE) {
      Rating rating = getRating(employerId);
      if (rating == null) {
        rating = new Rating(employerId);
      }
      int peopleRated = rating.getPeopleRated();
      float averageScore = rating.getRating();
      rating.setRating((averageScore * peopleRated + newEstimate) / (peopleRated + 1));
      rating.setPeopleRated(peopleRated + 1);

      if (Math.abs(newEstimate - 0.5) < ESTIMATE_RECOGNISE_LIMIT) {
        rating.setStar05(rating.getStar05() + 1);
      } else if (Math.abs(newEstimate - 1) < ESTIMATE_RECOGNISE_LIMIT) {
        rating.setStar1(rating.getStar1() + 1);
      } else if (Math.abs(newEstimate - 1.5) < ESTIMATE_RECOGNISE_LIMIT) {
        rating.setStar15(rating.getStar15() + 1);
      } else if (Math.abs(newEstimate - 2) < ESTIMATE_RECOGNISE_LIMIT) {
        rating.setStar2(rating.getStar2() + 1);
      } else if (Math.abs(newEstimate - 2.5) < ESTIMATE_RECOGNISE_LIMIT) {
        rating.setStar25(rating.getStar25() + 1);
      } else if (Math.abs(newEstimate - 3) < ESTIMATE_RECOGNISE_LIMIT) {
        rating.setStar3(rating.getStar3() + 1);
      } else if (Math.abs(newEstimate - 3.5) < ESTIMATE_RECOGNISE_LIMIT) {
        rating.setStar35(rating.getStar35() + 1);
      } else if (Math.abs(newEstimate - 4) < ESTIMATE_RECOGNISE_LIMIT) {
        rating.setStar4(rating.getStar4() + 1);
      } else if (Math.abs(newEstimate - 4.5) < ESTIMATE_RECOGNISE_LIMIT) {
        rating.setStar45(rating.getStar45() + 1);
      } else if (Math.abs(newEstimate - 5) < ESTIMATE_RECOGNISE_LIMIT) {
        rating.setStar5(rating.getStar5() + 1);
      }
      getSession().saveOrUpdate(rating);
    }
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
