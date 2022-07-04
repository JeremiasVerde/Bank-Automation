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

    //method
    @BeforeTest
    public void setUp(){
        FirefoxDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Util.TIMEOUT));
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

    public void enterCredentials(String userId, String password, WebDriver driver){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUserId(userId);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }

    @Test(dataProvider = "LoginProvider")
    public void testLogin(String userId, String password) {
        enterCredentials(userId, password,driver);
        try{
            Alert alert = driver.switchTo().alert();
            String popText = alert.getText();
            alert.accept();
            Assert.assertEquals(popText,LoginPage.LOGIN_ERROR);
        } catch (NoAlertPresentException e){
            String actualTitle = driver.getTitle();
            Assert.assertEquals(actualTitle, HomePage.Title);
            HomePage homePage = new HomePage(driver);
            String actualWelcomeText = homePage.getWelcomeText();
            String expectedWelcomeText = "Manger Id : " + userId;
            Assert.assertEquals(actualWelcomeText,expectedWelcomeText);
            String actualBankName = homePage.getTitleBank();
            String expectedBankName = HomePage.BANK_TITLE;
            Assert.assertEquals(actualBankName,expectedBankName);
        }
    }

    @Test
    public void testLoginsuccesful() {
        HomePage homePage = new HomePage(driver);
        enterCredentials(Util.USER_ID,Util.PASSWORD,driver);
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, HomePage.Title);
        String actualBankName = homePage.getTitleBank();
        String expectedBankName = HomePage.BANK_TITLE;
        Assert.assertEquals(actualBankName,expectedBankName);
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
    public void testLoginFail(String userId, String password) {
        enterCredentials(userId, password,driver);
        Alert alert = driver.switchTo().alert();
        String popText = alert.getText();
        alert.accept();
        Assert.assertEquals(popText,LoginPage.LOGIN_ERROR);
    }

    @AfterTest
    public void terminateBrowser (){
        driver.quit();
    }
}
