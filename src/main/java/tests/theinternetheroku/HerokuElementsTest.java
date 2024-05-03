package tests.theinternetheroku;

import interfaces.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HerokuElementsTest implements TestCase {
    @Override
    public void runTest() throws Exception {
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to the website
            driver.get("https://the-internet.herokuapp.com");

            // Check for a specific element by its text
            WebElement heading = driver.findElement(By.tagName("h1"));
            if (heading.getText().equals("Welcome to the-internet")) {
                System.out.println("Test Passed: Heading is correct.");
            } else {
                System.out.println("Test Failed: Heading is incorrect.");
            }

            // Optionally, you can add more tests here, for example:
            // Check if the "Form Authentication" link is present
            WebElement link = driver.findElement(By.linkText("Form Authentication"));
            if (link.isDisplayed()) {
                System.out.println("Test Passed: 'Form Authentication' link is present.");
            } else {
                System.out.println("Test Failed: 'Form Authentication' link is not present.");
            }

            Map<String, Boolean> expectedLinks = new HashMap<>();
            expectedLinks.put("Form Authentication", true);
            expectedLinks.put("Dropdown", true);
            expectedLinks.put("JavaScript Alerts", true);
            expectedLinks.put("File Upload", true);
            expectedLinks.put("Non-Existent Link", false);
            for (Map.Entry<String, Boolean> entry : expectedLinks.entrySet()) {
                List<WebElement> links = driver.findElements(By.linkText(entry.getKey()));
                boolean isDisplayed = !links.isEmpty() && links.get(0).isDisplayed();
                assertLinkVisibility(entry.getKey(), entry.getValue(), isDisplayed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    private static void assertLinkVisibility(String linkText, boolean expectedVisible, boolean actualVisible) {
        if (expectedVisible == actualVisible) {
            System.out.println("Test Passed: Visibility of '" + linkText + "' is as expected.");
        } else {
            System.out.println("Test Failed: Visibility of '" + linkText + "' is not as expected. Expected: "
                    + expectedVisible + ", Actual: " + actualVisible);
        }
    }
}
