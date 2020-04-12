package qa_test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class ItemScope {
    private WebElement buttonLogin;
    private WebDriver driver;

    public ItemScope(WebDriver driver){ this.driver = driver; }

    public void findButtonLogin(){
        this.buttonLogin = driver.findElement(By.cssSelector("[data-widget*='profileMenuAnonymous']"));
    }

    public void clickButtonLogin(){
        findButtonLogin();
        this.buttonLogin.click();
    }

    public void checkProfile(){
        this.buttonLogin = driver.findElement(By.cssSelector("[data-widget*='profile']"));
        Assert.assertEquals(this.buttonLogin.findElement(By.cssSelector(".a8y2")).getAttribute("textContent"),
                "Кабинет");
    }
}
