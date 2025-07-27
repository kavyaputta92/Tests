package ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UITests {

    private static WebDriver driver;

    @BeforeAll
    public static void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("http://localhost:3000");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @Order(1)
    public void testLoginInvalid() {
        driver.navigate().refresh();
        driver.findElement(By.cssSelector("input[placeholder='Username']")).sendKeys("wrong");
        driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys("wrong");
        driver.findElement(By.tagName("button")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            assertEquals("Invalid login", alert.getText());
            alert.accept(); // Dismiss the alert
        } catch (TimeoutException e) {
            fail("Expected alert was not present for invalid login.");
        }
    }


    @Test
    @Order(2)
    public void testLoginValid() {
        driver.navigate().refresh();
        driver.findElement(By.cssSelector("input[placeholder='Username']")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys("admin");
        driver.findElement(By.tagName("button")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            // If alert appears, this is a failure
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            fail("Unexpected alert present: " + alert.getText());
        } catch (TimeoutException ignored) {
            // No alert is good
        }

        assertTrue(driver.getPageSource().contains("Items"));
    }


    @Test
    @Order(3)
    public void testAddItem() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement input = driver.findElement(By.cssSelector("input[placeholder='New Item']"));
        input.clear();
        input.sendKeys("Selenium Test Item");
        driver.findElement(By.xpath("//button[text()='Add']")).click();

        // Wait until the new item appears in the page body
        boolean itemAppears = wait.until(ExpectedConditions.textToBePresentInElementLocated(
            By.tagName("body"), "Selenium Test Item"));

        assertTrue(itemAppears, "The item 'Selenium Test Item' was not found after adding.");
    }


    @Test
    @Order(4)
    public void testEditItem() {
        List<WebElement> editButtons = driver.findElements(By.xpath("//button[text()='Edit']"));
        assertFalse(editButtons.isEmpty());

        WebElement editButton = editButtons.get(0);
        editButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement editInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input")));
        editInput.clear();
        editInput.sendKeys("Selenium Updated Item");

        driver.findElement(By.xpath("//button[text()='Save']")).click();
        assertTrue(driver.getPageSource().contains("Selenium Updated Item"));
    }
    @Test
    @Order(5)
    public void testDeleteItem() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Find all items
        List<WebElement> items = driver.findElements(By.cssSelector("li"));
        WebElement targetItem = null;

        for (WebElement item : items) {
            if (item.getText().contains("Selenium")) {
                targetItem = item;
                break;
            }
        }

        assertNotNull(targetItem, "Could not find item containing text 'Selenium'");

        // Find the Delete button next to the target item
        WebElement deleteBtn = targetItem.findElement(By.xpath(".//button[text()='Delete']"));
        deleteBtn.click();

        // Wait for the item to disappear
        boolean isDeleted = wait.until(ExpectedConditions.stalenessOf(targetItem));
        assertTrue(isDeleted, "Item was not deleted successfully.");
    }

}