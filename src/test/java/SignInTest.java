import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SigninPage;
import utiles.ExtentReports.ExtentReportListener;

@Listeners(ExtentReportListener.class)
public class SignInTest extends BaseTest {
    HomePage Home;
    SigninPage SigninPage;

    @BeforeTest
    public void setUp() {
        Home = new HomePage();
        SigninPage = new SigninPage();
    }

    @Test
    public void SignIn() {
        Home.clickOnSignInUpLink();
        SigninPage.EnterLoginEmail().enterLoginPassword().clickOnLoginBtn();
    }
}
