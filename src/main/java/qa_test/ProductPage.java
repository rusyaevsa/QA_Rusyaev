package qa_test;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductPage {
    private WebDriver driver;

    public ProductPage(WebDriver driver){
        this.driver = driver;
    }

    private void waitJuicer(){
        WebElement typeLabel = driver.findElement(By.xpath("//div[text()[contains(., 'Вид соковыжималки')]]"));
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.visibilityOf(typeLabel));
    }

    @Step("Ввод диапазона цен от {0} до {1}")
    public void inputPrice(int priceIn, int priceOut) {
        waitJuicer();
        WebElement priceForm =  driver.findElement(By.cssSelector("[data-widget=\"searchResultsFilters\"]"))
                .findElement(By.xpath(".//div[text()[contains(., 'Цена')]]/.."));
        //ищем форму для ввода цены и вводим минимальную цену
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.visibilityOf(priceForm));
        WebElement priceInForm = priceForm.findElement(By.cssSelector("[qa-id*='range-from']"));

        String strPrice = "\b\b\b\b\b" + priceIn;
        priceInForm.sendKeys(strPrice);

        // ждём появления формы с диапазоном цен и после этого вводим верхнюю границу цены
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.visibilityOf(priceForm));
        WebElement priceOutForm = priceForm.findElement(By.cssSelector("[qa-id*='range-to']"));
        (new Actions(driver)).moveToElement(priceOutForm, priceOutForm.getSize().getWidth() - 1, 1)
                .click().build().perform();
        WebElement priceRange = driver.findElement(By.cssSelector("[data-widget*='searchResultsFiltersActive']"));
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.visibilityOf(priceRange));

        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.visibilityOf(priceOutForm));
        strPrice = "\b\b\b\b\b\b" + Integer.toString(priceOut);
        priceOutForm.sendKeys(strPrice);
        priceForm.click();
    }

    @Step("Проверка диапазона цен от {0} до {1}")
    public void checkJuicersPrices(final int priceIn, final int priceOut){
        // ожидаем появления формы с правильным диапазоном цен
        (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                String range = driver.findElement(By
                        .cssSelector("[data-widget*='searchResultsFiltersActive']"))
                        .findElement(By.xpath(".//div//span[text()[contains(., 'Цена')]]"))
                        .getAttribute("textContent").replaceAll("\\D", "");
                System.out.println(range);
                String userRange = Integer.toString(priceIn) + Integer.toString(priceOut);
                return range.equals(userRange);
            }
        });

        // извлекаем список цен соковыжималок
        WebElement searchResult = driver.findElement(By.cssSelector("[data-widget='searchResultsV2']"));
        List<WebElement> prices = searchResult.findElements(By.xpath("./div/div/div[@class]"));
        boolean allPrice = true;
        //проходим по всем ценам и проверяем на соответствие диапазону
        for(WebElement price: prices){
            WebElement priceValue = price.findElement(By.xpath("./div/div[3]/a//span"));
            System.out.println(priceValue.getAttribute("textContent"));
            int nowPrice = Integer
                    .parseInt(priceValue.getAttribute("textContent").replaceAll("\\D", ""));
            if (nowPrice > priceOut || nowPrice < priceIn) {
                allPrice = false;
                break;
            }
        }
        Assert.assertTrue(allPrice);
    }

    @Step("Сортировка товара по цене \"Сначала дешевые\"")
    public void sortPrice(){
        waitJuicer();
        PublicMethods.closeCookie(driver);
        // находим combobox с ценами и кликаем на него
        WebElement sortBox = driver.findElement(By.cssSelector("[data-widget*='searchResultsSort']"));
        sortBox = sortBox.findElement(By.xpath(".//input"));
        sortBox.click();
        // ожидаем обновление элемента combobox
        (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean> (){
            public Boolean apply(WebDriver driver){
                WebElement sortBox = driver.findElement(By.cssSelector("[data-widget*='searchResultsSort']"))
                        .findElement(By.xpath(".//input"));
                System.out.println(sortBox.getAttribute("aria-expanded"));
                return sortBox.getAttribute("aria-expanded").equals("true");
            }
        });

        // выбираем сортировку по цене(сначала дешёвые)
        sortBox.sendKeys(Keys.ARROW_DOWN);
        sortBox.sendKeys(Keys.ARROW_DOWN);
        sortBox.sendKeys(Keys.ENTER);
        // Ожидание, что combobox устаревает
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.stalenessOf(sortBox));
    }

    @Step("Добавление второго из списка товара в корзину")
    void addIntoBasket(){
        WebElement searchResult = driver.findElement(By.cssSelector("[data-widget='searchResultsV2']"));
        WebElement juicerTwo = searchResult
                .findElement(By.xpath("./div/div[2]"));
        WebElement juicerBasket = juicerTwo.findElement(By.xpath(".//button[.//*[contains(., 'В корзину')]]"));
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(juicerBasket));
        juicerBasket.click();
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(juicerTwo
                .findElement(By.xpath(".//span[text()[contains(., '1 шт')]]"))));
    }

    @Step("Ввод диапазона мощности >= {0}")
    void inputPower(int power) {
        waitJuicer();
        WebElement powerForm =
                driver.findElement(By.cssSelector("[data-widget=\"searchResultsFilters\"]"))
                        .findElement(By.xpath(".//div[text()[contains(., 'Мощность')]]/.."));
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.visibilityOf(powerForm));
        WebElement powerInForm = powerForm.findElement(By.cssSelector("[qa-id*='range-from']"));

        String strPrice = "\b\b\b\b\b" + Integer.toString(power);
        powerInForm.sendKeys(strPrice);
        powerForm.click();
    }

    @Step("Проверка диапазона мощности >= {0}")
    void checkPower(final int power){
        // ожидаем появления формы с диапазоном мощности
        (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                String powerRange = driver.findElement(By
                        .cssSelector("[data-widget*='searchResultsFiltersActive']"))
                        .findElement(By.xpath(".//div//span[text()[contains(., 'Мощность')]]"))
                        .getAttribute("textContent").replaceAll("\\D", "").substring(0, 4);
                System.out.println(powerRange);
                return powerRange.equals(Integer.toString(power));
            }
        });

        // из списка соковыжималок выбираем список мощностей
        WebElement searchResult = driver.findElement(By.cssSelector("[data-widget='searchResultsV2']"));
        List<WebElement> powers = searchResult.findElements(By.xpath("./div/div/div[@class]"));
        boolean allPower = true;
        // проверяем каждую соковыжималку на соответствие
        for(WebElement powerElement: powers){
            WebElement attributes = powerElement
                    .findElement(By.xpath("./div/div[2]//span[text()[contains(., 'Мощность')]]"));
            String powerValue = attributes.getAttribute("textContent");
            Pattern pattern = Pattern.compile("Мощность, Вт: \\d*");
            Matcher matcher = pattern.matcher(powerValue);
            matcher.find();
            powerValue = powerValue.substring(matcher.start(), matcher.end()).replaceAll("\\D", "");
            System.out.println(powerValue);
            int nowPower = Integer.parseInt(powerValue);
            if (nowPower < power) {
                allPower = false;
                break;
            }
        }
        Assert.assertTrue(allPower);
    }
}
