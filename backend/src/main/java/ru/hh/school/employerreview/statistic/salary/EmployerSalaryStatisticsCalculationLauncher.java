package ru.hh.school.employerreview.statistic.salary;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.hh.school.employerreview.OfflineCalculationConfig;

import static ru.hh.nab.common.util.PropertiesUtils.setSystemPropertyIfAbsent;

public class EmployerSalaryStatisticsCalculationLauncher {

  private static ApplicationContext applicationContext;

  public static void main(String... args) {
    if (applicationContext == null) {
      setSystemPropertyIfAbsent("settingsDir", "src/etc");
      applicationContext = new AnnotationConfigApplicationContext(OfflineCalculationConfig.class);
    }

    EmployerSalaryStatisticsCalculationWorker worker = applicationContext.getBean(EmployerSalaryStatisticsCalculationWorker.class);
    worker.doWork();
  }

  public static void setApplicationContext(ApplicationContext applicationContext) {
    EmployerSalaryStatisticsCalculationLauncher.applicationContext = applicationContext;
  }
}
