import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SignupPage;
import utiles.ExtentReports.ExtentReportListener;
import utiles.datareaders.DataProviderUtils;

import java.io.IOException;
import java.util.Iterator;

@Listeners(ExtentReportListener.class)
public class SignUpTest extends BaseTest {
    HomePage Home;
    SignupPage signupPage;

    @BeforeTest
    public void setup() {
        Home = new HomePage();
        signupPage = new SignupPage();
    }

    @DataProvider
    public Iterator<Object[]> getData() throws IOException {
        return DataProviderUtils.getData("src/test/resources/SignupTestData.json");
    }

    @Test(testName = "Signup", groups = "regression", dataProvider = "getData")
    public void signupHappyPathFlow(String Name, String Gender, String Day, String Month, String Year,
                                    String FirstName, String Last,
                                    String Address, String Country, String State, String City, String ZipCode, String Mobile) {
        Home.clickOnSignInUpLink();
        signupPage.enterName(Name).EnterEmail().clickOnSignupBtn()
                .chooseGender(Gender)
                .enterPassword()
                .chooseDay(Day).chooseMonth(Month).chooseYear(Year)
                .checkOnNewsletter()
                .enterFirstName(FirstName).enterLastName(Last)
                .enterAddress(Address).chooseCountry(Country).enterState(State).enterCity(City)
                .enterZipCode(ZipCode).enterMobileNumber(Mobile)
                .clickOnCreateAccountBtn();
        Home.clickOnSignInUpLink();
        signupPage.clickOnLogOnBtn();
    }

}
