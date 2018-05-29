package ru.hh.school.employerreview.webextractor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ReviewExtractor {
  private final static Logger LOGGER = Logger.getLogger(ReviewExtractor.class.getName());

  private static ApplicationContext applicationContext;
  private static int threadsCount = 4;
  private static int reviewsCountLimit = 30_000;
  private static int reviewLengthThreshold = 50;
  private static AtomicInteger reviewCount = new AtomicInteger(0);

  public static void main(String... args) {

    for (String arg : args) {
      if (arg.contains("threads")) {
        String[] items = arg.split("=");
        if (items.length > 1) {
          try {
            threadsCount = Integer.parseInt(items[1]);
            LOGGER.info("Threads count = " + threadsCount);
          } catch (NumberFormatException e) {
            LOGGER.warning(e.getMessage());
          }
        }
      }

      if (arg.contains("limit")) {
        String[] items = arg.split("=");
        if (items.length > 1) {
          try {
            reviewsCountLimit = Integer.parseInt(items[1]);
            LOGGER.info("Reviews count limit = " + reviewsCountLimit);
          } catch (NumberFormatException e) {
            LOGGER.warning(e.getMessage());
          }
        }
      }

      if (arg.contains("length_threshold")) {
        String[] items = arg.split("=");
        if (items.length > 1) {
          try {
            reviewLengthThreshold = Integer.parseInt(items[1]);
            LOGGER.info("Reviews length threshold = " + reviewLengthThreshold);
          } catch (NumberFormatException e) {
            LOGGER.warning(e.getMessage());
          }
        }
      }
    }

    if (applicationContext == null) {
      applicationContext = new AnnotationConfigApplicationContext(WebExtractorConfig.class);
    }
    ExternalReviewDao externalReviewDao = applicationContext.getBean(ExternalReviewDao.class);
    ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);

    ExternalEmployerSupplier externalEmployerSupplier = new ExternalEmployerSupplier();

    while (reviewCount.get() < reviewsCountLimit) {
      ExternalEmployer externalEmployer = externalEmployerSupplier.nextExternalEmployer();
      LOGGER.info("Employer being processed\t\t" + externalEmployer.getName() + "\t\t" + externalEmployer.getPath());
      ExternalReviewPageUrlSupplier externalReviewPageUrlSupplier = new ExternalReviewPageUrlSupplier(externalEmployer);
      String nextPage = externalReviewPageUrlSupplier.nextPage();
      LOGGER.info("Page being processed\t\t " + nextPage);
      ExternalReviewProcessor externalReviewProcessor = new ExternalReviewProcessor(nextPage, externalReviewDao, reviewCount, reviewLengthThreshold);
      executorService.execute(externalReviewProcessor);
      LOGGER.info("Current review count\t\t" + reviewCount.get());
    }
    executorService.shutdown();
  }

  public static void setApplicationContext(ApplicationContext applicationContext) {
    ReviewExtractor.applicationContext = applicationContext;
  }
}
