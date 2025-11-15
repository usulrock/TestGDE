package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

public class MyDriverListener implements WebDriverListener {

    private static final Logger logger = LogManager.getLogger(MyDriverListener.class);

    @Override
    public void beforeGet(WebDriver driver, String url) {
        logger.info("Navigálás erre az oldalra: " + url);
    }
}
