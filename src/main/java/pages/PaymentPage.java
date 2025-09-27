package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utiles.DriverMange.DriverManager;
import utiles.commonHelper.ElementHelper;

public class PaymentPage {
    private static final String SUCCESS_XPATH = "//p[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'order placed successfully')] | "
            + "//p[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'your order has been placed successfully')] | "
            + "//div[contains(@class,'alert') and contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'successfully')] | "
            + "//h2[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'order placed')]";
    WebDriver driver;
    
    // Locators
    By NameOnCard = By.name("name_on_card");
    By CardNumber = By.name("card_number");
    By CVC = By.name("cvc");
    By ExpMonth = By.name("expiry_month");
    By ExpYear = By.name("expiry_year");
    By PayAndConfirmButton = By.id("submit");
    By SuccessMessage = By.xpath(SUCCESS_XPATH);


    public PaymentPage() {
        this.driver = DriverManager.getDriver();
    }

    public void enterPaymentDetails(String name, String number, String cvc, String month, String year) {
        ElementHelper.sendText(driver, NameOnCard, name);
        ElementHelper.sendText(driver, CardNumber, number);
        ElementHelper.sendText(driver, CVC, cvc);
        ElementHelper.sendText(driver, ExpMonth, month);
        ElementHelper.sendText(driver, ExpYear, year);
    }

    public void confirmPayment() {
        ElementHelper.click(driver, PayAndConfirmButton);
        ElementHelper.waitForDocumentReady(driver);
    }

    public boolean isOrderSuccessVisible() {
        return ElementHelper.isElementDisplayed(driver, SuccessMessage);
    }

    public boolean waitUntilOrderSuccess() {
        long end = System.currentTimeMillis() + 20000; // 20s soft wait
        while (System.currentTimeMillis() < end) {
            try {
                if (isOrderSuccessVisible()) return true;
                if (ElementHelper.getCurrentUrl(driver).toLowerCase().contains("payment")) return true;
            } catch (Exception ignored) {
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
        }
        return isOrderSuccessVisible();
    }
}



