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
import ru.hh.school.employerreview.review.ReviewResource;
import ru.hh.school.employerreview.review.generator.ReviewGenerationService;
import ru.hh.school.employerreview.review.generator.ReviewsGenerator;
import ru.hh.school.employerreview.specializations.ProfessionalField;
import ru.hh.school.employerreview.specializations.ProfessionalFieldDao;
import ru.hh.school.employerreview.specializations.Specialization;
import ru.hh.school.employerreview.specializations.SpecializationDao;
import ru.hh.school.employerreview.specializations.SpecializationsResource;
import ru.hh.school.employerreview.statistic.employment.DurationByProffField;
import ru.hh.school.employerreview.statistic.employment.DurationByProffFieldCalculationWorker;
import ru.hh.school.employerreview.statistic.employment.DurationByProffFieldDao;
import ru.hh.school.employerreview.statistic.main.MainPageEmployment;
import ru.hh.school.employerreview.statistic.main.MainPageSalary;
import ru.hh.school.employerreview.statistic.main.MainPageStatistic;
import ru.hh.school.employerreview.statistic.main.MainPageStatisticDao;
import ru.hh.school.employerreview.statistic.StatisticResource;
import ru.hh.school.employerreview.statistic.salary.EmployerSalaryStatistics;
import ru.hh.school.employerreview.statistic.salary.EmployerSalaryStatisticsCalculationWorker;
import ru.hh.school.employerreview.statistic.salary.EmployerSalaryStatisticsDao;
import ru.hh.school.employerreview.webextractor.ExternalReview;
import ru.hh.school.employerreview.webextractor.ExternalReviewDao;

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
        StarsInRating.class, ProfessionalField.class, Specialization.class, EmployerVisit.class, Position.class,
        RatingDeviation.class, ExternalReview.class, EmployerSalaryStatistics.class, DurationByProffField.class,
        MainPageEmployment.class, MainPageSalary.class);
  }

  @Bean
  ReviewResource reviewResource(ReviewDao reviewDao,
                                EmployerDao employerDao,
                                RatingDao ratingDao,
                                MainPageStatisticDao mainPageStatisticDao) {
    return new ReviewResource(reviewDao, employerDao, ratingDao, mainPageStatisticDao);
  }

  @Bean
  StatisticResource statisticResource(MainPageStatisticDao mainPageStatisticDao,
                                      EmployerSalaryStatisticsDao employerSalaryStatisticsDao,
                                      DurationByProffFieldDao durationByProffFieldDao) {
    return new StatisticResource(mainPageStatisticDao,
        employerSalaryStatisticsDao,
        durationByProffFieldDao);
  }

  @Bean
  ReviewGenerationService reviewGenerationService(ReviewsGenerator reviewsGenerator) {
    return new ReviewGenerationService(reviewsGenerator);
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

  @Bean
  EmployerSalaryStatisticsDao employerSalaryStatisticsDao(SessionFactory sessionFactory) {
    return new EmployerSalaryStatisticsDao(sessionFactory);
  }

  @Bean
  DurationByProffFieldDao durationByProffFieldDao(SessionFactory sessionFactory) {
    return new DurationByProffFieldDao(sessionFactory);
  }

  @Bean
  EmployerSalaryStatisticsCalculationWorker employerSalaryStatisticsCalculationWorker(
        EmployerSalaryStatisticsDao employerSalaryStatisticsDao,
        ReviewDao reviewDao,
        ProfessionalFieldDao professionalFieldDao,
        EmployerDao employerDao,
        MainPageStatisticDao mainPageStatisticDao) {
    return new EmployerSalaryStatisticsCalculationWorker(employerSalaryStatisticsDao,
        reviewDao,
        professionalFieldDao,
        employerDao,
        mainPageStatisticDao);
  }

  @Bean
  DurationByProffFieldCalculationWorker durationByProffFieldCalculationWorker(
      DurationByProffFieldDao durationByProffFieldDao,
      ReviewDao reviewDao,
      ProfessionalFieldDao professionalFieldDao,
      EmployerDao employerDao,
      MainPageStatisticDao mainPageStatisticDao) {
    return new DurationByProffFieldCalculationWorker(durationByProffFieldDao,
        reviewDao,
        professionalFieldDao,
        employerDao,
        mainPageStatisticDao);
  }

  @Bean
  ReviewsGenerator reviewsGenerator(EmployerDao employerDao, MainPageStatisticDao mainPageStatisticDao, ReviewDao reviewDao,
                                    RatingDao ratingDao, ExternalReviewDao externalReviewDao, SpecializationDao specializationDao) {
    return new ReviewsGenerator(employerDao, mainPageStatisticDao, reviewDao, ratingDao, externalReviewDao, specializationDao);
  }

  @Bean
  ExternalReviewDao externalReviewDao(SessionFactory sessionFactory) {
    return new ExternalReviewDao(sessionFactory);
  }
}
