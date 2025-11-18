package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
public class Retry implements IRetryAnalyzer {
    private int retryCount = 0;
    private final Logger logger = LogManager.getLogger();
    public boolean retry(ITestResult result) {
        int maxRetryCount = 2;
        if (retryCount < maxRetryCount) {
            retryCount++;
            logger.info("Retry #" + retryCount + " for test: " + result.getMethod().getMethodName() + ", on thread: " + Thread.currentThread().getName());
            return true;
        }
        return false;
    }
}
