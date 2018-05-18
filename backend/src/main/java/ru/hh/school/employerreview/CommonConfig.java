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
import ru.hh.school.employerreview.employer.visit.EmployerVisit;
import ru.hh.school.employerreview.position.Position;
import ru.hh.school.employerreview.position.PositionDao;
import ru.hh.school.employerreview.position.PositionResource;
import ru.hh.school.employerreview.rating.Rating;
import ru.hh.school.employerreview.rating.RatingDao;
import ru.hh.school.employerreview.rating.deviation.RatingDeviation;
import ru.hh.school.employerreview.rating.stars.StarsInRating;
import ru.hh.school.employerreview.review.Review;
import ru.hh.school.employerreview.review.ReviewDao;
import ru.hh.school.employerreview.review.ReviewGenerationService;
import ru.hh.school.employerreview.review.ReviewResource;
import ru.hh.school.employerreview.specializations.ProfessionalField;
import ru.hh.school.employerreview.specializations.ProfessionalFieldDao;
import ru.hh.school.employerreview.specializations.Specialization;
import ru.hh.school.employerreview.specializations.SpecializationDao;
import ru.hh.school.employerreview.specializations.SpecializationsResource;
import ru.hh.school.employerreview.statistic.MainPageStatistic;
import ru.hh.school.employerreview.statistic.MainPageStatisticDao;
import ru.hh.school.employerreview.statistic.StatisticResource;

@Configuration
@Import({HibernateCommonConfig.class, FileSettingsConfig.class})
public class CommonConfig {

  @Bean
  EmployerSearchResource employerSearchResource(EmployerDao employerDao, RatingDao ratingDao, AreaDao areaDao) {
    return new EmployerSearchResource(employerDao, ratingDao, areaDao);
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
  MainPageStatisticDao mainPageStatisticDao(SessionFactory sessionFactory) {
    return new MainPageStatisticDao(sessionFactory);
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
    return new MappingConfig(Employer.class, Review.class, Area.class, Rating.class, MainPageStatistic.class,
        StarsInRating.class, ProfessionalField.class, Specialization.class, EmployerVisit.class, Position.class, RatingDeviation.class);
  }

  @Bean
  ReviewResource reviewResource(ReviewDao reviewDao, EmployerDao employerDao, RatingDao ratingDao, MainPageStatisticDao mainPageStatisticDao) {
    return new ReviewResource(reviewDao, employerDao, ratingDao, mainPageStatisticDao);
  }

  @Bean
  StatisticResource statisticResource(MainPageStatisticDao mainPageStatisticDao) {
    return new StatisticResource(mainPageStatisticDao);
  }

  @Bean
  ReviewGenerationService reviewGenerationService(ReviewDao reviewDao, EmployerDao employerDao,
                                                  RatingDao ratingDao, MainPageStatisticDao mainPageStatisticDao) {
    return new ReviewGenerationService(reviewDao, employerDao, ratingDao, mainPageStatisticDao);
  }

  @Bean
  ReviewDao reviewDao(SessionFactory sessionFactory) {
    return new ReviewDao(sessionFactory);
  }

  @Bean
  SpecializationDao specializationDao(SessionFactory sessionFactory) {
    return new SpecializationDao(sessionFactory);
  }

  @Bean
  ProfessionalFieldDao proffFieldDao(SessionFactory sessionFactory) {
    return new ProfessionalFieldDao(sessionFactory);
  }

  @Bean
  SpecializationsResource specializationsResource(SpecializationDao specializationDao) {
    return new SpecializationsResource(specializationDao);
  }

  @Bean
  PositionDao positionDao(SessionFactory sessionFactory) {
    return new PositionDao(sessionFactory);
  }

  @Bean
  PositionResource positionResource(PositionDao positionDao) {
    return new PositionResource(positionDao);
  }
}
