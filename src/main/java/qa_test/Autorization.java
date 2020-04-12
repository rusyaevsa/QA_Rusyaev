package qa_test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class Autorization {
    private WebDriver driver;
    private WebElement agreeButton;
    private WebElement inputPhone;

    Autorization(WebDriver driver){ this.driver = driver; }

    void inputPhoneInForm(String phone){
        WebElement inputPhone = driver.findElement(By.cssSelector("[name='phone']"));
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.visibilityOf(inputPhone));
        inputPhone.sendKeys(phone);
        inputPhone.sendKeys(Keys.ENTER);
    }

    void waitInputCode(){
        WebElement phoneForm = driver.findElement(By.xpath("//div[text()[contains(., 'Введите код')]]"));
        (new WebDriverWait(driver, 60)).until(ExpectedConditions.invisibilityOf(phoneForm));
        driver.navigate().refresh();
    }
}
