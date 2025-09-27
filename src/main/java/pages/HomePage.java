package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utiles.DriverMange.DriverManager;
import utiles.commonHelper.ElementHelper;
import utiles.config.LoadProperties;

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
        driver.get(LoadProperties.URL + "products");
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
