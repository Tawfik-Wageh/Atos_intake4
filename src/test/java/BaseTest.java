import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import utiles.DriverMange.DriverManager;

public class BaseTest {

    @BeforeTest
    public void Init() {
        DriverManager.driverSetup();
    }

    @AfterClass
    public void closeDriver() {
        DriverManager.closeDriver();
    }
}
