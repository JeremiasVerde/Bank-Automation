package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PasswordPage {
    private WebDriver driver;
    public static final String CHANGE_PASSWORD_ERROR = "Old Password is incorrect";
    public static final String NEW_PASSWORD="@123456";
    public static final String OLD_PASSWORD="Incorrect Old Password";
    public static final String PARCIAL_URL="manager/PasswordInput.php";

    //Locators
    By changePasswordButton = By.cssSelector("a[href='PasswordInput.php']");
    By oldPassWord = By.name("oldpassword");
    By newPassWord = By.name("newpassword");
    By confirmPassWord = By.name("confirmpassword");
    By submitButton = By.name("sub");

    public PasswordPage(WebDriver driver){
        this.driver=driver;
    }

    //Methods
    public void clickChancePasswordButton() {
        WebElement change = driver.findElement(changePasswordButton);
        change.click();
    }

    public void enterOldPassword(String old_Pass){
        WebElement oldPass = driver.findElement(oldPassWord);
        oldPass.sendKeys(old_Pass);
    }

    public void enterNewPassword(String new_pass){
        WebElement newPass = driver.findElement(newPassWord);
        newPass.sendKeys(new_pass);
    }

    public void enterConfirmPassword(String new_pass){
        WebElement confirmPass = driver.findElement(confirmPassWord);
        confirmPass.sendKeys(new_pass);
    }

    public void clickSubmit(){
        WebElement submit = driver.findElement(submitButton);
        submit.click();
    }
}
