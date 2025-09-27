package utiles.commonHelper;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {
    private static final int waitingTime = 30;

    public static WebElement waitForVisibility(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingTime));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingTime));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForInvisibility(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingTime));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitUntilTextPresent(WebDriver driver, By locator, String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingTime));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
    }

    public static WebElement fluentWait(WebDriver driver, By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
        return wait.until(driver1 -> driver1.findElement(locator));
    }

    public static void waitForLoaderToDisappear(WebDriver driver, By loaderLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingTime));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));
    }

    public static void waitForUrlContains(WebDriver driver, String fragment) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingTime));
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    public static void waitForDocumentReady(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingTime));
        wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }

    public static void waitForPageToLoad(WebDriver driver, String urlFragmentOrNull) {
        waitForDocumentReady(driver);
        if (urlFragmentOrNull != null && !urlFragmentOrNull.isEmpty()) {
            waitForUrlContains(driver, urlFragmentOrNull);
        }
    }

    public static void clickWhenClickable(WebDriver driver, By locator) {
        waitForClickable(driver, locator).click();
    }

    public static void typeWhenVisible(WebDriver driver, By locator, String text) {
        WebElement element = waitForVisibility(driver, locator);
        element.clear();
        element.sendKeys(text);
    }
}


