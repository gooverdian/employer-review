package ru.hh.school.employerreview;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.nab.core.CoreCommonConfig;

import javax.sql.DataSource;

@Configuration
@Import({CoreCommonConfig.class, CommonConfig.class})
public class TestConfig {

  @Primary
  @Bean(destroyMethod = "shutdown")
  DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.HSQL)
        .build();
  }

  @Bean
  TestQueryExecutorDao dummyDao(SessionFactory sessionFactory) {
    return new TestQueryExecutorDao(sessionFactory);
  }

  public static class TestQueryExecutorDao {

    private final SessionFactory sessionFactory;

    public TestQueryExecutorDao(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void executeQuery(String query) {
      getSession().createQuery(query).executeUpdate();
    }

    private Session getSession() {
      return sessionFactory.getCurrentSession();
    }
  }
}
