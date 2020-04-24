package qa_test;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CityChange {
    private WebDriver driver;

    public CityChange(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Изменение города на {0}")
    public void inputCity(String city) throws InterruptedException {
        WebElement formInputCity = driver.findElement(By.className("modal-content"));
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.visibilityOf(formInputCity));

        // вводим название города
        WebElement fieldInputCity = formInputCity.findElement(By.xpath(".//input[@type='text']"));
        fieldInputCity.sendKeys(city);

        // ждём пока список городов обновится и нажимаем ENTER
        WebElement cityLi = formInputCity.findElement(By.xpath(".//a[text()[contains(., '" + city + "')]]"));
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(cityLi));
        fieldInputCity.sendKeys(Keys.ENTER);
        // ожидаем пока форма с выбором города не закроется
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOf(formInputCity));
    }
}
