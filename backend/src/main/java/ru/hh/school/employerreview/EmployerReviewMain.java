package ru.hh.school.employerreview;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.hh.nab.core.Launcher;
import static ru.hh.nab.common.util.PropertiesUtils.setSystemPropertyIfAbsent;

public class EmployerReviewMain extends Launcher {

  public static void main(String[] args) {
    setSystemPropertyIfAbsent("settingsDir", "src/etc");
    doMain(new AnnotationConfigApplicationContext(ProdConfig.class));
  }
}
