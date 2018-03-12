package ru.hh.school.employerreview.employer_downloader;

import org.hibernate.cfg.Configuration;
import employer.Employer;

class HibernateConfigFactory {

  public static Configuration prod() {
    return new Configuration().addAnnotatedClass(Employer.class);
  }

  private HibernateConfigFactory() {
  }
}
