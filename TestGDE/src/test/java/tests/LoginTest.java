package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.UserData;


public class LoginTest extends BaseTest {

    @Test
    public void successfulLoginTest() throws InterruptedException {
        driver.get("https://practice.expandtesting.com/login");
        Thread.sleep(10000);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(UserData.USERNAME, UserData.PASSWORD);
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Bejelentkezés sikertelen!");
    }

    @Test
    public void failedLoginTest() throws InterruptedException {
        driver.get("https://practice.expandtesting.com/login");
        Thread.sleep(10000);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrongUserName", UserData.PASSWORD);
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Bejelentkezés sikeres!");
    }
}

