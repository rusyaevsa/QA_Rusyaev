package qa_test;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Basket {
    private WebDriver driver;

    public Basket(WebDriver driver){
        this.driver = driver;
    }

    @Step("Добавление {0} штук данного товара (или сколько имеется)")
    int addCountGood(int count){
        WebElement countBoxMain = driver.findElement(By.cssSelector("[data-widget*='split']"));
        countBoxMain = countBoxMain.findElement(By.xpath(".//div[@role='listbox']"));
        WebElement countBox = countBoxMain.findElement(By.xpath(".//input"));
        countBox.click();
        (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>(){
            public Boolean apply(WebDriver driver){
                WebElement countBox = driver.findElement(By.cssSelector("[data-widget*='split']"))
                        .findElement(By.xpath(".//div[@role='listbox']//input"));
                return countBox.getAttribute("aria-expanded").equals("true");
            }
        });

        for (int i = 1; i < count; i++) {
            countBox.sendKeys(Keys.ARROW_DOWN);
        }
        countBox.sendKeys(Keys.ENTER);
        return Integer.parseInt(countBoxMain.findElement(By.xpath("./div/div/div"))
                .getAttribute("textContent").replaceAll("\\D", ""));
    }

    private void waitLoadedRight(final int count){
        (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                WebElement rightPanel = driver.findElement(By.cssSelector("[data-widget=\"stickyContainer\"]"))
                        .findElement(By.xpath(".//span[text()[contains(., 'Товары')]]"));
                return rightPanel.getAttribute("textContent").contains(Integer.toString(count));
            }
        });
    }

    @Step("Проверка соответствия цены {0} штук товара")
    void checkPrice(int count){
        waitLoadedRight(count);
        WebElement good = driver.findElement(By.cssSelector("[data-widget=\"split\"]"));
        WebElement priceOne = good.findElement(By.xpath(".//div[text()[contains(., 'шт.')]]"));
        int priceSh = Integer.parseInt(priceOne.getAttribute("textContent")
                .replaceAll("\\D", ""));
        int priceAll = priceSh * count;
        WebElement rightPanel = driver.findElement(By.cssSelector("[data-widget=\"stickyContainer\"]"))
                .findElement(By.xpath(".//span[text()[contains(., 'Общая стоимость')]]/../span[2]"));
        int priceInPanel = Integer.parseInt(rightPanel.getAttribute("textContent")
                .replaceAll("\\D", ""));
        Assert.assertEquals(priceAll, priceInPanel);
    }
}
