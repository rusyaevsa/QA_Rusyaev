package qa_test;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LeftPanel {
    private WebDriver driver;

    public LeftPanel(WebDriver driver){ this.driver = driver; }

    @Step("Выбираем категорию \"Соковыжималки\"")
    public void chooseJuiser(){
        // ищем и нажимаем на элемент техника для кухни
        WebElement showAll = driver.findElement(By.cssSelector("[href='/category/tehnika-dlya-kuhni-10523/']"));
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.visibilityOf(showAll));
        String code = "window.scroll(" + (showAll.getLocation().x + 20) + ","
                + (showAll.getLocation().y + 20) + ");";

        ((JavascriptExecutor)driver).executeScript(code, showAll, 20, 20);
        PublicMethods.closeCookie(driver);
        showAll.click();
        PublicMethods.closeCookie(driver);
        // ищем и нажимаем на эелемент соковыжималки
        WebElement juicer = driver.findElement(By.cssSelector("[href='/category/sokovyzhimalki-10592/']"));
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.visibilityOf(juicer));
        juicer.click();
    }
}
