package webtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage extends Page {
  @FindBy(xpath = "//input")
  private WebElement searchLine;

  @FindBy(xpath = "//*[text()='ОСТАВИТЬ ОТЗЫВ']")
  public WebElement feedbackButton;

  @FindBy(xpath = "//*[@class='employer-search-results']")
  public WebElement searchResultsList;

  public MainPage(WebDriver driver, String url) {
    super(driver);
    this.url = url;
    PageFactory.initElements(driver, this);
  }

  public boolean isResultDisplayed() {
    List<WebElement> list = driver.findElements(By.xpath("//*[@class='employer-search-results']"));
    return !list.isEmpty();
  }

  public void search(String request) {
    searchLine.click();
    searchLine.clear();
    searchLine.sendKeys(request);
  }
}
