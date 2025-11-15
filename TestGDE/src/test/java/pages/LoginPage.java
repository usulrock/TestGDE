package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    private static final Logger logger = LogManager.getLogger();
    public LoginPage(WebDriver driver) {
        super(driver);
        if (!getPageTitle().contains("Test Login Page for Automation Testing Practice")) {
            throw new IllegalStateException("Ez nem a Login page -  " + getCurrentUrl());
        }
    }

    @FindBy(id = "submit-login")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@id='core']/div/div/a/i")
    private WebElement logoutButton;

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "flash")
    private WebElement alertMessage;

    @Step("Bejelentkezés a felhasználónévvel: {username} és jelszóval: {password}")
    public void login(String username, String password) {
        logger.info("Bejelentkezés a felhasználónévvel: " + username + " és jelszóval: " + password);
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    @Step("Sikeres bejelentkezés ellenőrzése a logout gomb megjelenésével és az üzenettel")
    public boolean isLoginSuccessful() {
        logger.info("Ellenőrzés, hogy a bejelentkezés sikeres-e");
        waitUntilVisible(logoutButton);
        return logoutButton.isDisplayed() && getText(alertMessage).contains("You logged into a secure area!");
    }

    @Step("Sikertelen bejelentkezés ellenőrzése a hibaüzenettel a {credentialType} alapján")
    public boolean isErrorMessageDisplayed(String credentialType) {
        logger.info("Ellenőrzés, hogy a bejelentkezés sikertelen-e a helytelen " + credentialType + " miatt");
        waitUntilVisible(alertMessage);
        return getText(alertMessage).contains("Your "+ credentialType + " is invalid!");
    }
}
