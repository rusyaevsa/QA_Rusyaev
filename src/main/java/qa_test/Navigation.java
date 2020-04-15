package qa_test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Navigation {
    private WebElement buttonCity;
    private WebDriver driver;

    public Navigation(WebDriver driver){ this.driver = driver; }

    public void findCity(){
        this.buttonCity = driver.findElement(By.cssSelector("[role*='navigation'] [type*='button']"));
    }

    public void clickCity() {
        findCity();
        this.buttonCity.click();
    }

    public void checkCity(String city){
        findCity();
        Assert.assertTrue(buttonCity.findElement(By.xpath(".//span")).getAttribute("textContent").contains(city));
    }
}
