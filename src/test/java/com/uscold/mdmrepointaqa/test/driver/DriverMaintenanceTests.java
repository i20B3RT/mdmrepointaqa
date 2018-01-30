package com.uscold.mdmrepointaqa.test.driver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.uscold.mdmrepointaqa.test.AbstractTestClass;
import com.uscold.mdmrepointaqa.test.utility.Assist;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static com.uscold.mdmrepointaqa.test.utility.Assist.sendId;


public class DriverMaintenanceTests extends AbstractTestClass {


    final static String WHSE = "800 - BETHLEHEM";
    int WHSE_int = Integer.parseInt(WHSE.substring(1, 3));
    final static String driverName = "JOHN";
    final static String WHSETEST = "800 - BETHLEHEM";
    final static String entNum = "Enterprise";
    int expectednumber = 1;
    private String offNumTotal = "";
    private String offNumOnsiteTotal = "";
    public String fistDriverFromGridList = "";
    private String firstRecordFoundOne = "";
    private String firstRecordFoundOne_int ="";
//    private String recordSearched = "";


    @Test(priority = 1,description = "TC: Search drivers at the whse level")
    public void SearchWhseLevelTest()  {

        Assist.chooseModule(driver, "Driver Maintenance");
        Assist.chooseWarehouse(driver, WHSE);

        //Send driver value to the driver basic search box
        //driver.findElement(By.id("txt_accountType")).sendKeys(driverName);

        sendId(driver,"txt_accountType",driverName);

        //Click on the search button using xpath
        click(driver.findElement(By.xpath("//button[@id='btn_basicSearch']")));

        //Wait on spinner is no longer displaying
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));

        //check if more than one record was returned, if less than 0, Assert will come back false
        int offNum = Integer.parseInt(driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getText());
        offNumTotal = driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getAttribute("value");
        Assert.assertTrue(offNum > expectednumber,"No driver records were returned whit the name: "+driverName);

        firstRecordFoundOne = driver.findElement(By.xpath("//tr[2][@class='ui-widget-content jqgrow ui-row-ltr']/td[3]")).getText();
        System.out.print("[Assist] [INFO] this driver record was cached: "+firstRecordFoundOne+" for searching with driver number at the whse level"+ System.lineSeparator());
    }

    @Test(dependsOnMethods = "SearchWhseLevelTest",priority = 2 )
    public void DriverSearchWithDriverNumberFirstDriverReturedFromWhseLevelTest() throws InterruptedException {
        //Select from dropdown
        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "sel_accountType_chosen","Driver #"));

        //Clear an send value from above test
        driver.findElement(By.id("txt_accountType")).clear();
        //System.out.print("This was used to search: "+firstRecordFoundOne);
        //driver.findElement(By.id("txt_accountType")).sendKeys(firstRecordFoundOne);
        sendId(driver,"txt_accountType",firstRecordFoundOne);

        //Click on the search button using xpath
        click(driver.findElement(By.xpath("//button[@id='btn_basicSearch']")));

//        final String recordSearched = driver.findElement(By.id("txt_accountType")).getAttribute("value");
//        int recordSearched_int = Integer.parseInt(recordSearched);

        //Wait for spinner to disappeared
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));


        //Check table for first record found on table
        int firstRecordFoundTwo = Integer.parseInt(driver.findElement(By.xpath("//tr[2][@class='ui-widget-content jqgrow ui-row-ltr']/td[3]")).getText());
        //final String rOne = firstRecordFound;


        System.out.print("int line 80: Record returned from driver number search: "+firstRecordFoundTwo);
//        System.out.print("||||||| Record cache from the whse test"+recordSearched_int);
        Assert.assertTrue(String.valueOf(firstRecordFoundTwo).equals(String.valueOf(firstRecordFoundOne)));
    }

    @Test(priority = 3)
    public void SearchOnsiteDriversWhseLevelTest() throws InterruptedException {
        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "sel_accountType_chosen","Onsite"));

        click(driver.findElement(By.id("driverOnsiteYes")));

        //Click on the search button using xpath
        click(driver.findElement(By.xpath("//button[@id='btn_basicSearch']")));

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));

        //check if more than one record was returned, if less than 0, Assert will come back false
        int offNumDos = Integer.parseInt(driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getText());

        //
        offNumOnsiteTotal =driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getAttribute("value");
        Assert.assertTrue(offNumDos>expectednumber);

    }
    @Test(priority = 4)
    public void SearchEntLevelTest() throws InterruptedException {
        Assist.chooseModule(driver, "Driver Maintenance");
        Assist.chooseWarehouse(driver, entNum);

        //Send driver value to the driver basic search box
        //driver.findElement(By.id("txt_accountType")).sendKeys(driverName);
        sendId(driver,"txt_accountType",driverName);

        //Click on the search button using xpath
        click(driver.findElement(By.xpath("//button[@id='btn_basicSearch']")));

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));

        //check if more than one record was returned, if less than 0, Assert will come back false
        int offNum = Integer.parseInt(driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getText());
        offNumTotal = driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getAttribute("value");
        Assert.assertTrue (offNum>expectednumber);


    }

}