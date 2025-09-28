package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utiles.DriverMange.DriverManager;
import utiles.commonHelper.ElementHelper;

public class HomePage {
    WebDriver driver;

    // Locators
    By SignInUp = By.linkText("Signup / Login");
    By ProductsLink = By.linkText("Products");
    By CartLink = By.linkText("Cart");
    By ContactUsLink = By.linkText("Contact us");
    By LogoutLink = By.linkText("Logout");

    public HomePage() {
        this.driver = DriverManager.getDriver();

    }

    public void clickOnSignInUpLink() {
        ElementHelper.click(driver, SignInUp);
    }

    public void goToProducts() {
        By productsLocator = By.partialLinkText("Products");
        utiles.commonHelper.WaitHelper.waitForClickable(driver, productsLocator); // Wait for Products link to be clickable
        org.openqa.selenium.WebElement productsElement = driver.findElement(productsLocator);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productsElement);
        productsElement.click();
        utiles.commonHelper.WaitHelper.waitForPageToLoad(driver, "/products");
    }

    public void goToCart() {
        ElementHelper.click(driver, CartLink);
    }

    public void goToContactUs() {
        ElementHelper.click(driver, ContactUsLink);
        utiles.commonHelper.WaitHelper.waitForPageToLoad(driver, null);
    }

    public void logout() {
        ElementHelper.click(driver, LogoutLink);
        utiles.commonHelper.WaitHelper.waitForPageToLoad(driver, null);
    }
}
