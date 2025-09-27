package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utiles.DriverMange.DriverManager;
import utiles.commonHelper.ElementHelper;

import java.util.List;

public class CartPage {
    WebDriver driver;

    // Locators
    By CartTableRows = By.cssSelector("#cart_info_table tbody tr");
    By ProductNames = By.cssSelector("#cart_info_table tbody tr .cart_description a");
    By ProductPrices = By.cssSelector("#cart_info_table tbody tr .cart_price p");
    By ProductQuantities = By.cssSelector("#cart_info_table tbody tr .cart_quantity button");
    By ProductTotals = By.cssSelector("#cart_info_table tbody tr .cart_total p.cart_total_price");
    By ProceedToCheckoutButton = By.cssSelector(".check_out");
    By CartBadge = By.cssSelector(".shop-menu .badges");

    public CartPage() {
        this.driver = DriverManager.getDriver();
    }

    public int getItemsCount() {
        List<WebElement> rows = ElementHelper.getElements(driver, CartTableRows);
        return rows.size();
    }

    public String getProductNameAt(int index) {
        return ElementHelper.getElements(driver, ProductNames).get(index).getText();
    }

    public String getProductPriceAt(int index) {
        return ElementHelper.getElements(driver, ProductPrices).get(index).getText();
    }

    public String getProductQuantityAt(int index) {
        return ElementHelper.getElements(driver, ProductQuantities).get(index).getText();
    }

    public String getProductTotalAt(int index) {
        return ElementHelper.getElements(driver, ProductTotals).get(index).getText();
    }

    public void proceedToCheckout() {
        ElementHelper.click(driver, ProceedToCheckoutButton);
        ElementHelper.waitForDocumentReady(driver);
    }

    public String getCartBadgeText() {
        return ElementHelper.isElementDisplayed(driver, CartBadge) ? ElementHelper.getText(driver, CartBadge) : "";
    }
}



