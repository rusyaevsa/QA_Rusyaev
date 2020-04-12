package qa_test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class CityChange {
    private WebDriver driver;

    public CityChange(WebDriver driver) {
        this.driver = driver;
    }

    public void inputCity(String city) throws InterruptedException {
        WebElement formInputCity = driver.findElement(By.className("modal-content"));
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.visibilityOf(formInputCity));
        WebElement fieldInputCity = formInputCity.findElement(By.xpath(".//input[@type='text']"));
        fieldInputCity.sendKeys(city);
        // ИСПРАВИТЬ
        Thread.sleep(1000);

        fieldInputCity.sendKeys(Keys.ENTER);

        driver.navigate().refresh();
    }
}
