package ru.hh.school.employerreview.webextractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ExternalReviewProcessor implements Runnable {
  private static final Logger LOGGER = Logger.getLogger(ExternalReviewProcessor.class.getName());

  private final static String POSITIVE = "Плюсы в работе";
  private final static String NEGATIVE = "Отрицательные стороны";

  private final AtomicInteger counter;
  private final String pageUrl;
  private final ExternalReviewDao externalReviewDao;
  private final int reviewLengthThreshold;

  public ExternalReviewProcessor(String pageUrl, ExternalReviewDao externalReviewDao, AtomicInteger counter, int reviewLengthThreshold) {
    this.pageUrl = pageUrl;
    this.externalReviewDao = externalReviewDao;
    this.counter = counter;
    this.reviewLengthThreshold = reviewLengthThreshold;
  }

  @Override
  public void run() {
    try {
      Document doc = Jsoup.connect(pageUrl).get();
      doc.select(".company-reviews-list-item-text-container").forEach(element -> {
        ExternalReview externalReview = new ExternalReview();

        String reviewText = element.select(".company-reviews-list-item-text-message").text().trim();
        String reviewPositivity = element.select(".company-reviews-list-item-text-title").text().trim();

        if (reviewText.length() > reviewLengthThreshold) {
          if (reviewPositivity.equals(POSITIVE)) {
            externalReview.setExternalReviewPositivity(ExternalReviewPositivity.POSITIVE);
          }
          if (reviewPositivity.equals(NEGATIVE)) {
            externalReview.setExternalReviewPositivity(ExternalReviewPositivity.NEGATIVE);
          }
          externalReview.setText(reviewText);
          try {
            externalReviewDao.save(externalReview);
            counter.incrementAndGet();
          } catch (Exception e) {
            LOGGER.warning(e.getMessage());
          }
        }
      });
    } catch (IOException e) {
      LOGGER.warning(e.getMessage());
    }
  }
}
