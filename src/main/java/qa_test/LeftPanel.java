package qa_test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LeftPanel {
    private WebDriver driver;

    public LeftPanel(WebDriver driver){ this.driver = driver; }

    public void chooseJuiser(){
        //WebElement showAll = driver.findElement(By.xpath("//div[text()[contains(., 'Техника для кухни')]]"));
        WebElement showAll = driver.findElement(By.cssSelector("[href='/category/tehnika-dlya-kuhni-10523/']"));
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.visibilityOf(showAll));
        showAll.click();
        PublicMethods.closeCookie(driver);
        WebElement juicer = driver.findElement(By.cssSelector("[href='/category/sokovyzhimalki-10592/']"));
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.visibilityOf(juicer));
        juicer.click();
    }
}
