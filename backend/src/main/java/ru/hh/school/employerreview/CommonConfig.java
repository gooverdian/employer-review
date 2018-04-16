package ru.hh.school.employerreview;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.hibernate.HibernateCommonConfig;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.area.AreaSearchResource;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.employer.EmployerSearchResource;
import ru.hh.school.employerreview.rating.Rating;
import ru.hh.school.employerreview.rating.RatingDao;
import ru.hh.school.employerreview.review.Review;
import ru.hh.school.employerreview.review.ReviewDao;
import ru.hh.school.employerreview.review.ReviewResource;

@Configuration
@Import({HibernateCommonConfig.class})
public class CommonConfig {

  @Bean
  EmployerSearchResource employerSearchResource(EmployerDao employerDao, RatingDao ratingDao) {
    return new EmployerSearchResource(employerDao, ratingDao);
  }

  @Bean
  AreaSearchResource areaSearchResource(AreaDao areaDao) {
    return new AreaSearchResource(areaDao);
  }

  @Bean
  EmployerDao employerDao(SessionFactory sessionFactory) {
    return new EmployerDao(sessionFactory);
  }

  @Bean
  RatingDao ratingDao(SessionFactory sessionFactory) {
    return new RatingDao(sessionFactory);
  }

  @Bean
  AreaDao areaDao(SessionFactory sessionFactory) {
    return new AreaDao(sessionFactory);
  }

  @Bean
  MappingConfig mappingConfig() {
    return new MappingConfig(Employer.class, Review.class, Area.class, Rating.class);
  }

  @Bean
  ReviewResource reviewResource(ReviewDao reviewDao, EmployerDao employerDao, RatingDao ratingDao) {
    return new ReviewResource(reviewDao, employerDao, ratingDao);
  }

  @Bean
  ReviewDao reviewDao(SessionFactory sessionFactory) {
    return new ReviewDao(sessionFactory);
  }
}
