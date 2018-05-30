package ru.hh.school.employerreview.review.generator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

public class ReviewsGeneratorMain {
  private final static Logger LOGGER = Logger.getLogger(ReviewsGeneratorMain.class.getName());

  private static int employersToProcessLimit = 1000;
  private static float perEmployerAvgReviewsCount = 5;
  private static float perEmployerReviewsCountDeviation = 5;
  private static int employersPerPage = 250;

  public static void main(String... args) {
    for (String arg : args) {
      if (arg.contains("limit")) {
        String[] items = arg.split("=");
        if (items.length > 1) {
          try {
            employersToProcessLimit = Integer.parseInt(items[1]);
            LOGGER.info("Employer to process limit = " + employersToProcessLimit);
          } catch (NumberFormatException e) {
            e.printStackTrace();
          }
        }
      }
      if (arg.contains("reviews")) {
        String[] items = arg.split("=");
        if (items.length > 1) {
          try {
            perEmployerAvgReviewsCount = Float.parseFloat(items[1]);
            LOGGER.info("Review average count = " + perEmployerAvgReviewsCount);
          } catch (NumberFormatException e) {
            e.printStackTrace();
          }
        }
      }
      if (arg.contains("deviation")) {
        String[] items = arg.split("=");
        if (items.length > 1) {
          try {
            perEmployerReviewsCountDeviation = Float.parseFloat(items[1]);
            LOGGER.info("Reviews count deviation = " + perEmployerReviewsCountDeviation);
          } catch (NumberFormatException e) {
            e.printStackTrace();
          }
        }
      }
      if (arg.contains("per_page")) {
        String[] items = arg.split("=");
        if (items.length > 1) {
          try {
            employersPerPage = Integer.parseInt(items[1]);
            LOGGER.info("Reviews count deviation = " + employersPerPage);
          } catch (NumberFormatException e) {
            e.printStackTrace();
          }
        }
      }
    }

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ReviewsGeneratorConfig.class);
    ReviewsGenerator reviewsGenerator = applicationContext.getBean(ReviewsGenerator.class);

    reviewsGenerator.setEmployersToProcessLimit(employersToProcessLimit);
    reviewsGenerator.setEmployersPerPage(employersPerPage);
    reviewsGenerator.setPerEmployerAvgReviewsCount(perEmployerAvgReviewsCount);
    reviewsGenerator.setPerEmployerReviewsCountDeviation(perEmployerReviewsCountDeviation);

    reviewsGenerator.process();
  }
}
