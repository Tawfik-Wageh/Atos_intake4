package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utiles.DriverMange.DriverManager;
import utiles.commonHelper.ElementHelper;

public class ProductDetailsPage {
    WebDriver driver;

    // Locators
    By Name = By.cssSelector(".product-information h2");
    By Category = By.xpath("//div[@class='product-information']/p[b[text()='Category:']]");
    By Price = By.cssSelector(".product-information span span");
    By Description = By.xpath("//div[@class='product-information']/p[1]");
    By QuantityInput = By.id("quantity");
    By AddToCartButton = By.cssSelector("button.cart");
    By AddedSuccessModal = By.id("cartModal");
    By ViewCartButton = By.xpath("//u[text()='View Cart']");


    public ProductDetailsPage() {
        this.driver = DriverManager.getDriver();
    }

    public String getName() {
        return ElementHelper.getText(driver, Name);
    }

    public String getCategory() {
        return ElementHelper.getText(driver, Category);
    }

    public String getPrice() {
        return ElementHelper.getText(driver, Price);
    }

    public String getDescription() {
        return ElementHelper.getText(driver, Description);
    }

    public void setQuantity(int qty) {
        ElementHelper.clearInput(driver, QuantityInput);
        ElementHelper.sendText(driver, QuantityInput, String.valueOf(qty));
    }

    public void addToCart() {
        ElementHelper.click(driver, AddToCartButton);
        ElementHelper.waitForVisibility(driver, AddedSuccessModal);
        ElementHelper.waitForDocumentReady(driver);
    }

    public void viewCartFromModal() {
        ElementHelper.click(driver, ViewCartButton);
        ElementHelper.waitForDocumentReady(driver);
    }
}



