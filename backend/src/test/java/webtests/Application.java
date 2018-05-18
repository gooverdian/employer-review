package webtests;

import org.openqa.selenium.WebDriver;
import webtests.pages.AddReviewPage;
import webtests.pages.MainPage;
import webtests.pages.ReviewsPage;

import java.util.concurrent.TimeUnit;

public class Application {
  public final MainPage mainPage;
  public final AddReviewPage addReviewPage;
  public final ReviewsPage reviewsPage;
  private final WebDriver driver;
  static final String BASE_URL = "http://localhost:3000/";

  public Application() {
    driver = getDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().window().maximize();

    mainPage = new MainPage(driver, BASE_URL);
    addReviewPage = new AddReviewPage(driver, BASE_URL + "review/add/");
    reviewsPage = new ReviewsPage(driver, BASE_URL + "employer/");
  }

  public void quit() {
    driver.quit();
  }

  private WebDriver getDriver() {
    String browserName;
    try {
      browserName = System.getProperty("browser");
      BrowsersFactory.valueOf(browserName);
    } catch (NullPointerException | IllegalArgumentException e) {
      browserName = "chrome";
      System.setProperty("browser", browserName);
    }
    return BrowsersFactory.valueOf(browserName).create();
  }
}
