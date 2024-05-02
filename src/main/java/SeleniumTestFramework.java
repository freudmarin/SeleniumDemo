import tests.CartAdditionTest;
import tests.CheckoutProcessTest;
import tests.ProductDetailTest;
import tests.ProductFilterTest;

public class SeleniumTestFramework {
    public static void main(String[] args) {
        TestRunner runner = new TestRunner(4); // Number of threads
        runner.addTestCase(new ProductFilterTest());
        runner.addTestCase(new ProductDetailTest());
        runner.addTestCase(new CartAdditionTest());
        runner.addTestCase(new CheckoutProcessTest());
        runner.runTests();
    }
}
