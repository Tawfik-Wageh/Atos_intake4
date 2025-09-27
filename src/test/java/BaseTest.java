import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utiles.DriverMange.DriverManager;

public class BaseTest {

    @BeforeTest
    public void Init() {
        DriverManager.driverSetup();
    }

    @AfterTest
    public void closeDriver() {
        DriverManager.closeDriver();
    }
}
