package ru.hh.school.employerreview.webextractor;

public class ExternalReviewPageUrlSupplier {
  private final static String HOST_URL = "https://pravda-sotrudnikov.ru/";
  private final ExternalEmployer externalEmployer;
  private int currentPage = 0;

  public ExternalReviewPageUrlSupplier(ExternalEmployer externalEmployer) {
    this.externalEmployer = externalEmployer;
  }

  public String nextPage() {
    currentPage++;
    return HOST_URL + externalEmployer.getPath() + "?page=" + currentPage;
  }
}
