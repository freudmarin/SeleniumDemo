package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HerokuLoginUtils {
    public static void login(WebDriver driver, String username, String password) {

        // Inputting username and password
        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("SuperSecretPassword!");

        // Clicking the login button
        WebElement loginButton = driver.findElement(By.className("radius"));
        loginButton.click();
    }
}
