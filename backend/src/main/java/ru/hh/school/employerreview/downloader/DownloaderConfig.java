package ru.hh.school.employerreview.downloader;

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
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.rating.Rating;
import ru.hh.school.employerreview.specializations.ProfessionalField;
import ru.hh.school.employerreview.specializations.ProfessionalFieldDao;
import ru.hh.school.employerreview.specializations.Specialization;
import ru.hh.school.employerreview.specializations.SpecializationDao;

import javax.sql.DataSource;

@Configuration
@Import({FileSettingsConfig.class})
@EnableTransactionManagement
public class DownloaderConfig {

  @Bean
  MappingConfig mappingConfig() {
    return new MappingConfig(Employer.class, Area.class, ProfessionalField.class, Specialization.class, Rating.class);
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
  AreaDao areaDao(SessionFactory sessionFactory) {
    return new AreaDao(sessionFactory);
  }

  @Bean
  SpecializationDao specializationDao(SessionFactory sessionFactory) {
    return new SpecializationDao(sessionFactory);
  }

  @Bean
  ProfessionalFieldDao proffFieldDao(SessionFactory sessionFactory) {
    return new ProfessionalFieldDao(sessionFactory);
  }
}
