package ru.hh.school.employerreview.webextractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.hh.school.employerreview.webextractor.ExternalEmployerPageUrlSupplierGenerator.EmployerPageUrlSupplier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class ExternalEmployerSupplier {
  private final static Logger LOGGER = Logger.getLogger(ExternalEmployerSupplier.class.getName());

  private final List<ExternalEmployerSupplierPerCategory> externalEmployerSuppliers = new ArrayList<>();
  private int callsCount = 0;

  public ExternalEmployerSupplier() {
    for (EmployerPageUrlSupplier urlSupplier : new ExternalEmployerPageUrlSupplierGenerator()) {
      externalEmployerSuppliers.add(new ExternalEmployerSupplierPerCategory(urlSupplier));
    }
  }

  public ExternalEmployer nextExternalEmployer() {
    callsCount++;
    return externalEmployerSuppliers.get(callsCount % externalEmployerSuppliers.size()).nextExternalEmployer();
  }

  private final static class ExternalEmployerSupplierPerCategory {
    private final EmployerPageUrlSupplier pageUrlSupplier;
    private Iterator<ExternalEmployer> externalEmployers;

    private ExternalEmployerSupplierPerCategory(EmployerPageUrlSupplier pageUrlSupplier) {
      this.pageUrlSupplier = pageUrlSupplier;
    }

    public ExternalEmployer nextExternalEmployer() {
      if (externalEmployers != null && externalEmployers.hasNext()) {
        return externalEmployers.next();
      }
      List<ExternalEmployer> externalEmployerList = new ArrayList<>();
      try {
        Document document = Jsoup.connect(pageUrlSupplier.nextPage()).get();
        document.select(".mdc-companies-item-container").select(".mdc-companies-item-title").select("span").select("a").forEach(element -> {
          ExternalEmployer externalEmployer = new ExternalEmployer(element.text(), element.attr("href"));
          externalEmployerList.add(externalEmployer);
        });
      } catch (IOException e) {
        LOGGER.warning(e.getMessage());
        return null;
      }
      externalEmployers = externalEmployerList.iterator();
      if (externalEmployers.hasNext()) {
        return externalEmployers.next();
      }
      return null;
    }
  }
}
