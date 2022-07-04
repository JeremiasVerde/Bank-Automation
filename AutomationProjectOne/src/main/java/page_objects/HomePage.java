package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
   public static final String Title = "Guru99 Bank Manager HomePage";
   private WebDriver driver;
   public static final String BANK_TITLE= "Guru99 Bank";

   //Locators
   By welcomeText = By.cssSelector("td[style='color: green']");
   By bankTitle= By.className("barone");

   public HomePage(WebDriver driver){
      this.driver=driver;
   }

   //Methods
   public String getWelcomeText(){
      WebElement welcomeMessage = driver.findElement(welcomeText);
      return welcomeMessage.getText();
   }

   public String getTitleBank(){
      WebElement bankName = driver.findElement(bankTitle);
      return bankName.getText();
   }
}
