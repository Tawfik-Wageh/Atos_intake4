package utiles.commonHelper;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class AssertionHelper {

    // Assert element is present and displayed
    public static void assertElementPresent(WebDriver driver, By locator) {
        try {
            boolean isDisplayed = ElementHelper.waitForVisibility(driver, locator).isDisplayed();
            Assert.assertTrue(isDisplayed, "❌ Element not displayed: " + locator);
        } catch (NoSuchElementException e) {
            Assert.fail("❌ Element not found: " + locator);
        }
    }

    // Assert current URL matches expected URL
    public static void assertUrl(WebDriver driver, String expectedUrl) {
        String actualUrl = ElementHelper.getCurrentUrl(driver);
        Assert.assertEquals(actualUrl, expectedUrl,
                "❌ Expected URL: " + expectedUrl + " but found: " + actualUrl);
    }

    // Assert that object is not null
    public static void assertNotNull(Object object, String message) {
        Assert.assertNotNull(object, "❌ " + message);
    }

    // Assert that object is null
    public static void assertNull(Object object, String message) {
        Assert.assertNull(object, "❌ " + message);
    }

    // Assert a condition is true
    public static void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, "❌ " + message);
    }

    // Assert a condition is false
    public static void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, "❌ " + message);
    }

    // Assert equality for strings with better messages
    public static void assertEqual(String actual, String expected) {
        Assert.assertEquals(actual, expected,
                "❌ Expected: [" + expected + "] but found: [" + actual + "]");
    }

    // Assert equality for numbers
    public static void assertEqual(Number actual, Number expected) {
        Assert.assertEquals(actual, expected,
                "❌ Expected: [" + expected + "] but found: [" + actual + "]");
    }

    // Assert text of a web element matches expected value
    public static void assertElementText(WebDriver driver, By locator, String expectedText) {
        String actualText = ElementHelper.waitForVisibility(driver, locator).getText();
        Assert.assertEquals(actualText.trim(), expectedText.trim(),
                "❌ Text mismatch! Expected: [" + expectedText + "] but found: [" + actualText + "]");
    }
}
