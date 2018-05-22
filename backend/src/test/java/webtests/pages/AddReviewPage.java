package webtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AddReviewPage extends EmployerBasedPage {

  @FindBy(xpath = "//div[@class='form-group']//input")
  private List<WebElement> inputs;

  @FindBy(xpath = "//div[contains(@class, 'MuiDialogActions')]//button[2]")
  private WebElement saveEmployerButton;

  @FindBy(xpath = "//div[@class='search-select__picker']//button[contains(@class, 'MuiButtonBase')]")
  private WebElement addEmployerButton;

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

  public void callAddEmployerForm() {
    addEmployerButton.click();
  }

  public void fillFormInputsAndSave(String areaName, String url) {
    type(inputs.get(1), areaName);
    type(inputs.get(2), url);
    saveEmployerButton.click();
  }

  public boolean isResultDisplayed() {
    List<WebElement> list = driver.findElements(
        By.xpath("//*[@class='search-select__results']//ul//div[contains(@class, 'MuiListItemText')]"));
    return !list.isEmpty();
  }
}
