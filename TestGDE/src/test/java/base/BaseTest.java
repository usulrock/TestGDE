package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utils.MyDriverListener;

import java.lang.reflect.Method;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(Method method, String browser) {
        String testName = method.getName();
        Thread.currentThread().setName(testName + "-" + browser);
        driver = Driver.getDriver(browser);
        driver = new EventFiringDecorator(new MyDriverListener()).decorate(driver);
    }

    @AfterMethod
    public void tearDown() {
        Driver.quitDriver();
    }
}
