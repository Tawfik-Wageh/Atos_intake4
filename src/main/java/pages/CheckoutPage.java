package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utiles.DriverMange.DriverManager;
import utiles.commonHelper.ElementHelper;

public class CheckoutPage {
    WebDriver driver;

    // Locators
    By AddressHeader = By.xpath("//h2[text()='Address Details']");
    By OrderReviewHeader = By.xpath("//h2[text()='Review Your Order']");
    By CommentTextArea = By.name("message");
    By PlaceOrderButton = By.xpath("//a[text()='Place Order']");

    public CheckoutPage() {
        this.driver = DriverManager.getDriver();
    }

    public boolean isAddressVisible() {
        return ElementHelper.isElementDisplayed(driver, AddressHeader);
    }

    public boolean isOrderReviewVisible() {
        return ElementHelper.isElementDisplayed(driver, OrderReviewHeader);
    }

    public void addOrderComment(String comment) {
        ElementHelper.sendText(driver, CommentTextArea, comment);
    }

    public void placeOrder() {
        ElementHelper.click(driver, PlaceOrderButton);
        ElementHelper.waitForDocumentReady(driver);
    }
}



