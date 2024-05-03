package tests.saucedemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LoginUtils;

import java.time.Duration;

public class SauceDemoLoginTest {
    private static final Logger log = LoggerFactory.getLogger(SauceDemoLoginTest.class);

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Open Sauce Demo
            driver.get("https://www.saucedemo.com");

            LoginUtils.login(driver, "standard_user", "secret_sauce");

            // Check if login was successful by looking for logout link
            WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
            menuButton.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement logoutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));

            if (logoutLink.isDisplayed()) {
                log.info("Login successful!");
            } else {
                log.error("Login failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
