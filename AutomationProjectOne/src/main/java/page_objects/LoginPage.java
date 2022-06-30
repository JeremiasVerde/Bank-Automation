package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    WebDriver driver;

    //locators
    By loginButton = By.name("btnLogin");
    By userIdInput = By.name("uid");
    By passwordInput = By.name("password");

    //methods
    public LoginPage(WebDriver driver){
        this.driver=driver;
    }

    public void enterUserId(String userId){
        WebElement elem_userId = driver.findElement(userIdInput);
        elem_userId.sendKeys(userId);
    }
    public void enterPassword(String password){
        WebElement elem_password = driver.findElement(passwordInput);
        elem_password.sendKeys(password);
    }
    public void clickLoginButton(){
        WebElement login = driver.findElement(loginButton);
        login.click();
    }
}
