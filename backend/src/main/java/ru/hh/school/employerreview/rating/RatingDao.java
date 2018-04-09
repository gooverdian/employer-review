package ru.hh.school.employerreview.rating;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

public class RatingDao {

  private final SessionFactory sessionFactory;
  private static final int MAX_ESTIMATE = 5;
  private static final int MIN_ESTIMATE = 1;

  public RatingDao(SessionFactory sessionFactory) {
    this.sessionFactory = requireNonNull(sessionFactory);
  }

  @Transactional(readOnly = true)
  public Rating getRating(Integer employerId) {
    return getSession().get(Rating.class, employerId);
  }

  @Transactional
  public void addNewEstimate(Integer employerId, int newEstimate) {
    if (newEstimate <= MAX_ESTIMATE && newEstimate >= MIN_ESTIMATE) {
      Rating rating = getRating(employerId);
      boolean isNewRating = false;
      if (rating == null) {
        rating = new Rating(employerId);
        isNewRating = true;
      }
      int peopleRated = rating.getPeopleRated();
      float averageScore = rating.getRating();
      rating.setRating((averageScore * peopleRated + newEstimate) / (peopleRated + 1));
      rating.setPeopleRated(peopleRated + 1);
      switch (newEstimate) {
        case 1 : {
          rating.setStar1(rating.getStar1() + 1);
          break;
        }
        case 2 : {
          rating.setStar2(rating.getStar2() + 1);
          break;
        }
        case 3 : {
          rating.setStar3(rating.getStar3() + 1);
          break;
        }
        case 4 : {
          rating.setStar4(rating.getStar4() + 1);
          break;
        }
        case 5 : {
          rating.setStar5(rating.getStar5() + 1);
          break;
        }
      }
      if (isNewRating) {
        getSession().save(rating);
      } else {
        getSession().update(rating);
      }
    }
  }

  @Transactional
  public Integer countStar(Integer employerId, int starNumber) {
    if (starNumber <= MAX_ESTIMATE && starNumber >= MIN_ESTIMATE) {
      Rating rating = getRating(employerId);
      switch (starNumber) {
        case 1: {
          return rating.getStar1();
        }
        case 2: {
          return rating.getStar2();
        }
        case 3: {
          return rating.getStar3();
        }
        case 4: {
          return rating.getStar4();
        }
        case 5: {
          return rating.getStar5();
        }
      }
    }
    return null;
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
