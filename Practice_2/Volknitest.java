package com.gmail.sbe45t;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Volknitest {

    private void sentPaymentInput (String cardHolder, String cardNumber) {
        WebElement cardholderinput = driver.findElement(By.id("input-card-holder"));
        cardholderinput.sendKeys(cardHolder);
        WebElement cardnumber = driver.findElement(By.id("input-card-number"));
        cardnumber.sendKeys(cardNumber);
        Select month = new Select(driver.findElement(By.id("card-expires-month")));
        month.selectByIndex(8);
        Select year = new Select(driver.findElement(By.id("card-expires-year")));
        year.selectByIndex(1);
        WebElement cvv = driver.findElement(By.id("input-card-cvc"));
        cvv.sendKeys("111");
        WebElement paybutton = driver.findElement(By.id("action-submit"));
        paybutton.click();
    }

    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Beast\\Test\\project\\volkni test\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://sandbox.cardpay.com/MI/cardpayment2.html?orderXml=PE9SREVSIFdBTExFVF9JRD0nODI5OScgT1JERVJfTlVNQkVSPSc0NTgyMTEnIEFNT1VOVD0nMjkxLjg2JyBDVVJSRU5DWT0nRVVSJyAgRU1BSUw9J2N1c3RvbWVyQGV4YW1wbGUuY29tJz4KPEFERFJFU1MgQ09VTlRSWT0nVVNBJyBTVEFURT0nTlknIFpJUD0nMTAwMDEnIENJVFk9J05ZJyBTVFJFRVQ9JzY3NyBTVFJFRVQnIFBIT05FPSc4NzY5OTA5MCcgVFlQRT0nQklMTElORycvPgo8L09SREVSPg==&sha512=998150a2b27484b776a1628bfe7505a9cb430f276dfa35b14315c1c8f03381a90490f6608f0dcff789273e05926cd782e1bb941418a9673f43c47595aa7b8b0d");
    }

    @Test
    public void cardnumberinput() {

        WebElement cardnumberinput = driver.findElement(By.id("input-card-number"));
        cardnumberinput.sendKeys("4000000000000001");
        WebElement cardholder = driver.findElement(By.id("input-card-holder"));
        cardholder.click();
        WebElement condition = driver.findElement(By.id("card-number-field"));
        String status = condition.getText();
        Assert.assertEquals("Card number is not valid", status);
       }

    @Test
    public void cardholderinput() {

        sentPaymentInput ("", "4000000000000077");
        WebElement condition = driver.findElement(By.id("card-holder-field"));
        String status = condition.getText();
        Assert.assertEquals("Cardholder name is required", status);
    }

    @Test
    public void declinecardinput() {
        sentPaymentInput ("AAA AAA", "5555555555554477");
        WebElement result = driver.findElement(By.id("payment-status-title"));
        String status = result.getText();
        Assert.assertEquals("Decline", status);
    }

    @Test
    public void successcardinput() {
        sentPaymentInput ("AAA AAA", "4000000000000077");
        WebElement result = driver.findElement(By.id("payment-status-title"));
        String status = result.getText();
        Assert.assertEquals("Success", status);
    }

    @Test
    public void confirmedcardinput() {
        sentPaymentInput ("AAA AAA", "4000000000000077");
        WebElement result = driver.findElement(By.id("payment-item-status"));
        String status = result.getText();
        Assert.assertEquals("confirmed", status.toLowerCase());
    }

    @Test
    public void pendingCardInput() {
        sentPaymentInput("AAA AAA", "4000000000000051");
        WebElement result = driver.findElement(By.xpath("//*[@id=\"payment-status-title\"]/span"));
        String status = result.getText();
        Assert.assertEquals("pending", status.toLowerCase());
    }

    @Test
    public void namecheckCardInput() {
        sentPaymentInput ("AAA AAA", "4000000000000077");
        WebElement result = driver.findElement(By.id("payment-item-cardholder"));
        String status = result.getText();
        Assert.assertEquals("aaa aaa", status.toLowerCase());
    }

    @Test
    public void wrongInput() {
        sentPaymentInput ("AAA AAA", "aaaaaaaaaaaaaaaa");
        WebElement condition = driver.findElement(By.xpath("//*[@id=\"card-number-field\"]/div/label"));
        String status = condition.getText();
        Assert.assertEquals("Card number is not valid", status);
    }

    @Test
    public void wrongCardHolderInput() {
        sentPaymentInput ("123!'#$", "4000000000000077");
        WebElement condition = driver.findElement(By.xpath("//*[@id=\"card-holder-field\"]/div/label"));
        String status = condition.getText();
        Assert.assertEquals("Cardholder name is not valid.", status);
    }

    @Test
    public void pointMouse() {
        WebElement element = driver.findElement(By.id("cvc-hint-toggle"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        WebElement element1 = driver.findElement(By.id("cvc-hint-bubble"));
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(element1));
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

}
