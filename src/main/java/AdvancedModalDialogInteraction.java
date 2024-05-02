import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LoginUtils;

import java.time.Duration;

public class AdvancedModalDialogInteraction {
    private static final Logger log = LoggerFactory.getLogger(AdvancedModalDialogInteraction.class);

    public static void main(String[] args) {
        // Set up ChromeDriver
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        try {
            // Open Sauce Demo website
            driver.get("https://www.saucedemo.com");

            // Perform login
            LoginUtils.login(driver, "standard_user", "secret_sauce");

            // Open modal dialog
            openModalDialog(driver);

            log.info("Opened About page");
        } catch (Exception e) {
           log.error("An error occurred during test execution:", e);
        } finally {
            // Quit WebDriver instance
            driver.quit();
        }
    }

    private static void openModalDialog(WebDriver driver) {
        // Click on a button to trigger the modal dialog
        driver.findElement(By.id("react-burger-menu-btn")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement aboutSidebarLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("about_sidebar_link")));
        aboutSidebarLink.click();
    }
}
