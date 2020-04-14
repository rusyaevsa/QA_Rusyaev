package qa_test;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.security.Key;
import java.util.List;

public class ProductPage {
    private WebDriver driver;

    public ProductPage(WebDriver driver){
        this.driver = driver;
    }

    public void waitJuicer(){
        WebElement typeLabel = driver.findElement(By.xpath("//div[text()[contains(., 'Вид соковыжималки')]]"));
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.visibilityOf(typeLabel));
    }

    public void inputPrice(int priceIn, int priceOut) {
        waitJuicer();
        WebElement priceInForm =
                driver.findElement(By.cssSelector("[data-widget=\"searchResultsFilters\"]"))
                .findElement(By.xpath(".//div[text()[contains(., 'Цена')]]/.."));
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.visibilityOf(priceInForm));
        priceInForm = priceInForm.findElement(By.cssSelector("[qa-id*='range-from']"));

        String strPrice = "\b\b\b\b\b" + Integer.toString(priceIn);
        priceInForm.sendKeys(strPrice);
        WebElement priceOutForm = driver.findElement(By.cssSelector("[data-widget=\"searchResultsFilters\"]"))
                .findElement(By.xpath(".//div[text()[contains(., 'Цена')]]/.."));
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.visibilityOf(priceOutForm));
        priceOutForm = priceOutForm.findElement(By.cssSelector("[qa-id*='range-to']"));
        priceOutForm.sendKeys("");

        WebElement priceRange = driver.findElement(By.cssSelector("[data-widget*='searchResultsFiltersActive']"));
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.visibilityOf(priceRange));

        priceOutForm = driver.findElement(By.cssSelector("[data-widget=\"searchResultsFilters\"]"))
                .findElement(By.xpath(".//div[text()[contains(., 'Цена')]]/.."));
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.visibilityOf(priceOutForm));
        priceOutForm = priceOutForm.findElement(By.cssSelector("[id*='to_']"));
        strPrice = "\b\b\b\b\b\b" + Integer.toString(priceOut);
        priceOutForm.sendKeys(strPrice);
        priceOutForm.sendKeys(Keys.ENTER);
    }

    public void checkJuicersPrices(int priceIn, int priceOut){
        (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                String d = driver.findElement(By
                        .cssSelector("[data-widget*='searchResultsFiltersActive']"))
                        .getAttribute("textContent").replaceAll("\\D", "");
                System.out.println(d);
                return d.equals("30004000");
            }
        });
//        JavascriptExecutor js = ((JavascriptExecutor) driver);
//        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebElement searchResult = driver.findElement(By.cssSelector("[data-widget='searchResultsV2']"));
        List<WebElement> prices = searchResult.findElements(By.xpath("./div/div"));
        System.out.println(prices.size());
        boolean allPrice = true;
        for(WebElement price: prices){
            WebElement priceValue = price.findElement(By.xpath(".//div[@class]/div/div[3]/a//span"));
            System.out.println(priceValue.getAttribute("textContent"));
            int nowPrice = Integer
                    .parseInt(priceValue.getAttribute("textContent").replaceAll("\\D", ""));
            //System.out.println(nowPrice);
            if (nowPrice > priceOut) {
                allPrice = false;
                break;
            }
        }
        Assert.assertTrue(allPrice);
    }



    public void sortPrice(){
        waitJuicer();
        PublicMethods.closeCookie(driver);
        WebElement sortBox = driver.findElement(By.cssSelector("[data-widget*='searchResultsSort']"));
        sortBox = sortBox.findElement(By.xpath(".//input"));
        sortBox.click();
        (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean> (){
            public Boolean apply(WebDriver driver){
                WebElement sortBox = driver.findElement(By.cssSelector("[data-widget*='searchResultsSort']"))
                        .findElement(By.xpath(".//input"));
                System.out.println(sortBox.getAttribute("aria-expanded"));
                return sortBox.getAttribute("aria-expanded").equals("true");
            }
        });
        sortBox.sendKeys(Keys.ARROW_DOWN);
        sortBox.sendKeys(Keys.ARROW_DOWN);
        sortBox.sendKeys(Keys.ENTER);
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.stalenessOf(sortBox));
    }

    public void addIntoBasket(){

        WebElement searchResult = driver.findElement(By.cssSelector("[data-widget='searchResultsV2']"));
        WebElement juicerBasket = searchResult
                .findElement(By.xpath("./div/div[2]//button[.//*[contains(., 'В корзину')]]"));
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(juicerBasket));
        juicerBasket.click();

    }


}
