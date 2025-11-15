package tests;

import base.BasePage;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.UserData;


public class LoginTest extends BaseTest {

    @Test(description = "Sikeres bejelentkezés")
    public void successfulLoginTest() throws InterruptedException {
        driver.get("https://practice.expandtesting.com/login");
        Thread.sleep(5000);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(UserData.USERNAME, UserData.PASSWORD);
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Bejelentkezés sikertelen!");
    }

    @Test(description = "Sikertelen bejelentkezés helytelen felhasználónévvel")
    public void failedLoginUsernameTest() throws InterruptedException {
        driver.get("https://practice.expandtesting.com/login");
        Thread.sleep(5000);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrongUserName", UserData.PASSWORD);
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("username"), "Bejelentkezés sikeres pedig sikertelent vártunk!");
    }

    @Test(description = "Sikertelen bejelentkezés helytelen jelszóval")
    public void failedLoginPasswordTest() throws InterruptedException {
        driver.get("https://practice.expandtesting.com/login");
        Thread.sleep(5000);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(UserData.USERNAME, "wrongPassword");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("password"), "Bejelentkezés sikeres pedig sikertelent vártunk!");
    }
}

