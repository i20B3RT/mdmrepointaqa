package com.uscold.mdmrepointaqa.test.driver;

import com.uscold.mdmrepointaqa.test.Abstract;
import com.uscold.mdmrepointaqa.test.utility.AssistPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.uscold.mdmrepointaqa.test.utility.AssistPage.sendInput;


public class DriverMaintenanceSearchTests extends Abstract {


//    AssistPage.date(driver);
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
    final static String driverID = "BTH";
    final static String driverFirstName = "JOHN";
    final static String driverLastName = "SMITH";
    final static String driverContactNumber = "8888888888";
    final static String driverTractorNumber = "TRK";
    final static String trailerNumber = "TRL";
    final static String carrierNumber = "90090";

    //Fetch today's date and convert to a string plus_100.
    Calendar dateTres = Calendar.getInstance();
    Date dateUno = dateTres.getTime();
    DateFormat dateForm = new SimpleDateFormat("YYMMddhhmm");
    String toDay = dateForm.format(dateUno);

//    @Test(priority = 1,description = "TC: Search drivers at the whse level")
//    public void CreateNewDriverTest() {
//
//        AssistPage.chooseModule(driver, "Driver Maintenance");
//        AssistPage.chooseWarehouse(driver, WHSE);
//
//        //Click on the search button using xpath
//        click(driver.findElement(By.id("createNewBtn")));
//
//        sendInput(driver,"driverFirstName",driverFirstName);
//
//        sendInput(driver,"driverLastName",driverLastName);
//
//        sendInput(driver,"driverId",driverID+toDay);
//
//    }


    @Test(priority = 2,description = "TC: Search drivers at the whse level")
    public void SearchWhseLevelTest()  {

        AssistPage.chooseModule(driver, "Driver Maintenance");
        AssistPage.chooseWarehouse(driver, WHSE);

        //Send driver value to the driver basic search box
        //driver.findElement(By.id("txt_accountType")).sendKeys(driverName);

        sendInput(driver,"id", "txt_accountType",driverName);

        //Click on the search button using xpath
        click(driver.findElement(By.xpath("//button[@id='btn_basicSearch']")));

        //Wait on spinner is no longer displaying
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));

        //check if more than one record was returned, if less than 0, Assert will come back false
        int offNum = Integer.parseInt(driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getText());
        offNumTotal = driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getAttribute("value");
        Assert.assertTrue(offNum > expectednumber,"No driver records were returned whit the name: "+driverName);

        firstRecordFoundOne = driver.findElement(By.xpath("//tr[2][@class='ui-widget-content jqgrow ui-row-ltr']/td[3]")).getText();
        System.out.print("[AssistPage] [INFO] this driver record was cached: "+firstRecordFoundOne+" for searching with driver number at the whse level"+ System.lineSeparator());
    }

    @Test(dependsOnMethods = "SearchWhseLevelTest",priority = 3 )
    public void DriverSearchWithDriverNumberFirstDriverReturedFromWhseLevelTest() throws InterruptedException {
        //Select from dropdown
        click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "sel_accountType_chosen","Driver #"));

        //Clear an send value from above test
        driver.findElement(By.id("txt_accountType")).clear();
        //System.out.print("This was used to search: "+firstRecordFoundOne);
        //driver.findElement(By.id("txt_accountType")).sendKeys(firstRecordFoundOne);
        sendInput(driver,"id", "txt_accountType",firstRecordFoundOne);

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
        click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "sel_accountType_chosen","Onsite"));

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
    @Test(priority = 5)
    public void SearchEntLevelTest() throws InterruptedException {
        AssistPage.chooseModule(driver, "Driver Maintenance");
        AssistPage.chooseWarehouse(driver, entNum);

        //Send driver value to the driver basic search box
        //driver.findElement(By.id("txt_accountType")).sendKeys(driverName);
        sendInput(driver,"id", "txt_accountType",driverName);

        //Click on the search button using xpath
        click(driver.findElement(By.xpath("//button[@id='btn_basicSearch']")));

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));

        //check if more than one record was returned, if less than 0, Assert will come back false
        int offNum = Integer.parseInt(driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getText());
        offNumTotal = driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getAttribute("value");
        Assert.assertTrue (offNum>expectednumber);


    }

}