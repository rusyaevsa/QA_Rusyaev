package qa_test;

import org.testng.annotations.Test;

public class Test2 extends TestBefore{
    @Test
    public void changeCityTest() throws InterruptedException {
        String city = "Вольск", phone = "9173004375";
        Navigation navigation = new Navigation(driver);
        navigation.clickCity();
        CityChange cityChange = new CityChange(driver);
        cityChange.inputCity(city);
        navigation.checkCity(city);
        ItemScope itemScope = new ItemScope(driver);
        itemScope.clickButtonLogin();
        Autorization autorization = new Autorization(driver);
        autorization.inputPhoneInForm(phone);
        autorization.waitInputCode();
        // Тест с сравнением города
        itemScope.clickButtonProfile();
        navigation.checkCity(city);
    }
}
