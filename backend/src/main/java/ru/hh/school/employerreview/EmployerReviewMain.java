package ru.hh.school.employerreview;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.hh.nab.core.Launcher;
import ru.hh.nab.core.servlet.DefaultServletConfig;
import ru.hh.school.employerreview.filter.CorsFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class EmployerReviewMain extends Launcher {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(ProdConfig.class);
    doMain(
        context,
        new DefaultServletConfig() {
          @Override
          public void configureServletContext(ServletContextHandler servletContextHandler, ApplicationContext applicationContext) {
            super.configureServletContext(servletContextHandler, applicationContext);
            servletContextHandler.addFilter(CorsFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
          }
        });
  }
}
