package ru.hh.school.employerreview;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.common.util.FileSettings;
import ru.hh.nab.core.CoreProdConfig;
import ru.hh.nab.datasource.DataSourceFactory;
import ru.hh.nab.datasource.DataSourceType;
import ru.hh.nab.hibernate.HibernateProdConfig;

import javax.sql.DataSource;

@Configuration
@Import({
    CoreProdConfig.class,
    HibernateProdConfig.class,
    CommonConfig.class
})
public class ProdConfig {
  @Bean
  DataSource dataSource(DataSourceFactory dataSourceFactory, FileSettings fileSettings) {
    return dataSourceFactory.create(DataSourceType.MASTER, fileSettings);
  }
}
