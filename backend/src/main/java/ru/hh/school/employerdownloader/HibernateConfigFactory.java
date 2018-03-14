package ru.hh.school.employerdownloader;

import org.hibernate.cfg.Configuration;
import ru.hh.school.employerreview.employer.Employer;

class HibernateConfigFactory {

  public static Configuration prod() {
    return new Configuration().addAnnotatedClass(Employer.class);
  }

  private HibernateConfigFactory() {
  }
}
