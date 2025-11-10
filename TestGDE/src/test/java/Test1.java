import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Test1 {


    WebDriver driver;

    @BeforeClass
    public void setup() {
        // ChromeDriver elérési út
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\riotvos\\OneDrive - GoTo Technologies USA, Inc\\Desktop\\WebDrivers\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void loginTest() {
        driver.get("https://practice.expandtesting.com/");
        WebElement loginButton = driver.findElement(By.id("loginButton"));
        loginButton.click();

        // Példa ellenőrzés
        WebElement message = driver.findElement(By.id("loginMessage"));
        Assert.assertTrue(message.isDisplayed());
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

