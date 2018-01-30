package com.uscold.mdmrepointaqa.test.utility;


import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.uscold.mdmrepointaqa.test.util.TestConstants.GET_ELEMENT_TIMEOUT;

public class Assist {
    private static final Logger LOGGER = Logger.getLogger(Assist.class);
    private static Set<Cookie> cookies = new HashSet<>();
    private final static int maxWaitTimeMillisToBeUsedInChooseFunctions = 3000;

    public static void click(WebElement el, int maxWaitTimeMillis) {
        long startedAt = System.currentTimeMillis();
        int step = 100;
        RuntimeException caughtEx = null;

        while (System.currentTimeMillis() - startedAt < maxWaitTimeMillis) {
            try {
                el.click();
                LOGGER.warn("was waiting for " + (System.currentTimeMillis() - startedAt) + " to click on " + el);
                return;
            } catch (WebDriverException ex) {
                caughtEx = ex;
            }
            try {
                Thread.sleep(step);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Waiting time is out for element to become clickable:" + el, caughtEx);
    }

    public static synchronized void loginWithCookies(WebDriver driver, String url) {
        if (cookies.isEmpty()) {
            try {
                login(driver, url);
                cookies = driver.manage().getCookies();
            } catch (Exception ex) {
                LOGGER.error(ex);
            }
        } else {
            driver.get(url);
            driver.manage().deleteAllCookies();
            cookies.forEach(cookie -> driver.manage().addCookie(cookie));
            driver.get(url);
        }
    }

    public static void login(WebDriver driver, String url) {
        driver.get(url);
        if (driver.getCurrentUrl().endsWith("login.html")) {
            WebElement loginElement = driver.findElement(By.name("j_username"));
            WebElement passwordElement = driver.findElement(By.name("j_password"));

            loginElement.sendKeys(System.getProperty("user.username"));
            passwordElement.sendKeys(System.getProperty("user.password"));
            driver.findElement(By.name("btn_login")).click();
        }
    }

    public static void click(WebElement el) throws InterruptedException {
        click(el, GET_ELEMENT_TIMEOUT * 1000);
    }

    static void click(WebDriver driver, By by) throws InterruptedException {
        WebElement el = driver.findElement(by);
        click(el, GET_ELEMENT_TIMEOUT * 1000);
    }

    public static void chooseModule(WebDriver driver, String moduleName) {
        try {
            WebElement searchModule = driver.findElement(By.id("searchText"));
            Assist.scrollTo(driver, searchModule);
            click(searchModule);
            searchModule.clear();
            searchModule.sendKeys(moduleName);
            click(driver.findElement(By.linkText(moduleName)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void chooseWarehouse(WebDriver driver, final String substring) {
        WebElement whSelect = driver.findElement(By.id("globalWarehouseSelect_chosen"));
        whSelect.findElement(By.xpath(".//a[@class='chosen-single']")).click();
        List<WebElement> warehouses = whSelect.findElements(By.xpath(".//div[@class='chosen-drop']//ul[@class='chosen-results']/li"));
        WebElement dropDownElem = warehouses.stream().filter(wh -> wh.getText().contains(substring)).findFirst().get();
        click(dropDownElem, maxWaitTimeMillisToBeUsedInChooseFunctions);
        if (isElementPresent(driver, By.id("warehouseOk")))
            click(driver.findElement(By.id("warehouseOk")), maxWaitTimeMillisToBeUsedInChooseFunctions);

    }

    static void chooseCustomer(WebDriver driver, final String substring) {
        try {
            WebElement custSelect = driver.findElement(By.id("globalCustomerSelect_chosen"));
            click(custSelect.findElement(By.xpath(".//a[@class='chosen-single']")));
            List<WebElement> customers = custSelect.findElements(By.xpath(".//div[@class='chosen-drop']//ul[@class='chosen-results']/li"));
            WebElement chosenCustomerOption = customers.stream().filter(wh -> wh.getText().contains(substring)).findFirst().get();
            click(chosenCustomerOption, maxWaitTimeMillisToBeUsedInChooseFunctions);
            if (isElementPresent(driver, By.id("customerOk")))
                click(driver.findElement(By.id("customerOk")), maxWaitTimeMillisToBeUsedInChooseFunctions);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void sendId(WebDriver driver,String byID, String sendKeysVal) {
        long startedAtSend = System.currentTimeMillis();
                driver.findElement(By.id(byID)).sendKeys(sendKeysVal);
//                System.out.print("[Assist] [INFO] this value was send. value:  ->" + sendKeysVal + System.lineSeparator());
                //LOGGER.info("this value was send. value:  ->" + sendKeysVal);
                LOGGER.warn("was waiting for " + (System.currentTimeMillis() - startedAtSend) + " to send this value : " + sendKeysVal+ " to ====================-> id:"+byID);
                return;
    }
    public static void sendXp(WebDriver driver,String byXp, String sendKeysVal) {
        long startedAtSend = System.currentTimeMillis();
        driver.findElement(By.xpath(byXp)).sendKeys(sendKeysVal);
//                System.out.print("[Assist] [INFO] this value was send. value:  ->" + sendKeysVal + System.lineSeparator());
        //LOGGER.info("this value was send. value:  ->" + sendKeysVal);
        LOGGER.warn("was waiting for " + (System.currentTimeMillis() - startedAtSend) + " to send this value : " + sendKeysVal+ " to ====================-> xpath:"+byXp);
        return;
    }

    public static boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static void chooseWarehouse(WebDriver driver, int number) {
        chooseWarehouse(driver, String.valueOf(number));
    }

    public static void chooseCustomer(WebDriver driver, int number) throws InterruptedException {
        chooseCustomer(driver, String.valueOf(number));
    }

    public static void maximizeWindow(WebDriver driver) {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            org.openqa.selenium.Point position = new org.openqa.selenium.Point(0, 0);
            driver.manage().window().setPosition(position);
            org.openqa.selenium.Dimension maximizedScreenSize =
                    new org.openqa.selenium.Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
            driver.manage().window().setSize(maximizedScreenSize);
        }
        driver.manage().window().maximize();

    }

    static boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public static void scrollTo(WebDriver driver, WebElement elem) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public static WebElement chooseValueFromStandardDropDownByTextMatch(WebDriver driver, String id, String value) throws InterruptedException {
        List<WebElement> values = clickOnDropDownLabel(driver, id, value);
        return values.stream().filter(el -> el.getAttribute("innerText").trim().equalsIgnoreCase(value)).findFirst().get();
    }

    public static WebElement chooseValueFromStandardDropDownBySubstring(WebDriver driver, String id, String substring) throws InterruptedException {
        List<WebElement> values = clickOnDropDownLabel(driver, id, substring);
        return values.stream().filter(el -> el.getAttribute("innerText").trim().toLowerCase().contains(substring.toLowerCase())).findFirst().get();
    }

    private static List<WebElement> clickOnDropDownLabel(WebDriver driver, String id, String textToFind) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, GET_ELEMENT_TIMEOUT);
        WebElement accTypeContainer = driver.findElement(By.id(id));
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(By.id(id),
                "class", "chosen-disabled")));
        WebElement aElem = accTypeContainer.findElement(By.cssSelector("a.chosen-single"));
        //scrollTo(driver,aElem);
        click(aElem);
        //accTypeContainer.findElement(By.xpath("./div/div/input")).sendKeys(textToFind);
        return accTypeContainer.findElements(By.xpath("div[@class='chosen-drop']/ul/li"));
    }

}
