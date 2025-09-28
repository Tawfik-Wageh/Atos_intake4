import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import utiles.DriverMange.DriverManager;
import utiles.ExtentReports.ExtentReportListener;
import utiles.commonHelper.AssertionHelper;
import utiles.config.LoadProperties;
import utiles.datareaders.DataProviderUtils;

import java.io.IOException;
import java.util.Iterator;

@Listeners(ExtentReportListener.class)
public class E2E_User_Flow extends BaseTest {
    String testEmail;
    String testPassword;
    HomePage home;
    SigninPage signin;
    SignupPage signup;
    ProductsPage products;
    ProductDetailsPage productDetails;
    CartPage cart;
    CheckoutPage checkout;
    PaymentPage payment;
    ContactUsPage contact;

    @BeforeMethod
    public void setUpMethod() throws IOException {
        home = new HomePage();
        signin = new SigninPage();
        signup = new SignupPage();
        products = new ProductsPage();
        productDetails = new ProductDetailsPage();
        cart = new CartPage();
        checkout = new CheckoutPage();
        payment = new PaymentPage();
        contact = new ContactUsPage();

        // Read signup data from JSON
        Iterator<Object[]> dataIterator = DataProviderUtils.getData("src/test/resources/SignupTestData.json");
        String Name = null, Gender = null, Day = null, Month = null, Year = null, FirstName = null, LastName = null, Address = null, Country = null, State = null, City = null, ZipCode = null, MobileNumber = null, password = null, Email = null;
        if (dataIterator.hasNext()) {
            Object[] dataArr = dataIterator.next();
            Name = (String) dataArr[0];        // Name
            Gender = (String) dataArr[1];      // Gender
            password = (String) dataArr[2];    // Password
            Day = (String) dataArr[3];         // Day
            Month = (String) dataArr[4];       // Month
            Year = (String) dataArr[5];        // Year
            FirstName = (String) dataArr[6];   // FirstName
            LastName = (String) dataArr[7];    // LastName
            Address = (String) dataArr[8];     // Address
            Country = (String) dataArr[9];     // Country
            State = (String) dataArr[10];      // State
            City = (String) dataArr[11];       // City
            ZipCode = (String) dataArr[12];    // ZipCode
            MobileNumber = (String) dataArr[13]; // MobileNumber
            // If you add Email to JSON, map it here
        }
        testEmail = null; // Email is not present in JSON, set to null or add to JSON
        testPassword = password;


        // Ensure logged out before starting
        if (DriverManager.getDriver().findElements(By.linkText("Logout")).size() > 0) {
            home.logout();
        }


        // Sign up and login ONCE
        home.clickOnSignInUpLink();
        utiles.commonHelper.WaitHelper.waitForPageToLoad(DriverManager.getDriver(), "/login");
        signup.enterName(Name).EnterEmail().clickOnSignupBtn()
                .chooseGender(Gender)
                .enterPassword().chooseDay(Day)
                .chooseMonth(Month)
                .chooseYear(Year)
                .enterFirstName(FirstName)
                .enterLastName(LastName)
                .enterAddress(Address)
                .chooseCountry(Country)
                .enterState(State)
                .enterCity(City)
                .enterZipCode(ZipCode)
                .enterMobileNumber(MobileNumber)
                .clickOnCreateAccountBtn();
        utiles.commonHelper.WaitHelper.waitForPageToLoad(DriverManager.getDriver(), null);
        home.clickOnSignInUpLink();
        signup.clickOnLogOnBtn();
        utiles.commonHelper.WaitHelper.waitForPageToLoad(DriverManager.getDriver(), "/login");
        home.clickOnSignInUpLink();
        signin.EnterLoginEmail().enterLoginPassword().clickOnLoginBtn();
        utiles.commonHelper.WaitHelper.waitForPageToLoad(DriverManager.getDriver(), null);
        utiles.commonHelper.WaitHelper.waitForClickable(DriverManager.getDriver(), By.partialLinkText("Products"));
    }


    @Test(groups = {"browse", "smoke"})
    public void testBrowseProducts() {
        home.goToProducts();
        AssertionHelper.assertTrue(products.isLoaded(), "Products page did not load");
        products.searchForProduct("T-shirt");
        AssertionHelper.assertTrue(products.getSearchResultsCount() > 0, "No products returned for search query");
        products.openFirstProductDetails();
        AssertionHelper.assertTrue(productDetails.getName() != null && !productDetails.getName().isEmpty(), "Product name is empty");
        AssertionHelper.assertTrue(productDetails.getPrice() != null && !productDetails.getPrice().isEmpty(), "Product price is empty");
        AssertionHelper.assertTrue(productDetails.getDescription() != null && !productDetails.getDescription().isEmpty(), "Product description is empty");
    }

    @Test(groups = {"cart", "smoke"})
    public void testAddToCart() {
        home.goToProducts();
        products.openProductDetailsAt(0);
        productDetails.setQuantity(2);
        productDetails.addToCart();
        productDetails.viewCartFromModal();
        home.goToProducts();
        products.openProductDetailsAt(1);
        productDetails.setQuantity(1);
        productDetails.addToCart();
        productDetails.viewCartFromModal();
        AssertionHelper.assertTrue(cart.getItemsCount() >= 2, "Cart does not contain two items");
        AssertionHelper.assertEqual(cart.getProductQuantityAt(0).trim(), "2");
        AssertionHelper.assertEqual(cart.getProductQuantityAt(1).trim(), "1");
    }

    @Test(groups = {"checkout", "smoke"})
    public void testCheckoutAndPlaceOrder() {
        home.goToProducts();
        products.openProductDetailsAt(0);
        productDetails.setQuantity(2);
        productDetails.addToCart();
        productDetails.viewCartFromModal();
        home.goToProducts();
        products.openProductDetailsAt(1);
        productDetails.setQuantity(1);
        productDetails.addToCart();
        productDetails.viewCartFromModal();
        cart.proceedToCheckout();
        AssertionHelper.assertTrue(checkout.isAddressVisible(), "Address section not visible at checkout");
        AssertionHelper.assertTrue(checkout.isOrderReviewVisible(), "Order review not visible at checkout");
        checkout.addOrderComment("Please deliver between 9 AM - 5 PM.");
        checkout.placeOrder();
        payment.enterPaymentDetails("Test User", "4242424242424242", "123", "12", "2028");
        payment.confirmPayment();
        AssertionHelper.assertTrue(payment.waitUntilOrderSuccess(), "Order success message not visible after payment");
    }

    @Test(groups = {"contact", "smoke"})
    public void testContactUs() {
        home.goToContactUs();
        AssertionHelper.assertTrue(contact.isLoaded(), "Contact Us page not loaded");
        contact.fillForm("Test User", "test@example.com", "Order Support", "Need help with my recent order.");
        contact.submit();
        AssertionHelper.assertTrue(contact.isSuccessVisible(), "Contact Us success confirmation not shown");
    }

    @Test(groups = {"logout", "smoke"})
    public void testLogoutAndSessionValidation() {
        home.logout();
        home.clickOnSignInUpLink();
        AssertionHelper.assertUrl(DriverManager.getDriver(), LoadProperties.URL + "login");
    }

    @Test(groups = {"e2e", "regression"})
    public void endToEndFlow() {
        testBrowseProducts();
        testAddToCart();
        testCheckoutAndPlaceOrder();
        testContactUs();
        testLogoutAndSessionValidation();
    }
}
