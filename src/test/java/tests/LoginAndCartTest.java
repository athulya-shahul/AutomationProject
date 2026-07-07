package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginAndCartTest {

    @Test
    public void loginAndAddToCart()
    throws InterruptedException{

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");
        WebDriver driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        // Open Website
        driver.get("https://www.saucedemo.com");
        Thread.sleep(3000);

        // Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(3000);

        // Verify Login
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
        System.out.println("Login Successful");

        // Add Product to Cart
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge")));

        // Verify Cart Count
        String cartCount = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(cartCount, "1");
        System.out.println("Product Added to Cart");

        // Open Cart
        driver.findElement(By.className("shopping_cart_link")).click();

        // Verify Product
        String productName = driver.findElement(By.className("inventory_item_name")).getText();
        Assert.assertEquals(productName, "Sauce Labs Backpack");
        System.out.println("Cart Verification Successful");
        Thread.sleep(5000);

        driver.quit();
    }
}
