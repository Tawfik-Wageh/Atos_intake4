import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import utiles.DriverMange.DriverManager;
import utiles.ExtentReports.ExtentReportListener;
import utiles.commonHelper.AssertionHelper;
import utiles.config.LoadProperties;

@Listeners(ExtentReportListener.class)
public class E2E_User_Flow extends BaseTest {

    // First Test Case
    @Test(groups = {"browse", "smoke"})
    public void testBrowseProducts() {
        HomePage home = new HomePage();
        SigninPage signin = new SigninPage();
        SignupPage signup = new SignupPage();
        ProductsPage products = new ProductsPage();
        ProductDetailsPage productDetails = new ProductDetailsPage();

        // Create account and login
        createAccountAndLogin(home, signup, signin);

        // Browse Products
        home.goToProducts();
        AssertionHelper.assertTrue(products.isLoaded(), "Products page did not load");
        int allCount = products.getVisibleProductsCount();
        AssertionHelper.assertTrue(allCount > 0, "No products visible on All Products page");

        // Search and open details
        products.searchForProduct("T-shirt");
        int searchCount = products.getSearchResultsCount();
        AssertionHelper.assertTrue(searchCount > 0, "No products returned for search query");
        products.openFirstProductDetails();

        // Validate product details
        String nameA = productDetails.getName();
        String priceA = productDetails.getPrice();
        String descA = productDetails.getDescription();
        AssertionHelper.assertTrue(nameA != null && !nameA.isEmpty(), "Product name is empty");
        AssertionHelper.assertTrue(priceA != null && !priceA.isEmpty(), "Product price is empty");
        AssertionHelper.assertTrue(descA != null && !descA.isEmpty(), "Product description is empty");
    }

    // Second Test Case
    @Test(groups = {"cart", "smoke"})
    public void testAddToCart() {
        HomePage home = new HomePage();
        SigninPage signin = new SigninPage();
        SignupPage signup = new SignupPage();
        ProductsPage products = new ProductsPage();
        ProductDetailsPage productDetails = new ProductDetailsPage();
        CartPage cart = new CartPage();

        // Create account and login
        createAccountAndLogin(home, signup, signin);

        // Add Product A qty 2
        home.goToProducts();
        products.openFirstProductDetails();
        productDetails.setQuantity(2);
        productDetails.addToCart();
        productDetails.viewCartFromModal();

        // Go back to products and add a second product (Product B)
        home.goToProducts();
        products.openFirstProductDetails();
        productDetails.setQuantity(1);
        productDetails.addToCart();
        productDetails.viewCartFromModal();

        // Cart assertions
        int items = cart.getItemsCount();
        AssertionHelper.assertTrue(items >= 2, "Cart does not contain two items");

        // Verify quantities and names are present
        String firstQty = cart.getProductQuantityAt(0);
        String secondQty = cart.getProductQuantityAt(1);
        AssertionHelper.assertEqual(firstQty.trim(), "2");
        AssertionHelper.assertEqual(secondQty.trim(), "1");
        AssertionHelper.assertTrue(!cart.getProductNameAt(0).isEmpty(), "First product name is empty");
        AssertionHelper.assertTrue(!cart.getProductNameAt(1).isEmpty(), "Second product name is empty");
        AssertionHelper.assertTrue(!cart.getProductPriceAt(0).isEmpty(), "First product price is empty");
        AssertionHelper.assertTrue(!cart.getProductPriceAt(1).isEmpty(), "Second product price is empty");
        AssertionHelper.assertTrue(!cart.getProductTotalAt(0).isEmpty(), "First product total is empty");
        AssertionHelper.assertTrue(!cart.getProductTotalAt(1).isEmpty(), "Second product total is empty");
    }

    // Third Test Case
    @Test(groups = {"checkout", "smoke"})
    public void testCheckoutAndPlaceOrder() {
        HomePage home = new HomePage();
        SigninPage signin = new SigninPage();
        SignupPage signup = new SignupPage();
        ProductsPage products = new ProductsPage();
        ProductDetailsPage productDetails = new ProductDetailsPage();
        CartPage cart = new CartPage();
        CheckoutPage checkout = new CheckoutPage();
        PaymentPage payment = new PaymentPage();

        // Create account and login
        createAccountAndLogin(home, signup, signin);

        // Add products to cart
        addProductsToCart(home, products, productDetails, cart);

        // Proceed to checkout
        cart.proceedToCheckout();
        AssertionHelper.assertTrue(checkout.isAddressVisible(), "Address section not visible at checkout");
        AssertionHelper.assertTrue(checkout.isOrderReviewVisible(), "Order review not visible at checkout");
        checkout.addOrderComment("Please deliver between 9 AM - 5 PM.");
        checkout.placeOrder();

        // Payment
        payment.enterPaymentDetails("Test User", "4242424242424242", "123", "12", "2028");
        payment.confirmPayment();
        AssertionHelper.assertTrue(payment.waitUntilOrderSuccess(), "Order success message not visible after payment");
    }

    // Fourth Test Case
    @Test(groups = {"contact", "smoke"})
    public void testContactUs() {
        HomePage home = new HomePage();
        SigninPage signin = new SigninPage();
        SignupPage signup = new SignupPage();
        ContactUsPage contact = new ContactUsPage();

        // Create account and login
        createAccountAndLogin(home, signup, signin);

        // Contact Us
        home.goToContactUs();
        AssertionHelper.assertTrue(contact.isLoaded(), "Contact Us page not loaded");
        contact.fillForm("Test User", "test@example.com", "Order Support", "Need help with my recent order.");
        contact.submit();
        AssertionHelper.assertTrue(contact.isSuccessVisible(), "Contact Us success confirmation not shown");
    }

    // Fifth Test Case
    @Test(groups = {"logout", "smoke"})
    public void testLogoutAndSessionValidation() {
        HomePage home = new HomePage();
        SigninPage signin = new SigninPage();
        SignupPage signup = new SignupPage();

        // Create account and login
        createAccountAndLogin(home, signup, signin);

        // Logout & session validation
        home.logout();
        home.clickOnSignInUpLink();
        AssertionHelper.assertUrl(DriverManager.getDriver(), LoadProperties.URL + "login");
    }

    // End-to-End Test Case
    @Test(groups = {"e2e", "regression"})
    public void endToEndFlow() {
        testBrowseProducts();
        testAddToCart();
        testCheckoutAndPlaceOrder();
        testContactUs();
        testLogoutAndSessionValidation();
    }

    // Helper methods to reduce redundancy
    private void createAccountAndLogin(HomePage home, SignupPage signup, SigninPage signin) {
        home.clickOnSignInUpLink();
        signup.enterName("Test User").EnterEmail().clickOnSignupBtn()
                .chooseGender("Mrs")
                .enterPassword()
                .chooseDay("10").chooseMonth("May").chooseYear("1995")
                .enterFirstName("Test").enterLastName("User")
                .enterAddress("Test Street 123").chooseCountry("Canada").enterState("ON").enterCity("Toronto")
                .enterZipCode("A1A1A1").enterMobileNumber("1234567890")
                .clickOnCreateAccountBtn();
        home.clickOnSignInUpLink();
        signup.clickOnLogOnBtn();

        // Login with the just-created credentials
        home.clickOnSignInUpLink();
        signin.EnterLoginEmail().enterLoginPassword().clickOnLoginBtn();
    }

    private void addProductsToCart(HomePage home, ProductsPage products, ProductDetailsPage productDetails, CartPage cart) {
        // Add Product A qty 2
        home.goToProducts();
        products.openFirstProductDetails();
        productDetails.setQuantity(2);
        productDetails.addToCart();
        productDetails.viewCartFromModal();

        // Go back to products and add a second product (Product B)
        home.goToProducts();
        products.openFirstProductDetails();
        productDetails.setQuantity(1);
        productDetails.addToCart();
        productDetails.viewCartFromModal();
    }
}


