package qa_test;

import org.testng.annotations.Test;

public class Test3 extends TestBefore {
    @Test
    public void catalogTest() {
        ItemScope itemScope = new ItemScope(driver);
        itemScope.clickButtonCatalog();
        itemScope.chooseJuicer();
        LeftPanel leftPanel = new LeftPanel(driver);
        leftPanel.chooseJuiser();
        ProductPage productPage = new ProductPage(driver);
        productPage.inputPrice(3000, 4000);
        productPage.checkJuicersPrices(3000, 4000);
        productPage.sortPrice();
        productPage.addIntoBasket();
        itemScope.goToBasket();
        Basket basket = new Basket(driver);
        int countInBasket = basket.addCountGood(5);
        basket.checkPrice(countInBasket);
    }
}
