import tests.saucedemo.CartAdditionTest;
import tests.saucedemo.CheckoutProcessTest;
import tests.saucedemo.ProductDetailTest;
import tests.saucedemo.ProductFilterTest;
import tests.theinternetheroku.HerokuElementsTest;
import tests.theinternetheroku.HerokuLoginLogoutTest;

public class SeleniumTestFramework {
    public static void main(String[] args) {
        TestRunner runner = new TestRunner(4); // Number of threads
        runner.addTestCase(new ProductFilterTest());
        runner.addTestCase(new ProductDetailTest());
        runner.addTestCase(new CartAdditionTest());
        runner.addTestCase(new CheckoutProcessTest());
        runner.runTests();

        TestRunner herokuRunner = new TestRunner(2);
        herokuRunner.addTestCase(new HerokuLoginLogoutTest());
        herokuRunner.addTestCase(new HerokuElementsTest());
        herokuRunner.runTests();
    }
}
