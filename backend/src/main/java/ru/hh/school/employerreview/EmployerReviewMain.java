package ru.hh.school.employerreview;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.hh.nab.core.Launcher;

public class EmployerReviewMain extends Launcher {

  public static void main(String[] args) {
    doMain(new AnnotationConfigApplicationContext(ProdConfig.class));
  }
}
