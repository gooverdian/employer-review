package ru.hh.school.employerreview;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.hh.nab.core.CoreProdConfig;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.nab.hibernate.DataSourceFactory;
import ru.hh.nab.hibernate.HibernateProdConfig;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.nab.hibernate.datasource.DataSourceType;
import ru.hh.school.employerreview.employer.Employer;
import ru.hh.school.employerreview.employer.EmployerDao;
import ru.hh.school.employerreview.review.Review;
import ru.hh.school.employerreview.review.ReviewDao;
import ru.hh.school.employerreview.review.ReviewResource;

import javax.sql.DataSource;

@Configuration
@Import({
    CoreProdConfig.class,
    HibernateProdConfig.class
})
public class ProdConfig {

  @Bean
  DataSource dataSource(DataSourceFactory dataSourceFactory, FileSettings fileSettings) throws Exception {
    return fileSettings.getBoolean("master.embedded")
        ? createEmbeddedDatabase()
        : dataSourceFactory.create(DataSourceType.MASTER, fileSettings);
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
  ExampleResource exampleResource() {
    return new ExampleResource();
  }

  @Bean
  ReviewDao reviewDao(SessionFactory sessionFactory) {
    return new ReviewDao(sessionFactory);
  }

  @Bean
  EmployerDao employerDao(SessionFactory sessionFactory) {
    return new EmployerDao(sessionFactory);
  }

  private EmbeddedDatabase createEmbeddedDatabase() {
    return new EmbeddedDatabaseBuilder()
        .setName("master")
        .setType(EmbeddedDatabaseType.HSQL)
        .build();
  }

  @Bean
  CorsFilter corsFilter() {
    return new CorsFilter();
  }
}
