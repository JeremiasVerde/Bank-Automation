import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.FirefoxDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import page_objects.HomePage;
import page_objects.LoginPage;
import java.time.Duration;


public class LoginTests {
    public WebDriver driver ;
    private LoginPage loginPage;

    //method
    @BeforeTest
    public void setUp(){
        FirefoxDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Util.TIMEOUT));
        loginPage = new LoginPage(driver);
    }

    @BeforeMethod
    public void initializeTest(){
        driver.get(Util.BASE_URL);
    }

    @DataProvider(name="LoginProvider")
    public Object[][] getDataFromDataProvider(){
        return new Object[][]{
                {Util.USER_ID, Util.PASSWORD},
                {Util.INVALID,Util.PASSWORD},
                {Util.USER_ID,Util.INVALID},
                {Util.INVALID,Util.INVALID}
        };
    }

    @Test(dataProvider = "LoginProvider")
    public void verifyLogin(String userId, String password) {
        loginPage.enterUserId(userId);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        try{
            Alert alert = driver.switchTo().alert();
            String popText = alert.getText();
            alert.accept();
            Assert.assertEquals(popText,Util.LOGIN_ERROR);
        } catch (NoAlertPresentException e){
            String actualTitle = driver.getTitle();
            Assert.assertEquals(actualTitle, HomePage.Title);
        }
    }

    //TODO: 2 TESTS UNO PARA LOGINSUCCESFUL Y OTRO PARA LOGINFAIL
    //TODO: SUBIR ESTE A UN GITHUB. NOMBRE DEL REPO: BANK AUTOMATION

    @Test
    public void verifyLoginsuccesful() {
        loginPage.enterUserId(Util.USER_ID);
        loginPage.enterPassword(Util.PASSWORD);
        loginPage.clickLoginButton();
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, HomePage.Title);
    }

    @DataProvider(name="LoginProviderFail")
    public Object[][] getDataFromDataProviderFail(){
        return new Object[][]{
                {Util.INVALID,Util.PASSWORD},
                {Util.USER_ID,Util.INVALID},
                {Util.INVALID,Util.INVALID}
        };
    }

    @Test(dataProvider = "LoginProviderFail")
    public void verifyLoginFail(String userId, String password) {
        loginPage.enterUserId(userId);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        Alert alert = driver.switchTo().alert();
        String popText = alert.getText();
        alert.accept();
        Assert.assertEquals(popText,Util.LOGIN_ERROR);
    }

    @AfterTest
    public void terminateBrowser (){
        driver.quit();
    }
}
