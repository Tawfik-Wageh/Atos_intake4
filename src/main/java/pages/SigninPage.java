package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utiles.DriverMange.DriverManager;
import utiles.commonHelper.ElementHelper;
import utiles.commonHelper.Global;
import utiles.config.LoadProperties;

// WE use fluent pattern (Method chainning )
public class SigninPage {
    WebDriver driver;
    Global global;

    // Locators
    By LoginEmail = By.xpath("//input[@data-qa='login-email']");
    By LoginPassword = By.xpath("//input[@data-qa='login-password']");
    By LoginButton = By.xpath("//button[@data-qa='login-button']");


    public SigninPage() {
        this.driver = DriverManager.getDriver();
        global = Global.getInstance();
    }

    public SigninPage EnterLoginEmail() {
        String email = global.getEmail();
        if (email == null || email.isEmpty()) {
            email = LoadProperties.getProperty("LoginEmail");
        }
        if (email == null) {
            email = "";
        }
        ElementHelper.sendText(driver, LoginEmail, email);
        return this;
    }

    public SigninPage enterLoginPassword() {
        String password = global.getPassword();
        if (password == null || password.isEmpty()) {
            password = LoadProperties.getProperty("LoginPassword");
        }
        if (password == null) {
            password = "";
        }
        ElementHelper.sendText(driver, LoginPassword, password);
        return this;
    }

    public SigninPage clickOnLoginBtn() {
        ElementHelper.click(driver, LoginButton);
        return this;
    }
}
