package tests;

import interfaces.TestCase;
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

public class CheckoutProcessTest implements TestCase {
    private static final Logger log = LoggerFactory.getLogger(CheckoutProcessTest.class);

    public void runTest() {
        WebDriver driver = new ChromeDriver();

        try {
            // Open the Sauce Demo website
            driver.get("https://www.saucedemo.com");

            LoginUtils.login(driver, "standard_user", "secret_sauce");

            // Add an item to the cart
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

            // Navigate to the cart and proceed to checkout
            driver.findElement(By.className("shopping_cart_link")).click();
            driver.findElement(By.id("checkout")).click();

            // Fill out checkout information
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys("John");
            driver.findElement(By.id("last-name")).sendKeys("Doe");
            driver.findElement(By.id("postal-code")).sendKeys("90210");
            driver.findElement(By.id("continue")).click();

            // Verify order completion
            WebElement finishButton = new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.elementToBeClickable(By.id("finish")));
            finishButton.click();

            // Verify order completion
            WebElement orderCompleteMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
            if (orderCompleteMessage.getText().equals("Thank you for your order!")) {
                log.info("Checkout process completed successfully.");
            } else {
                log.error("Error: Checkout process did not complete successfully.");
            }
        } catch (Exception e) {
           log.error("An error occurred during the Checkout Process Test: {}" , e.getMessage());
        } finally {
            // Clean up and close the browser
            driver.quit();
        }
    }
}
