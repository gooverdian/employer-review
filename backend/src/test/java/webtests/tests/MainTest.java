package webtests.tests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import webtests.Application;
import webtests.pages.AddReviewPage;
import webtests.pages.MainPage;
import webtests.pages.ReviewsPage;

public class MainTest {

  static final Application app = new Application();

  @AfterClass
  public static void quitApp() {
    app.quit();
  }

  @Test
  public void employerSearchTest() {
    MainPage mainPage = app.mainPage;
    mainPage.open();
    mainPage.search("h");
    Assert.assertTrue(mainPage.isResultDisplayed());
  }

  @Test
  public void addReviewTest() {
    Integer employerId = 1;
    Integer starValue = 5;
    String text = "Норм";

    AddReviewPage addReviewPage = app.addReviewPage;
    addReviewPage.setEmployerId(employerId);
    addReviewPage.open();
    addReviewPage.addReview(starValue, text);

    ReviewsPage reviewsPage = app.reviewsPage;
    reviewsPage.setEmployerId(employerId);
    reviewsPage.open();
    Assert.assertEquals(text, reviewsPage.getLastEstimateText());
    Assert.assertEquals(starValue, reviewsPage.getLastReviewStarValue());
  }
}
