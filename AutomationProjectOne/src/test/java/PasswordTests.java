import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import io.github.bonigarcia.wdm.managers.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import page_objects.PasswordPage;

import java.time.Duration;
import java.util.Arrays;

public class PasswordTests {

    public WebDriver driver;

    @BeforeTest
    public void setUp(){
        //FirefoxDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
        //driver = new FirefoxDriver();
        ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
        ChromeOptions opt = new ChromeOptions();
        opt.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));
        driver = new ChromeDriver(opt);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Util.TIMEOUT));
        driver.get(Util.BASE_URL);
    }

    @BeforeMethod
    public void login(){
        LoginTests login = new LoginTests();
        login.enterCredentials(Util.USER_ID,Util.PASSWORD,driver);
    }

    @Test
    public void testURL(){
        PasswordPage passwordPage = new PasswordPage(driver);
        passwordPage.clickChancePasswordButton();
        String actualURL = driver.getCurrentUrl();
        String ExpectiveURL = Util.BASE_URL + "manager/PasswordInput.php";
        Assert.assertEquals(actualURL,ExpectiveURL);
    }

    @AfterTest
    public void terminateBrowser (){
        driver.quit();
    }
}
