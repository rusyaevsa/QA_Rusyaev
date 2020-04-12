package qa_test;

import org.testng.annotations.Test;

public class Test1 extends TestBefore {
    @Test
    public void loginTest() {
        ItemScope itemScope = new ItemScope(driver);
        itemScope.clickButtonLogin();
        String phone = "9173004375";
        Autorization autorization = new Autorization(driver);
        autorization.inputPhoneInForm(phone);
        autorization.waitInputCode();
        itemScope.checkProfile();
    }
}
