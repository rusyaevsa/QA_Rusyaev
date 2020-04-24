package qa_test;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class Autorization {
    private WebDriver driver;

    Autorization(WebDriver driver){ this.driver = driver; }

    @Step("Ввод номера телефона")
    void inputPhoneInForm(String phone){
        WebElement inputPhone = driver.findElement(By.cssSelector("[name='phone']"));
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.visibilityOf(inputPhone));
        inputPhone.sendKeys(phone);
        inputPhone.sendKeys(Keys.ENTER);
    }

    @Step("Ожидание ввода кода из SMS")
    void waitInputCode(){
        WebElement phoneForm = driver.findElement(By.xpath("//div[text()[contains(., 'Введите код')]]"));
        (new WebDriverWait(driver, 60)).until(ExpectedConditions.invisibilityOf(phoneForm));
        driver.navigate().refresh();
    }
}
