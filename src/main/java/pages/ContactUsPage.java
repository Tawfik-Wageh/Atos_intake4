package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utiles.DriverMange.DriverManager;
import utiles.commonHelper.ElementHelper;

public class ContactUsPage {
    WebDriver driver;

    // Locators
    By GetInTouchHeader = By.xpath("//h2[text()='Get In Touch']");
    By Name = By.name("name");
    By Email = By.name("email");
    By Subject = By.name("subject");
    By Message = By.id("message");
    By SubmitButton = By.name("submit");
    By SuccessAlert = By.xpath("//div[contains(@class,'status') and contains(text(),'successfully')] | //div[contains(@class,'alert-success')]");

    public ContactUsPage() {
        this.driver = DriverManager.getDriver();
    }

    public boolean isLoaded() {
        return ElementHelper.isElementDisplayed(driver, GetInTouchHeader);
    }

    public void fillForm(String name, String email, String subject, String message) {
        ElementHelper.sendText(driver, Name, name);
        ElementHelper.sendText(driver, Email, email);
        ElementHelper.sendText(driver, Subject, subject);
        ElementHelper.sendText(driver, Message, message);
    }

    public void submit() {
        ElementHelper.click(driver, SubmitButton);

        try {
            driver.switchTo().alert().accept();
        } catch (Exception ignored) {
        }
        ElementHelper.waitForDocumentReady(driver);
    }

    public boolean isSuccessVisible() {
        return ElementHelper.isElementDisplayed(driver, SuccessAlert);
    }
}



