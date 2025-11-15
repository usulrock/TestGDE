package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.Duration;

public class Driver {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = System.getProperty("browser", "chrome");
            System.setProperty("webdriver.chrome.driver", "src/test/java/base/webdrivers/chromedriver.exe");
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
                    chromeOptions.addArguments("--start-maximized");  //ablak kinagyítása
                    chromeOptions.addArguments("--disable-notifications");  //értesítések letiltása
                    chromeOptions.addArguments("--disable-popup-blocking");  //felugró ablakok letiltása
                    chromeOptions.addArguments("--disable-features=SidePanelPinning");  //oldalpanel letiltása
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});  //infobar üzenet eltüntetése
                    //chromeOptions.addArguments("--headless=new"); //headless mód engedélyezése

                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    chromeOptions.merge(capabilities);

                    webDriver = new ChromeDriver(chromeOptions);
                    break;
            }

            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.set(webDriver); // thread-hez hozzárendeljük a drivert
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