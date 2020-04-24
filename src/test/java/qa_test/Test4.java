package qa_test;

import org.testng.annotations.Test;

public class Test4 extends TestBefore{
    @Test
    public void juicerPowerTest() {
        ItemScope itemScope = new ItemScope(driver);
        itemScope.clickButtonCatalog();
        itemScope.chooseAppliances();
        LeftPanel leftPanel = new LeftPanel(driver);
        leftPanel.chooseJuiser();
        ProductPage productPage = new ProductPage(driver);
        productPage.inputPower(1000);
        productPage.checkPower(1000);
        productPage.sortPrice();
        productPage.addIntoBasket();
        itemScope.goToBasket();
        Basket basket = new Basket(driver);
        int countInBasket = basket.addCountGood(5);
        basket.checkPrice(countInBasket);
    }
}
