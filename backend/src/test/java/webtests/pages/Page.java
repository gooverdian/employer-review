package webtests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
  WebDriver driver;
  private WebDriverWait wait;
  protected String handle;
  String url;

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
}
