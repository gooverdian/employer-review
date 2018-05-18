package webtests.pages;

import org.openqa.selenium.WebDriver;

public class EmployerBasedPage extends Page {

  Integer employerId;

  public EmployerBasedPage(WebDriver driver) {
    super(driver);
  }
  public void setEmployerId(Integer employerId) {
    this.employerId = employerId;
  }

  @Override
  public void open() {
    driver.navigate().to(url + employerId);
    handle = driver.getWindowHandle();
  }
}
