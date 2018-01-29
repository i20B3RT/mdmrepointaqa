package com.uscold.mdmrepointaqa.test.user;

/**
 * ********************************************
 * * Created by ${"Robert Morales"} on 1/24/2018.
 * ********************************************
 **/

import com.uscold.mdmrepointaqa.test.AbstractTestClass;
import com.uscold.mdmrepointaqa.test.utility.Assist;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.util.List;

public class CreateUserTest extends AbstractTestClass {

    private static final Logger LOGGER = Logger.getLogger(CreateUserTest.class);

    final static String firstName = "RICH";
    final static String LastName = "Downs";
    final static String securityLevel = "9";
    int S_L_int =Integer.parseInt(securityLevel);
    final static String primaryWhse = "800 - BETHLEHEM";
    final static String ciTy = "CUPERTINO";
    final static String staTe = "California";
    final static String zipCode = "95014";

    @Test
    public void createUserTest() throws InterruptedException {
        Assist.chooseModule(driver,"User Management");
        //choose warehouse
        //Assist.chooseWarehouse(driver, 160);
        //div id successMsg ; is successfully created
        click(driver.findElement(By.id("basicSearch")));
        click(driver.findElement(By.id("addNewUser")));

        driver.findElement(By.id("fName")).clear();
        driver.findElement(By.id("fName")).sendKeys(firstName);
        driver.findElement(By.id("lName")).clear();
        driver.findElement(By.id("lName")).sendKeys(LastName);
        driver.findElement(By.id("securityLevel")).clear();
        driver.findElement(By.id("securityLevel")).sendKeys(securityLevel);


        //Deprecated - make this a method and use primaryWhse as the calling string
        WebElement primaryWhSelect = driver.findElement(By.id("primaryWarehouse_chosen"));
        click(primaryWhSelect.findElement(By.cssSelector("a.chosen-single")));
        List<WebElement> warehouses = primaryWhSelect.findElements(By.cssSelector("div.chosen-drop > ul > li"));
        WebElement choseWh = warehouses.stream().filter(wh -> wh.getText().contains("800")).findFirst().get();
        click(choseWh);

        //LOGGER.warn(recordTimeForOperation(() -> wait.until(webDriver -> !driver.findElement(By.id("userIdGen")).getAttribute("value").isEmpty())));

        click(driver.findElement(By.id("btn_Submit")));

        click(driver.findElement(By.id("chk_all1")));
        click(driver.findElement(By.id("410")));
        click(driver.findElement(By.id("assign")));
        click(driver.findElement(By.id("userSecSubmit")));

        WebElement statusMsg = driver.findElement(By.id("reportSuccessMsg"));
        if (!statusMsg.isDisplayed() && !statusMsg.getText().toLowerCase().contains("successfully created"))
            throw new RuntimeException("Failed to create user");

    }
}