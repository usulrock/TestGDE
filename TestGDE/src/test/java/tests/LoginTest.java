package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.Retry;
import utils.TestData;


public class LoginTest extends BaseTest {

    @Test(description = "Sikeres bejelentkezés", retryAnalyzer = Retry.class)
    @Parameters("browser")
    public void successfulLoginTest() {
        driver.get(TestData.LOGIN_PAGE);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.USERNAME, TestData.PASSWORD);
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Bejelentkezés sikertelen!");
    }

    @Test(description = "Sikertelen bejelentkezés helytelen felhasználónévvel", retryAnalyzer = Retry.class)
    @Parameters("browser")
    public void failedLoginUsernameTest() {
        driver.get(TestData.LOGIN_PAGE);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrongUserName", TestData.PASSWORD);
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("username"), "Bejelentkezés sikeres pedig sikertelent vártunk!");
    }

    @Test(description = "Sikertelen bejelentkezés helytelen jelszóval", retryAnalyzer = Retry.class)
    @Parameters("browser")
    public void failedLoginPasswordTest() {
        driver.get(TestData.LOGIN_PAGE);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.USERNAME, "wrongPassword");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("password"), "Bejelentkezés sikeres pedig sikertelent vártunk!");
    }
}

