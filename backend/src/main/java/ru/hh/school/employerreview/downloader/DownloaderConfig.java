package ru.hh.school.employerreview.downloader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.core.CoreProdConfig;
import ru.hh.nab.core.util.FileSettings;
import ru.hh.nab.core.util.PropertiesUtils;
import ru.hh.nab.hibernate.DataSourceFactory;
import ru.hh.nab.hibernate.HibernateCommonConfig;
import ru.hh.nab.hibernate.HibernateProdConfig;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.nab.hibernate.NabSessionFactoryBean;
import ru.hh.nab.hibernate.datasource.DataSourceType;
import ru.hh.school.employerreview.ExampleResource;
import ru.hh.school.employerreview.employer.Employer;

import javax.sql.DataSource;
import java.util.Properties;

import static ru.hh.nab.core.util.PropertiesUtils.fromFilesInSettingsDir;

@Configuration
@Import({
    CoreProdConfig.class,
    HibernateProdConfig.class
})

public class DownloaderConfig {
  @Bean
  String serviceName() {
    return "employer downloader";
  }

  @Bean
  ExampleResource exampleResource() {
    return new ExampleResource();
  }

}
