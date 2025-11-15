package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.MyDriverListener;

import java.lang.reflect.Method;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp(Method method) {
        String testName = method.getName();
        Thread.currentThread().setName(testName);
        driver = Driver.getDriver();
        driver = new EventFiringDecorator(new MyDriverListener()).decorate(driver);
    }

    @AfterMethod
    public void tearDown() {
        Driver.quitDriver();
    }
}
