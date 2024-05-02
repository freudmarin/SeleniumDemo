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

public class ProductDetailTest implements TestCase {
    private static final Logger log = LoggerFactory.getLogger(ProductDetailTest.class);

    public void runTest() {
        WebDriver driver = new ChromeDriver();

        try {
            // Open the Sauce Demo website
            driver.get("https://www.saucedemo.com");

            // Log in to the website
            LoginUtils.login(driver, "standard_user", "secret_sauce");

            // Wait for the inventory page to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_container")));

            // Click on the first product to open its details
            WebElement firstProduct = driver.findElement(By.className("inventory_item"));
            String expectedProductName = firstProduct.findElement(By.className("inventory_item_name")).getText();
            String expectedPrice = firstProduct.findElement(By.className("inventory_item_price")).getText();
            firstProduct.findElement(By.className("inventory_item_name")).click();

            // Verify the product details on the new page
            WebElement productDetailsName = driver.findElement(By.className("inventory_details_name"));
            WebElement productDetailsPrice = driver.findElement(By.className("inventory_details_price"));

            // Assert the product details
            boolean nameMatches = productDetailsName.getText().equals(expectedProductName);
            boolean priceMatches = productDetailsPrice.getText().equals(expectedPrice);

            if (nameMatches && priceMatches) {
                log.info("Product details verified successfully: Name and Price match.");
            } else {
                log.error("Error: Product details do not match.");
                log.error("Expected: {} ",  expectedProductName + " / " + expectedPrice);
                log.error("Got: {}", productDetailsName.getText() + " / " + productDetailsPrice.getText());
            }
        } catch (Exception e) {
            log.error("An error occurred during the Product Detail Test: {}" , e.getMessage());
        } finally {
            // Clean up and close the browser
            driver.quit();
        }
    }
}
