package qa_test;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ItemScope {
    private WebElement buttonLogin;
    private WebElement buttonCatalog;
    private WebDriver driver;

    public ItemScope(WebDriver driver){ this.driver = driver; }

    private void findButtonLogin(){
        this.buttonLogin = driver.findElement(By.cssSelector("[data-widget*='profileMenuAnonymous']"));
    }

    @Step("Нажатие на кнопку \"Войти\"")
    void clickButtonLogin(){
        findButtonLogin();
        this.buttonLogin.click();
    }

    private void findButtonProfile(){
        this.buttonLogin = driver.findElement(By.cssSelector("[data-widget*='profile']"));
    }

    @Step("Нажатие на кнопку \"Кабинет\"")
    void clickButtonProfile(){
        findButtonProfile();
        this.buttonLogin.click();
    }

    @Step("Проверка надписи на кнопке \"Кабинет\"")
    void checkProfile(){
        findButtonProfile();
        Assert.assertEquals(this.buttonLogin.findElement(By.xpath(".//a/span[2]")).getAttribute("textContent"),
                "Кабинет");
    }

    private void findButtonCatalog(){
        this.buttonCatalog = driver.findElement(By.cssSelector("[data-widget*='catalogMenu']"));
    }

    @Step("Нажатие на кнопку \"Каталог\"")
    void clickButtonCatalog(){
        findButtonCatalog();
        this.buttonCatalog.click();
    }

    // выбрать соковыжималку
    @Step("Выбор раздела \"Бытовая техника\"")
    void chooseJuicer(){
        WebElement catalog = driver.findElement(By.cssSelector("[href='/category/bytovaya-tehnika-10500/']"));
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.visibilityOf(catalog));
        //(new Actions(driver)).moveToElement(catalog).build().perform();
        catalog.click();
    }

    @Step("Нажатие на кнопку \"Корзина\"")
    void goToBasket(){
        WebElement basket = driver.findElement(By.cssSelector("[data-widget*='cart']"));
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>(){
            public Boolean apply(WebDriver driver){
                WebElement count = driver.findElement(By.cssSelector("[data-widget*='cart']"))
                        .findElement(By.cssSelector("[class*='f-caption--bold']"));
                return count.getAttribute("textContent").equals("1");
            }
        });
        basket.click();
    }


}
