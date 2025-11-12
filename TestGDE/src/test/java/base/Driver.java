package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.Duration;

public class Driver {

    private static WebDriver driver;

    // Privát konstruktor – Singleton minta
    private Driver() {
    }

    // Driver példány lekérése
    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = System.getProperty("browser", "chrome");
            switch (browser.toLowerCase()) {
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addPreference("dom.webnotifications.enabled", false); // értesítések tiltása
                    firefoxOptions.addArguments("--width=1920", "--height=1080");
                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                case "chrome":
                default:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");  // ablak kinagyítása
                    chromeOptions.addArguments("--disable-notifications"); // értesítések tiltása
                    chromeOptions.addArguments("--disable-popup-blocking"); // felugró ablakok letiltása
                    chromeOptions.addArguments("--disable-features=SidePanelPinning"); // oldalsáv letiltása
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); // infobar eltávolítása
                    //chromeOptions.addArguments("--headless=new"); // headless futtatás

                    // Példa capability hozzáadására
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    chromeOptions.merge(capabilities);

                    driver = new ChromeDriver(chromeOptions);
                    break;
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }

    // Driver bezárása
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}

