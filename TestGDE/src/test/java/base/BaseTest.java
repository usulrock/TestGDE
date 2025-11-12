package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = Driver.getDriver();
    }

    @AfterMethod
    public void tearDown() {
        Driver.quitDriver();
    }
}
