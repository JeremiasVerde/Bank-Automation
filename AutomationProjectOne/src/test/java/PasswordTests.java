import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.FirefoxDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import page_objects.PasswordPage;

import java.time.Duration;


public class PasswordTests {

    public WebDriver driver;

    public void verifyCurrentUrl(){
        String actualURL = driver.getCurrentUrl();
        String ExpectedURL = Util.BASE_URL + "manager/PasswordInput.php";
        Assert.assertEquals(actualURL,ExpectedURL);
    }

    @BeforeTest
    public void setUp(){
        FirefoxDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Util.TIMEOUT));
        driver.get(Util.BASE_URL);
    }

    @BeforeMethod
    public void login(){
        LoginTests login = new LoginTests();
        login.enterCredentials(Util.USER_ID,Util.PASSWORD,driver);
    }

    @Test
    public void testWrongOldPassword(){
        PasswordPage passwordPage = new PasswordPage(driver);
        passwordPage.clickChancePasswordButton();
        verifyCurrentUrl();
        passwordPage.enterOldPassword(PasswordPage.OLD_PASSWORD);
        passwordPage.enterNewPassword(PasswordPage.NEW_PASSWORD);
        passwordPage.enterConfirmPassword(PasswordPage.NEW_PASSWORD);
        passwordPage.clickSubmit();
        Alert alert = driver.switchTo().alert();
        String popText = alert.getText();
        alert.accept();
        Assert.assertEquals(popText, PasswordPage.CHANGE_PASSWORD_ERROR);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(Util.TIMEOUT));
        wait.until(ExpectedConditions.urlContains("PasswordInput.php"));
        verifyCurrentUrl();
    }

    @AfterTest
    public void terminateBrowser (){
        driver.quit();
    }
}
