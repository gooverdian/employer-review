package ru.hh.school.employerreview;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.hh.nab.core.CoreProdConfig;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.nab.hibernate.DataSourceFactory;
import ru.hh.nab.hibernate.HibernateCommonConfig;
import ru.hh.nab.hibernate.HibernateProdConfig;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.nab.hibernate.datasource.DataSourceType;

@Configuration
@Import({
    CoreProdConfig.class,
    HibernateProdConfig.class,
    HibernateCommonConfig.class})
public class ProdConfig {

  @Bean
  DataSource dataSource(DataSourceFactory dataSourceFactory, FileSettings fileSettings) throws Exception {
    return fileSettings.getBoolean("master.embedded")
        ? createEmbeddedDatabase()
        : dataSourceFactory.create(DataSourceType.MASTER, fileSettings);
  }

  @Bean
  MappingConfig mappingConfig() {
    return new MappingConfig();
  }

  @Bean
  ExampleResource exampleResource() {
    return new ExampleResource();
  }

  private EmbeddedDatabase createEmbeddedDatabase() {
    return new EmbeddedDatabaseBuilder()
        .setName("master")
        .setType(EmbeddedDatabaseType.HSQL)
        .build();
  }
}
