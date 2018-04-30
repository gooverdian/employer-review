package ru.hh.school.employerreview;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.hh.nab.common.util.FileSettings;

import java.util.Properties;

import static ru.hh.nab.common.util.PropertiesUtils.fromFilesInSettingsDir;
import static ru.hh.nab.common.util.PropertiesUtils.setSystemPropertyIfAbsent;

@Configuration
public class FileSettingsConfig {

  @Primary
  @Bean
  FileSettings fileSettings() throws Exception {
    setSystemPropertyIfAbsent("settingsDir", "src/etc");
    Properties properties = fromFilesInSettingsDir("service.properties", "service.properties.dev");

    String port = System.getenv("PORT");
    if (StringUtils.isNotBlank(port)) {
      properties.setProperty("jetty.port", port);
    }

    String jdbcUrl = System.getenv("JDBC_DATABASE_URL");
    if (StringUtils.isNotBlank(jdbcUrl)) {
      if (jdbcUrl.contains("?")) {
        properties.setProperty("master.jdbcUrl", jdbcUrl + "&stringtype=unspecified");
      } else {
        properties.setProperty("master.jdbcUrl", jdbcUrl + "?stringtype=unspecified");
      }
    }

    String user = System.getenv("JDBC_DATABASE_USERNAME");
    if (StringUtils.isNotBlank(jdbcUrl)) {
      properties.setProperty("master.user", user);
    }
    String password = System.getenv("JDBC_DATABASE_PASSWORD");
    if (StringUtils.isNotBlank(jdbcUrl)) {
      properties.setProperty("master.password", password);
    }

    return new FileSettings(properties);
  }
}
