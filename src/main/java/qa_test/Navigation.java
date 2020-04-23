package qa_test;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Navigation {
    private WebElement buttonCity;
    private WebDriver driver;

    public Navigation(WebDriver driver){ this.driver = driver; }

    private void findCity(){
        this.buttonCity = driver.findElement(By.cssSelector("[role*='navigation'] [type*='button']"));
    }

    @Step("Нажатие на город в левом верхнем углу страницы")
    void clickCity() {
        findCity();
        this.buttonCity.click();
    }

    @Step("Проверка города в левом верхнем углу страницы ({0})")
    public void checkCity(String city){
        findCity();
        Assert.assertTrue(buttonCity.findElement(By.xpath(".//span")).getAttribute("textContent").contains(city));
    }
}
