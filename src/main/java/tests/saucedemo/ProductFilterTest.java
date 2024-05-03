package tests.saucedemo;

import interfaces.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LoginUtils;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ProductFilterTest implements TestCase {
    private static final Logger log = LoggerFactory.getLogger(ProductFilterTest.class);
    public void runTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.saucedemo.com");
            LoginUtils.login(driver, "standard_user", "secret_sauce");

            WebDriverWait waitForInventoryContainer = new WebDriverWait(driver, Duration.ofSeconds(10));
            waitForInventoryContainer.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_container")));

            WebDriverWait waitForProductSortContainer= new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement dropdown1 = waitForProductSortContainer.until(ExpectedConditions.refreshed(
                    ExpectedConditions.elementToBeClickable(By.className("product_sort_container"))
            ));
            Select select = new Select(dropdown1);
            select.selectByValue("lohi");

            boolean priceSortedCorrectly = isSortedByPrice(driver);
            log.info("Sorted by Price (Low to High): {}", (priceSortedCorrectly ? "Passed" : "Failed"));
            WebElement dropdown2 = waitForProductSortContainer.until(ExpectedConditions.refreshed(
                    ExpectedConditions.elementToBeClickable(By.className("product_sort_container"))
            ));
            Select selectAZ = new Select(dropdown2);
            selectAZ.selectByValue("az");
            boolean nameSortedCorrectly = isSortedByName(driver);
            log.info("Sorted by Name (A to Z): {}", (nameSortedCorrectly ? "Passed" : "Failed"));
        } catch (Exception e) {
            log.error("An error occurred during the Product Sorting Test: ", e);
        } finally {
            driver.quit();
        }
    }

    private boolean isSortedByPrice(WebDriver driver) {
        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        List<Double> priceValues = prices.stream()
                .map(price -> Double.parseDouble(price.getText().replace("$", "")))
                .collect(Collectors.toList());

        return isSortedAscending(priceValues);
    }

    private boolean isSortedByName(WebDriver driver) {
        List<WebElement> names = driver.findElements(By.className("inventory_item_name"));
        List<String> nameValues = names.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        return isSortedAlphabetically(nameValues);
    }

    private boolean isSortedAscending(List<Double> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedAlphabetically(List<String> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }
}
