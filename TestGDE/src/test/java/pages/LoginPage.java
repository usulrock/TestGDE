package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
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
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    @Step("Sikeres bejelentkezés ellenőrzése a logout gomb megjelenésével és az üzenettel")
    public boolean isLoginSuccessful() {
        waitUntilVisible(logoutButton);
        return logoutButton.isDisplayed() && getText(alertMessage).contains("You logged into a secure area!");
    }

    @Step("Sikeres bejelentkezés ellenőrzése a logout gomb megjelenésével és az üzenettel")
    public boolean isErrorMessageDisplayed() {
        waitUntilVisible(alertMessage);
        return getText(alertMessage).contains("Your username is invalid!");
    }

}
