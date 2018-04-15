package ru.hh.school.employerreview.downloader;

import com.mchange.v2.c3p0.DriverManagerDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.hh.nab.common.util.FileSettings;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.specializations.ProfessionalField;
import ru.hh.school.employerreview.specializations.ProfessionalFieldDao;
import ru.hh.school.employerreview.specializations.Specialization;
import ru.hh.school.employerreview.specializations.SpecializationDao;

import javax.sql.DataSource;
import java.util.Properties;

import static ru.hh.nab.common.util.PropertiesUtils.fromFilesInSettingsDir;

@Configuration
@EnableTransactionManagement
class DownloaderConfig {

  @Bean
  FileSettings fileSettings() throws Exception {
    Properties properties = fromFilesInSettingsDir("service.properties", "service.properties.dev");
    return new FileSettings(properties);
  }

  @Bean
  DataSource dataSource(FileSettings fileSettings) {
    String dataSourceName = "master";
    FileSettings dataSourceSettings = fileSettings.getSubSettings(dataSourceName);
    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(false);
    driverManagerDataSource.setJdbcUrl(dataSourceSettings.getString("jdbcUrl"));
    driverManagerDataSource.setUser(dataSourceSettings.getString("user"));
    driverManagerDataSource.setPassword(dataSourceSettings.getString("password"));
    driverManagerDataSource.setIdentityToken(dataSourceName);
    return driverManagerDataSource;
  }

  @Bean
  MappingConfig mappingConfig() {
    return new MappingConfig(Employer.class, Area.class, ProfessionalField.class, Specialization.class);
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
