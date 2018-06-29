package com.gmail.sbe45t;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Volknitest {

    private void cityCheck (String cityName) {
        WebElement cityButton = driver.findElement(By.cssSelector("body > div.main > div > div.header2.suggest-owner.i-bem.header2_type_default > noindex > div.header2__main > div > div.header2__middle > div > div.header2__right > div.header2-menu.header2-menu_loading_yes.i-bem.header2-menu_js_inited > span > span.header2-menu__text"));
        cityButton.click();
        WebElement cityBox = driver.findElement(By.cssSelector("body > div.modal.modal_theme_normal.modal_autoclosable_yes.modal_background_paranja.modal_closer_content.popup2.popup2_autoclosable_yes.popup2_closer_yes.i-bem.popup2_js_inited.modal_js_inited.modal_has-animation_yes.popup2_visible_yes.modal_visible_yes > div > div > div.modal__content > div.modal__wrapper > form > div > div > div > div.input.input_size_l.input_theme_normal.i-bem.input_js_inited > span > input"));
        cityBox.sendKeys(cityName);
        cityBox.sendKeys(Keys.RETURN);
        List<WebElement> myElem = driver.findElements(By.className("region-suggest__list-item"));
        myElem.get(0).click();
    }

    private void findNokia (String phoneVar) {
        WebElement searchBox = driver.findElement(By.id("header-search"));
        searchBox.sendKeys(phoneVar);
        searchBox.sendKeys(Keys.RETURN);
    }

    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Beast\\Test\\project\\volkni test\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://market.yandex.ru/");
    }

    @Test
    public void nokiaSearch() throws InterruptedException {
        cityCheck( "самара");
        Thread.sleep(1000);
        findNokia("nokia 6");
        WebElement sortPrice = driver.findElement(By.cssSelector("body > div.main > div:nth-child(5) > div.n-filter-panel-dropdown.i-bem.n-filter-panel-dropdown_js_inited > div.n-filter-panel-dropdown__main > div.n-filter-panel-dropdown__item.n-filter-panel-dropdown__item_size_triple.n-filter-panel-dropdown__item_left_100 > div.n-filter-block_pos_left.i-bem > div:nth-child(3) > a"));
        sortPrice.click();
        Thread.sleep(5000);
        WebElement price = driver.findElement(By.cssSelector("body > div.main > div:nth-child(5) > div.layout.layout_type_search.i-bem > div.layout__col.i-bem.layout__col_search-results_normal > div.n-filter-applied-results.metrika.b-zone.i-bem.n-filter-applied-results_js_inited.b-zone_js_inited > div > div.n-snippet-list.n-snippet-list_type_grid.snippet-list_size_3.metrika.b-zone.b-spy-init.i-bem.metrika_js_inited.snippet-list_js_inited.b-spy-init_js_inited.b-zone_js_inited > div:nth-child(1) > div.n-snippet-cell2__body > div.n-snippet-cell2__price > div > a > div"));
        String status = price.getText();
        Assert.assertEquals("19 000 \u20BD", status);
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

}
