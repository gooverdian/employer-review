package webtests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
  WebDriver driver;
  private WebDriverWait wait;
  protected String handle;
  String url;

  @FindBy(xpath = "//input")
  protected WebElement searchLine;

  Page(WebDriver driver) {
    this.driver = driver;
    wait = new WebDriverWait(driver, 10);
  }

  public void open() {
    driver.navigate().to(url);
    handle = driver.getWindowHandle();
  }

  void type(WebElement element, String text) {
    element.click();
    element.clear();
    element.sendKeys(text);
  }

  public void search(String request) {
    searchLine.click();
    searchLine.clear();
    searchLine.sendKeys(request);
  }
}
