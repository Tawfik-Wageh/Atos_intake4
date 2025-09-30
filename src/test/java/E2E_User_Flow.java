import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import utiles.DriverMange.DriverManager;
import utiles.ExtentReports.ExtentReportListener;
import utiles.commonHelper.AssertionHelper;
import utiles.commonHelper.LogsUtil;
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
        LogsUtil.info("Starting setup for test method");
        home = new HomePage();
        signin = new SigninPage();
        signup = new SignupPage();
        products = new ProductsPage();
        productDetails = new ProductDetailsPage();
        cart = new CartPage();
        checkout = new CheckoutPage();
        payment = new PaymentPage();
        contact = new ContactUsPage();
        LogsUtil.debug("Page objects initialized");

        // Read signup data from JSON
        Iterator<Object[]> dataIterator = DataProviderUtils.getData("src/test/resources/SignupTestData.json");
        String Name = null, Gender = null, Day = null, Month = null, Year = null, FirstName = null, LastName = null, Address = null, Country = null, State = null, City = null, ZipCode = null, MobileNumber = null, password = null, Email = null;
        if (dataIterator.hasNext()) {
            Object[] dataArr = dataIterator.next();
            Name = (String) dataArr[0];
            Gender = (String) dataArr[1];
            password = (String) dataArr[2];
            Day = (String) dataArr[3];
            Month = (String) dataArr[4];
            Year = (String) dataArr[5];
            FirstName = (String) dataArr[6];
            LastName = (String) dataArr[7];
            Address = (String) dataArr[8];
            Country = (String) dataArr[9];
            State = (String) dataArr[10];
            City = (String) dataArr[11];
            ZipCode = (String) dataArr[12];
            MobileNumber = (String) dataArr[13];
            LogsUtil.debug("Signup data loaded from JSON");
        }
        testEmail = null;
        testPassword = password;

        if (DriverManager.getDriver().findElements(By.linkText("Logout")).size() > 0) {
            LogsUtil.info("Logging out before test");
            home.logout();
        }

        LogsUtil.info("Signing up and logging in");
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
        LogsUtil.info("Setup complete");
    }

    @Description("First Test Case")
    @Test(groups = {"browse", "smoke"})
    public void testBrowseProducts() {
        LogsUtil.info("Starting testBrowseProducts");
        home.goToProducts();
        AssertionHelper.assertTrue(products.isLoaded(), "Products page did not load");
        products.searchForProduct("T-shirt");
        AssertionHelper.assertTrue(products.getSearchResultsCount() > 0, "No products returned for search query");
        products.openFirstProductDetails();
        AssertionHelper.assertTrue(productDetails.getName() != null && !productDetails.getName().isEmpty(), "Product name is empty");
        AssertionHelper.assertTrue(productDetails.getPrice() != null && !productDetails.getPrice().isEmpty(), "Product price is empty");
        AssertionHelper.assertTrue(productDetails.getDescription() != null && !productDetails.getDescription().isEmpty(), "Product description is empty");
        LogsUtil.info("testBrowseProducts completed");
    }

    @Description("Second Test Case")
    @Test(groups = {"cart", "smoke"})
    public void testAddToCart() {
        LogsUtil.info("Starting testAddToCart");
        home.goToProducts();
        products.openProductDetailsAt(0);
        productDetails.setQuantity(2);
        productDetails.addToCart();
        LogsUtil.debug("Added first product to cart with quantity 2");
        productDetails.viewCartFromModal();
        home.goToProducts();
        products.openProductDetailsAt(1);
        productDetails.setQuantity(1);
        productDetails.addToCart();
        LogsUtil.debug("Added second product to cart with quantity 1");
        productDetails.viewCartFromModal();
        AssertionHelper.assertTrue(cart.getItemsCount() >= 2, "Cart does not contain two items");
        AssertionHelper.assertEqual(cart.getProductQuantityAt(0).trim(), "2");
        AssertionHelper.assertEqual(cart.getProductQuantityAt(1).trim(), "1");
        LogsUtil.info("testAddToCart completed");
    }

    @Description("Third Test Case")
    @Test(groups = {"checkout", "smoke"})
    public void testCheckoutAndPlaceOrder() throws IOException {
        LogsUtil.info("Starting testCheckoutAndPlaceOrder");
        home.goToProducts();
        products.openProductDetailsAt(0);
        productDetails.setQuantity(2);
        productDetails.addToCart();
        LogsUtil.debug("Added first product to cart for checkout");
        productDetails.viewCartFromModal();
        home.goToProducts();
        products.openProductDetailsAt(1);
        productDetails.setQuantity(1);
        productDetails.addToCart();
        LogsUtil.debug("Added second product to cart for checkout");
        productDetails.viewCartFromModal();
        cart.proceedToCheckout();
        AssertionHelper.assertTrue(checkout.isAddressVisible(), "Address section not visible at checkout");
        AssertionHelper.assertTrue(checkout.isOrderReviewVisible(), "Order review not visible at checkout");

        // Read Checkout.json using DataProviderUtils and JSONReaderUtil
        LogsUtil.info("Reading checkout and payment data from Checkout.json");
        Iterator<Object[]> dataIterator = utiles.datareaders.DataProviderUtils.getData("src/test/resources/Checkout.json");
        String orderComment = "";
        String name = "", cardNumber = "", cvc = "", expiryMonth = "", expiryYear = "";
        if (dataIterator.hasNext()) {
            Object[] dataArr = dataIterator.next();
            orderComment = (String) dataArr[0];
            name = (String) dataArr[1];
            cardNumber = (String) dataArr[2];
            cvc = (String) dataArr[3];
            expiryMonth = (String) dataArr[4];
            expiryYear = (String) dataArr[5];
            LogsUtil.debug("Order comment: " + orderComment);
            LogsUtil.debug("Payment details: name=" + name + ", cardNumber=" + cardNumber + ", cvc=" + cvc + ", expiryMonth=" + expiryMonth + ", expiryYear=" + expiryYear);
        }

        checkout.addOrderComment(orderComment);
        LogsUtil.info("Order comment added");
        checkout.placeOrder();
        LogsUtil.info("Order placed, entering payment details");
        payment.enterPaymentDetails(name, cardNumber, cvc, expiryMonth, expiryYear);
        payment.confirmPayment();
        LogsUtil.info("Payment confirmed");
        AssertionHelper.assertTrue(payment.waitUntilOrderSuccess(), "Order success message not visible after payment");
        LogsUtil.info("testCheckoutAndPlaceOrder completed");
    }

    @Description("Fourth Test Case")
    @Test(groups = {"contact", "smoke"})
    public void testContactUs() throws IOException {
        LogsUtil.info("Starting testContactUs");
        home.goToContactUs();
        AssertionHelper.assertTrue(contact.isLoaded(), "Contact Us page not loaded");


        // Read Contact.json using DataProviderUtils and JSONReaderUtil
        Iterator<Object[]> dataIterator = utiles.datareaders.DataProviderUtils.getData("src/test/resources/Contact.json");
        String name = "", email = "", subject = "", message = "";
        if (dataIterator.hasNext()) {
            Object[] dataArr = dataIterator.next();
            name = (String) dataArr[0];
            email = (String) dataArr[1];
            subject = (String) dataArr[2];
            message = (String) dataArr[3];
            LogsUtil.debug("Contact form data: name=" + name + ", email=" + email + ", subject=" + subject + ", message=" + message);
        }


        contact.fillForm(name, email, subject, message);
        contact.submit();
        AssertionHelper.assertTrue(contact.isSuccessVisible(), "Contact Us success confirmation not shown");
        LogsUtil.info("testContactUs completed");
    }


    @Description("Fifth Test Case")
    @Test(groups = {"logout", "smoke"})
    public void testLogoutAndSessionValidation() {
        LogsUtil.info("Starting testLogoutAndSessionValidation");
        home.logout();
        home.clickOnSignInUpLink();
        AssertionHelper.assertUrl(DriverManager.getDriver(), LoadProperties.URL + "login");
        LogsUtil.info("testLogoutAndSessionValidation completed");
    }

    @Description("User_Flow Test Case")
    @Test(groups = {"e2e", "regression"})
    public void endToEndFlow() throws IOException {
        LogsUtil.info("Starting endToEndFlow");
        testBrowseProducts();
        testAddToCart();
        testCheckoutAndPlaceOrder();
        testContactUs();
        testLogoutAndSessionValidation();
        LogsUtil.info("endToEndFlow completed");
    }
}
