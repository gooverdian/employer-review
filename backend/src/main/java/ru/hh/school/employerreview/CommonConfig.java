package ru.hh.school.employerreview;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.hibernate.HibernateCommonConfig;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.employer.EmployerSearchResource;
import ru.hh.school.employerreview.review.Review;
import ru.hh.school.employerreview.review.ReviewDao;
import ru.hh.school.employerreview.review.ReviewResource;

@Configuration
@Import({HibernateCommonConfig.class})
public class CommonConfig {

  @Bean
  EmployerSearchResource employerSearchResource(EmployerDao employerDao) {
    return new EmployerSearchResource(employerDao);
  }

  @Bean
  EmployerDao employerDao(SessionFactory sessionFactory) {
    return new EmployerDao(sessionFactory);
  }

  @Bean
  MappingConfig mappingConfig() {
    return new MappingConfig(Employer.class, Review.class);
  }

  @Bean
  ReviewResource reviewResource(ReviewDao reviewDao, EmployerDao employerDao) {
    return new ReviewResource(reviewDao, employerDao);
  }

  @Bean
  ReviewDao reviewDao(SessionFactory sessionFactory) {
    return new ReviewDao(sessionFactory);
  }
}
