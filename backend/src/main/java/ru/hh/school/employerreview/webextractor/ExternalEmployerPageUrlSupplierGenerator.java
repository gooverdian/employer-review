package ru.hh.school.employerreview.webextractor;

import java.util.Iterator;

public class ExternalEmployerPageUrlSupplierGenerator implements Iterable<ExternalEmployerPageUrlSupplierGenerator.EmployerPageUrlSupplier> {

  private final static String[] categories = {
      "https://pravda-sotrudnikov.ru/catalog/uslugi-naseleniju",
      "https://pravda-sotrudnikov.ru/catalog/optovaja-i-roznichnaja-torgovlja",
      "https://pravda-sotrudnikov.ru/catalog/proizvoditeli-i-postavshhiki"
  };
  private int currentCategory = 0;

  @Override
  public Iterator<EmployerPageUrlSupplier> iterator() {
    return new EmployerUrlPageUrlSupplierGeneratorIterator();
  }

  public final class EmployerUrlPageUrlSupplierGeneratorIterator implements Iterator<EmployerPageUrlSupplier> {

    @Override
    public boolean hasNext() {
      return currentCategory < categories.length - 1;
    }

    @Override
    public EmployerPageUrlSupplier next() {
      EmployerPageUrlSupplier employerPageUrlSupplier = new EmployerPageUrlSupplier(categories[currentCategory]);
      currentCategory++;
      return employerPageUrlSupplier;
    }
  }

  final static class EmployerPageUrlSupplier {
    private final String categoryUrl;
    private int currentPage = 0;

    EmployerPageUrlSupplier(String categoryUrl) {
      this.categoryUrl = categoryUrl;
    }

    String nextPage() {
      currentPage++;
      return categoryUrl + "?page=" + currentPage;
    }
  }
}
