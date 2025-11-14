package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.Duration;

public class Driver {

    // ThreadLocal tárolja a drivert — minden szálnak saját példánya lesz
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private Driver() {
    }

    // Driver példány lekérése
    public static WebDriver getDriver() {
        if (driver.get() == null) {  // szál-specifikus driver lekérése
            String browser = System.getProperty("browser", "chrome");
            WebDriver webDriver;

            switch (browser.toLowerCase()) {
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                    firefoxOptions.addArguments("--width=1920", "--height=1080");
                    webDriver = new FirefoxDriver(firefoxOptions);
                    break;

                case "chrome":
                default:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    chromeOptions.addArguments("--disable-features=SidePanelPinning");
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                    //chromeOptions.addArguments("--headless=new");

                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    chromeOptions.merge(capabilities);

                    webDriver = new ChromeDriver(chromeOptions);
                    break;
            }

            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.set(webDriver); // szálhoz hozzárendeljük a drivert
        }
        return driver.get();
    }

    // Driver bezárása
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}