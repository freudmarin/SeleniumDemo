
import interfaces.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class TestRunner {
    private List<TestCase> testCases = new ArrayList<>();
    private ExecutorService executor;

    public TestRunner(int threads) {
        executor = Executors.newFixedThreadPool(threads);
    }

    public void addTestCase(TestCase testCase) {
        testCases.add(testCase);
    }

    public void runTests() {
        for (TestCase testCase : testCases) {
            executor.submit(() -> {
                try {
                    testCase.runTest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
