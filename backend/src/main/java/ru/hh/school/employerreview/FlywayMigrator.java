package ru.hh.school.employerreview;

import org.flywaydb.core.Flyway;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.hh.school.employerreview.downloader.DownloaderConfig;

import javax.sql.DataSource;

import static ru.hh.nab.common.util.PropertiesUtils.setSystemPropertyIfAbsent;

public class FlywayMigrator {

  public static void main(String... arg) {
    setSystemPropertyIfAbsent("settingsDir", "src/etc");
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DownloaderConfig.class);

    Flyway flyway = new Flyway();
    DataSource dataSource = applicationContext.getBean(DataSource.class);
    flyway.setDataSource(dataSource);
    flyway.migrate();
  }
}
