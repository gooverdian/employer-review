package webtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AddReviewPage extends EmployerBasedPage {

  public AddReviewPage(WebDriver driver, String url) {
    super(driver);
    this.url = url;
    PageFactory.initElements(driver, this);
  }

  public void addReview(int starValue, String text) {
    WebElement star5 = driver.findElements(By.xpath("(//div[contains(@class, 'rating')]//button)")).get(starValue - 1);
    WebElement textArea = driver.findElement(By.xpath("//textarea[@type='text']"));
    WebElement applyButton = driver.findElement(By.xpath("//button[@type='submit']"));

    star5.click();
    type(textArea, text);
    applyButton.click();
  }
}
