package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class Driver {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver(String browser) {
        if (driver.get() == null) {

            String localRun = System.getProperty("localRun", "true");
            WebDriver webDriver;

            if (localRun.equalsIgnoreCase("false")) {

                DesiredCapabilities options;

                switch (browser) {
                    case "chrome":
                        ChromeOptions chromeRemote = new ChromeOptions();
                        options = new DesiredCapabilities();
                        options.setCapability(ChromeOptions.CAPABILITY, chromeRemote);
                        break;

                    case "firefox":
                        FirefoxOptions firefoxRemote = new FirefoxOptions();
                        options = new DesiredCapabilities();
                        options.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxRemote);
                        break;

                    default:
                        throw new RuntimeException("Unsupported browser: " + browser);
                }

                // Selenoid/Moon opciók
                options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                    put("sessionTimeout", "1m");
                    put("labels", new HashMap<String, Object>() {{
                        put("manual", "true");
                    }});
                    put("enableVNC", true);
                }});

                try {
                    webDriver = new RemoteWebDriver(
                            new URL("https://" + System.getenv("MOON_BUSINESS_UNIT") + ":" + System.getenv("MOON_PASSWORD") + "@moon.live.k8s-platform.gis-prod.eva.expertcity.com/wd/hub"),
                            options
                    );
                } catch (MalformedURLException e) {
                    throw new RuntimeException("Moon URL is invalid!", e);
                }

                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                driver.set(webDriver);
                return driver.get();
            } else {
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
            }

            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.set(webDriver);
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