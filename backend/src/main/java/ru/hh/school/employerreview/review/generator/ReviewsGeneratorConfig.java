package ru.hh.school.employerreview.review.generator;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.school.employerreview.FileSettingsConfig;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.employer.visit.EmployerVisit;
import ru.hh.school.employerreview.position.Position;
import ru.hh.school.employerreview.rating.Rating;
import ru.hh.school.employerreview.rating.RatingDao;
import ru.hh.school.employerreview.rating.stars.StarsInRating;
import ru.hh.school.employerreview.review.Review;
import ru.hh.school.employerreview.review.ReviewDao;
import ru.hh.school.employerreview.specializations.ProfessionalField;
import ru.hh.school.employerreview.specializations.Specialization;
import ru.hh.school.employerreview.specializations.SpecializationDao;
import ru.hh.school.employerreview.statistic.main.MainPageStatistic;
import ru.hh.school.employerreview.statistic.main.MainPageStatisticDao;
import ru.hh.school.employerreview.webextractor.ExternalReview;
import ru.hh.school.employerreview.webextractor.ExternalReviewDao;

import javax.sql.DataSource;

@Configuration
@Import({FileSettingsConfig.class})
@EnableTransactionManagement
public class ReviewsGeneratorConfig {
  @Bean
  MappingConfig mappingConfig() {
    return new MappingConfig(Area.class, Specialization.class, Position.class, ProfessionalField.class,
        ExternalReview.class, Employer.class, MainPageStatistic.class, Review.class,
        EmployerVisit.class, Rating.class, StarsInRating.class);
  }

  @Bean
  LocalSessionFactoryBean sessionFactory(DataSource dataSource, MappingConfig mappingConfig) {
    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource);
    sessionFactoryBean.setAnnotatedClasses(mappingConfig.getMappings());
    return sessionFactoryBean;
  }

  @Bean
  PlatformTransactionManager transactionManager(SessionFactory sessionFactory, DataSource dataSource) {
    HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager(sessionFactory);
    hibernateTransactionManager.setDataSource(dataSource);
    return hibernateTransactionManager;
  }

  @Bean
  ExternalReviewDao externalReviewDao(SessionFactory sessionFactory) {
    return new ExternalReviewDao(sessionFactory);
  }

  @Bean
  EmployerDao employerDao(SessionFactory sessionFactory) {
    return new EmployerDao(sessionFactory);
  }

  @Bean
  MainPageStatisticDao mainPageStatisticDao(SessionFactory sessionFactory) {
    return new MainPageStatisticDao(sessionFactory);
  }

  @Bean
  ReviewDao reviewDao(SessionFactory sessionFactory) {
    return new ReviewDao(sessionFactory);
  }

  @Bean
  RatingDao ratingDao(SessionFactory sessionFactory) {
    return new RatingDao(sessionFactory);
  }

  @Bean
  SpecializationDao specializationDao(SessionFactory sessionFactory) {
    return new SpecializationDao(sessionFactory);
  }

  @Bean
  ReviewsGenerator reviewsGenerator(EmployerDao employerDao, MainPageStatisticDao mainPageStatisticDao, ReviewDao reviewDao,
                                    RatingDao ratingDao, ExternalReviewDao externalReviewDao, SpecializationDao specializationDao) {
    return new ReviewsGenerator(employerDao, mainPageStatisticDao, reviewDao, ratingDao, externalReviewDao, specializationDao);
  }
}
