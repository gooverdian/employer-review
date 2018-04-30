package ru.hh.school.employerreview.downloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static ru.hh.nab.common.util.PropertiesUtils.setSystemPropertyIfAbsent;

public abstract class AbstractDownloader {
  protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  protected static ApplicationContext applicationContext;

  protected static void init() {
    setSystemPropertyIfAbsent("settingsDir", "src/etc");
    applicationContext = new AnnotationConfigApplicationContext(DownloaderConfig.class);
  }
}
