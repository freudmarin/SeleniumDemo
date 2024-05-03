package tests.theinternetheroku;

import interfaces.TestCase;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HerokuLoginUtils;

import java.time.Duration;


public class HerokuLoginLogoutTest implements TestCase {

    private static final Logger log = LoggerFactory.getLogger(HerokuLoginLogoutTest.class);

    @Override
    public void runTest() throws Exception {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Open Sauce Demo
            driver.get("https://the-internet.herokuapp.com/login");
            HerokuLoginUtils.login(driver, "tomsmith", "SuperSecretPassword!");
            WebElement successMessage = driver.findElement(By.cssSelector(".flash.success"));
            if (successMessage.getText().contains("You logged into a secure area!")) {
                log.info("Test Passed: Login successful.");
            } else {
                log.error("Test Failed: Login failed.");
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button.secondary.radius")));

            driver.findElement(By.cssSelector(".button.secondary.radius")).click();
            WebElement logoutMessage = driver.findElement(By.id("flash"));
            if (logoutMessage.getText().contains("You logged out of the secure area!")) {
                log.info("Test Passed: Logout successful.");
            } else {
                log.error("Test Failed: Logout failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
