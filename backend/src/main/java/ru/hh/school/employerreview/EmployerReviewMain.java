package ru.hh.school.employerreview;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.hh.nab.core.Launcher;
import ru.hh.nab.core.servlet.DefaultServletConfig;
import ru.hh.school.employerreview.filter.CorsFilter;
import ru.hh.school.employerreview.filter.QueryParamExceptionMapper;
import ru.hh.school.employerreview.filter.SqlExceptionMapper;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

import static ru.hh.nab.common.util.PropertiesUtils.setSystemPropertyIfAbsent;

public class EmployerReviewMain extends Launcher {

  public static void main(String[] args) {
    setSystemPropertyIfAbsent("settingsDir", "src/etc");
    ApplicationContext context = new AnnotationConfigApplicationContext(ProdConfig.class);
    doMain(
        context,
        new DefaultServletConfig() {
          @Override
          public void configureServletContext(ServletContextHandler servletContextHandler, ApplicationContext applicationContext) {
            super.configureServletContext(servletContextHandler, applicationContext);
            servletContextHandler.addFilter(CorsFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
          }

          @Override
          public ResourceConfig createResourceConfig(ApplicationContext context) {
            ResourceConfig resourceConfig = super.createResourceConfig(context);
            resourceConfig.register(QueryParamExceptionMapper.class);
            resourceConfig.register(SqlExceptionMapper.class);
            return resourceConfig;
          }
        });
  }
}
