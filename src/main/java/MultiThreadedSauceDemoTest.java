import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LoginUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadedSauceDemoTest {
    private static final Logger log = LoggerFactory.getLogger(MultiThreadedSauceDemoTest.class);

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ExecutorService executor = Executors.newFixedThreadPool(2); // Adjust the number of threads

        Runnable task1 = () -> runTest("standard_user", "secret_sauce");
        Runnable task2 = () -> runTest("problem_user", "secret_sauce");

        executor.execute(task1);
        executor.execute(task2);

        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Tests interrupted.");
        }
    }

    private static void runTest(String username, String password) {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.saucedemo.com");
            LoginUtils.login(driver, username, password);
            log.info("Test completed for user: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
