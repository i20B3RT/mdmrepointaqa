package com.uscold.mdmrepointaqa.test;

import com.uscold.mdmrepointaqa.test.utility.Assist;
import com.uscold.mdmrepointaqa.test.util.TestConstants;
import com.uscold.mdmrepointaqa.test.util.WebDriverFactory;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

import static com.uscold.mdmrepointaqa.test.util.TestConstants.GET_ELEMENT_TIMEOUT;

public abstract class AbstractTestClass extends BaseTestNGTest {
    private static final Logger LOGGER = Logger.getLogger(AbstractTestClass.class);
    private static final boolean IS_HEADLESS = BooleanUtils.toBoolean(System.getProperty("ui.headless"));
    public static final String EWM_URL = System.getProperty("ewm.url");

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeSuite
    public void beforeSuite() {
        LOGGER.info("EWM_URL:" + EWM_URL);
        LOGGER.info("HEADLESS:" + IS_HEADLESS);
    }


    @BeforeClass
    public void beforeClassInit() {
        driver = initDriver();
        wait = new WebDriverWait(driver, GET_ELEMENT_TIMEOUT);
        driver.manage().timeouts().implicitlyWait(TestConstants.GET_ELEMENT_TIMEOUT, TimeUnit.SECONDS);
        Assist.loginWithCookies(driver, EWM_URL);
    }


    private WebDriver initDriver() {
        driver = WebDriverFactory.getDriver(getOptions(IS_HEADLESS));
        return driver;
    }

    private ChromeOptions getOptions(boolean isHeadless) {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (isHeadless) {
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--test-type");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--no-first-run");
            chromeOptions.addArguments("--no-default-browser-check");
            chromeOptions.addArguments("--ignore-certificate-errors");
            chromeOptions.addArguments("--start-maximized");
        }

        return chromeOptions;
    }

    @AfterClass(alwaysRun = true)
    public void afterTest() {
        driver.quit();
        LOGGER.info("Driver quit");
    }

    protected void click(WebElement el, int maxWaitTimeMillis) {
        Assist.click(el, maxWaitTimeMillis);
    }

    protected void click(WebElement el) {
        Assist.click(el, GET_ELEMENT_TIMEOUT * 1000);
    }

    protected void click(By by) {
        WebElement el = driver.findElement(by);
        Assist.click(el, GET_ELEMENT_TIMEOUT * 1000);
    }

    long recordTimeForOperation(Runnable runnable) {
        long startedAt = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - startedAt;
    }

    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
    public boolean isElementFound( String text) {
        long startedAt = System.currentTimeMillis();
        RuntimeException caughtEx = null;
        try{
            WebElement webElement = driver.findElement(By.id(text));
            System.out.println("isElementFound : true :"+text + "true");
            LOGGER.warn("was waiting for " + (System.currentTimeMillis() - startedAt) + " to click on " + text);
        }catch(NoSuchElementException e){
            e.printStackTrace();
            System.out.println("isElementFound : false :"+text);
            return false;
        }
        return true;
    }


    public WebDriver getDriver() {
        return driver;
    }
}