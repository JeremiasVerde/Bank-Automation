package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PasswordPage {
    private WebDriver driver;

    //Locators
    By changePasswordButton = By.cssSelector("a[href='PasswordInput.php']");

    public PasswordPage(WebDriver driver){
        this.driver=driver;
    }

    //Methods
    public void clickChancePasswordButton() {
        WebElement change = driver.findElement(changePasswordButton);
        change.click();
    }
}
