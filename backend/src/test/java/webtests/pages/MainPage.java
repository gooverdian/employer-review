package webtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage extends Page {

  @FindBy(xpath = "//*[text()='ОСТАВИТЬ ОТЗЫВ']")
  private WebElement feedbackButton;

  @FindBy(xpath = "//*[@class='employer-search-results']")
  private WebElement searchResultsList;

  public MainPage(WebDriver driver, String url) {
    super(driver);
    this.url = url;
    PageFactory.initElements(driver, this);
  }

  public boolean isResultDisplayed() {
    List<WebElement> list = driver.findElements(By.xpath("//*[@class='employer-search-results']"));
    return !list.isEmpty();
  }

  public void clickOnResult(int index) {
    List<WebElement> list = driver.findElements(By.xpath("//*[@class='employer-search-results']"));
    list.get(index).click();
  }
}
