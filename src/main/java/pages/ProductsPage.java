package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utiles.DriverMange.DriverManager;
import utiles.commonHelper.ElementHelper;

import java.util.List;

public class ProductsPage {
    WebDriver driver;

    // Locators
    By ProductsHeader = By.xpath("//h2[text()='All Products']");
    By ProductCards = By.cssSelector(".features_items .product-image-wrapper");
    By SearchInput = By.id("search_product");
    By SearchButton = By.id("submit_search");
    By SearchResultsHeader = By.xpath("//h2[text()='Searched Products']");
    By ViewProductLinks = By.xpath("//a[text()='View Product']");

    public ProductsPage() {
        this.driver = DriverManager.getDriver();
    }

    public boolean isLoaded() {
        ElementHelper.waitForDocumentReady(driver);
        return ElementHelper.isElementDisplayed(driver, ProductsHeader);
    }

    public int getVisibleProductsCount() {
        List<WebElement> products = ElementHelper.getElements(driver, ProductCards);
        return products.size();
    }

    public void searchForProduct(String query) {
        ElementHelper.sendText(driver, SearchInput, query);
        ElementHelper.click(driver, SearchButton);
        ElementHelper.waitForVisibility(driver, SearchResultsHeader);
        ElementHelper.waitForDocumentReady(driver);
    }

    public int getSearchResultsCount() {
        List<WebElement> products = ElementHelper.getElements(driver, ProductCards);
        return products.size();
    }

    public void openFirstProductDetails() {
        List<WebElement> links = ElementHelper.getElements(driver, ViewProductLinks);
        if (links.isEmpty()) {
            return;
        }
        WebElement first = links.get(0);
        try {
            ElementHelper.highlightElement(driver, first);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", first);
        } catch (Exception ignored) {
        }
        try {
            ElementHelper.click(first);
            return;
        } catch (Exception ignored) {
        }
        try {
            ElementHelper.jsClick(driver, ViewProductLinks);
            return;
        } catch (Exception ignored) {
        }
        try {
            String href = first.getAttribute("href");
            if (href != null && !href.isEmpty()) {
                driver.get(href);
            }
        } catch (Exception ignored) {
        }
    }
}



