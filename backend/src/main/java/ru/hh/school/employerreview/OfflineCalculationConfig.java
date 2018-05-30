package ru.hh.school.employerreview;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.position.Position;
import ru.hh.school.employerreview.rating.Rating;
import ru.hh.school.employerreview.rating.RatingDao;
import ru.hh.school.employerreview.rating.deviation.RatingDeviation;
import ru.hh.school.employerreview.rating.stars.StarsInRating;
import ru.hh.school.employerreview.review.Review;
import ru.hh.school.employerreview.review.ReviewDao;
import ru.hh.school.employerreview.specializations.ProfessionalField;
import ru.hh.school.employerreview.specializations.ProfessionalFieldDao;
import ru.hh.school.employerreview.specializations.Specialization;
import ru.hh.school.employerreview.statistic.employment.DurationByProffField;
import ru.hh.school.employerreview.statistic.employment.DurationByProffFieldCalculationWorker;
import ru.hh.school.employerreview.statistic.employment.DurationByProffFieldDao;
import ru.hh.school.employerreview.statistic.main.MainPageEmployment;
import ru.hh.school.employerreview.statistic.main.MainPageReviewCounter;
import ru.hh.school.employerreview.statistic.main.MainPageSalary;
import ru.hh.school.employerreview.statistic.main.MainPageStatisticDao;
import ru.hh.school.employerreview.statistic.salary.EmployerSalaryStatistics;
import ru.hh.school.employerreview.statistic.salary.EmployerSalaryStatisticsCalculationWorker;
import ru.hh.school.employerreview.statistic.salary.EmployerSalaryStatisticsDao;

import javax.sql.DataSource;

@Configuration
@Import({FileSettingsConfig.class})
@EnableTransactionManagement
public class OfflineCalculationConfig {

  @Bean
  MappingConfig mappingConfig() {
    return new MappingConfig(Employer.class, Area.class, Rating.class, RatingDeviation.class, StarsInRating.class,
        EmployerSalaryStatistics.class, Review.class, ProfessionalField.class, Specialization.class, Position.class,
        DurationByProffField.class, MainPageEmployment.class, MainPageSalary.class, MainPageReviewCounter.class);
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
  EmployerDao employerDao(SessionFactory sessionFactory) {
    return new EmployerDao(sessionFactory);
  }

  @Bean
  RatingDao ratingDao(SessionFactory sessionFactory) {
    return new RatingDao(sessionFactory);
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
  ProfessionalFieldDao professionalFieldDao(SessionFactory sessionFactory) {
    return new ProfessionalFieldDao(sessionFactory);
  }

  @Bean
  ReviewDao reviewDao(SessionFactory sessionFactory) {
    return new ReviewDao(sessionFactory);
  }

  @Bean
  MainPageStatisticDao mainPageStatisticDao(SessionFactory sessionFactory) {
    return new MainPageStatisticDao(sessionFactory);
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
}
