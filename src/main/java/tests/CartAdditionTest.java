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
import java.util.ArrayList;
import java.util.List;

public class CartAdditionTest implements TestCase {
    private static final Logger log = LoggerFactory.getLogger(CartAdditionTest.class);

    public void runTest() {
        WebDriver driver = new ChromeDriver();

        try {
            // Open the Sauce Demo website
            driver.get("https://www.saucedemo.com");

            LoginUtils.login(driver, "standard_user", "secret_sauce");

            // Wait for the inventory page to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));

            // Add multiple items to the cart
            List<String> addedItems = new ArrayList<>();
            List<WebElement> itemsToAdd = driver.findElements(By.xpath("//button[text()='Add to cart']"));
            for (int i = 0; i < 2; i++) {  // Example: Add the first two items to the cart
                WebElement item = itemsToAdd.get(i);
                addedItems.add(driver.findElements(By.className("inventory_item_name")).get(i).getText());
                item.click();
            }

            // Navigate to the cart page
            driver.findElement(By.className("shopping_cart_link")).click();

            // Verify cart contents
            List<WebElement> cartItems = driver.findElements(By.className("inventory_item_name"));
            boolean allItemsMatch = true;
            for (WebElement cartItem : cartItems) {
                if (!addedItems.contains(cartItem.getText())) {
                    allItemsMatch = false;
                    break;
                }
            }

            if (allItemsMatch) {
                log.info("All items were added to the cart successfully.");
            } else {
                log.error("Error: Not all items match the expected results.");
            }
        } catch (Exception e) {
            log.error("An error occurred during the Cart Addition Test: {}" , e.getMessage());
        } finally {
            // Clean up and close the browser
            driver.quit();
        }
    }
}
