package qa_test;

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

    public int addCountGood(int count){
        WebElement countBox = driver.findElement(By.cssSelector("[data-widget*='split']"));
        countBox = countBox.findElement(By.xpath(".//div[@role='listbox']//input"));
        countBox.click();
        (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>(){
            public Boolean apply(WebDriver driver){
                WebElement countBox = driver.findElement(By.cssSelector("[data-widget*='split']"))
                        .findElement(By.xpath(".//div[@role='listbox']//input"));
                return countBox.getAttribute("aria-expanded").equals("true");
            }
        });
        int k = count;
        for (int i = 1; i < count; i++) {
            countBox.sendKeys(Keys.ARROW_DOWN);
        }
        countBox.sendKeys(Keys.ENTER);
        return k;
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

    public void checkPrice(int count){
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
