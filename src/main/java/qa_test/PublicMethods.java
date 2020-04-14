package qa_test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PublicMethods {
    public static void closeCookie(WebDriver driver){
        try {
            WebElement close = driver.findElement(By.cssSelector("[aria-label=\"Закрыть сообщение\"]"));
            (new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOf(close));
            close.click();
        }
        catch (Exception e){}
    }
}
