package webtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ReviewsPage extends EmployerBasedPage {

  public ReviewsPage(WebDriver driver, String url) {
    super(driver);
    this.url = url;
    PageFactory.initElements(driver, this);
  }

  public String getLastEstimateText() {
    List<WebElement> list = driver.findElements(By.xpath("//*[contains(@class,'review__text')]"));
    return list.get(list.size() - 1).getText();
  }

  public Integer getLastReviewStarValue() {
    List<WebElement> list = driver.findElements(By.xpath("//*[contains(@class,'review__header')]"));
    return Integer.parseInt(list.get(list.size() - 1).getText());
  }
}
