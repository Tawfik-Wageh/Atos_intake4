package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utiles.DriverMange.DriverManager;
import utiles.commonHelper.ElementHelper;
import utiles.commonHelper.Global;

public class SignupPage {
    WebDriver driver;
    Global global;

    // Locators
    By Name = By.name("name");
    By Email = By.xpath("//input[@data-qa='signup-email']");
    By SignupBtn = By.xpath("//button[text()='Signup']");
    By MrsGender = By.id("uniform-id_gender2");
    By Password = By.id("password");
    By DaysDropdown = By.id("days");
    By MonthDropdown = By.id("months");
    By YearsDropdown = By.id("years");
    By Newsletter = By.xpath("//label[text()='Sign up for our newsletter!']");
    By SpecialOffer = By.xpath("//label[text()='Receive special offers from our partners!']");
    By First_name = By.id("first_name");
    By Last_name = By.id("last_name");
    By Address = By.id("address1");
    By CountryDropdown = By.id("country");
    By State = By.id("state");
    By City = By.id("city");
    By Zipcode = By.id("zipcode");
    By Mobile_number = By.id("mobile_number");
    By Create_Account = By.xpath("//button[text()='Create Account']");
    By AccountCreatedMessage = By.xpath("//h2[@data-qa='account-created']");
    By LogOutBtn = By.linkText("Logout");

    public SignupPage() {
        this.driver = DriverManager.getDriver();
        global = Global.getInstance();
    }

    public SignupPage enterName(String name) {
        ElementHelper.sendText(driver, Name, name);
        return this;
    }

    public SignupPage EnterEmail() {
        global.setEmail();
        ElementHelper.sendText(driver, Email, global.getEmail());
        return this;
    }

    public SignupPage clickOnSignupBtn() {
        ElementHelper.click(driver, SignupBtn);
        return this;
    }

    public SignupPage chooseGender(String text) {
        ElementHelper.findElementByValue(text, driver).click();
        return this;
    }

    public SignupPage enterPassword() {
        global.setPassword();
        ElementHelper.sendText(driver, Password, global.getPassword());
        return this;
    }

    public SignupPage chooseDay(String day) {
        ElementHelper.selectFromDropDownByText(driver, DaysDropdown, day);
        return this;
    }

    public SignupPage chooseMonth(String month) {
        ElementHelper.selectFromDropDownByText(driver, MonthDropdown, month);
        return this;
    }

    public SignupPage chooseYear(String year) {
        ElementHelper.selectFromDropDownByText(driver, YearsDropdown, year);
        return this;
    }

    public SignupPage checkOnNewsletter() {
        ElementHelper.click(driver, Newsletter);
        return this;
    }

    public SignupPage enterFirstName(String name) {
        ElementHelper.sendText(driver, First_name, name);
        return this;
    }

    public SignupPage enterLastName(String name) {
        ElementHelper.sendText(driver, Last_name, name);
        return this;
    }

    public SignupPage enterAddress(String address) {
        ElementHelper.sendText(driver, Address, address);
        return this;
    }

    public SignupPage chooseCountry(String country) {
        ElementHelper.selectFromDropDownByText(driver, CountryDropdown, country);
        return this;
    }

    public SignupPage enterCity(String city) {
        ElementHelper.sendText(driver, City, city);
        return this;
    }

    public SignupPage enterState(String state) {
        ElementHelper.sendText(driver, State, state);
        return this;
    }

    public SignupPage enterZipCode(String code) {
        ElementHelper.sendText(driver, Zipcode, code);
        return this;
    }

    public SignupPage enterMobileNumber(String number) {
        ElementHelper.sendText(driver, Mobile_number, number);
        return this;
    }

    public SignupPage clickOnCreateAccountBtn() {
        ElementHelper.click(driver, Create_Account);
        return this;
    }

    public SignupPage clickOnLogOnBtn() {
        ElementHelper.click(driver, LogOutBtn);
        return this;
    }

}
