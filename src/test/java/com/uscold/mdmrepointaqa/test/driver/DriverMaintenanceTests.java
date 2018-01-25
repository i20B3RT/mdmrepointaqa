package com.uscold.mdmrepointaqa.test.driver;

import com.uscold.mdmrepointaqa.test.AbstractTestClass;
import com.uscold.mdmrepointaqa.test.util.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

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



    @Test(priority = 1)
    public void SearchWhseLevelTest() throws InterruptedException {
        PageHelper.chooseModule(driver, "Driver Maintenance");
        PageHelper.chooseWarehouse(driver, WHSE);

        //Send driver value to the driver basic search box
        driver.findElement(By.id("txt_accountType")).sendKeys(driverName);
        //Click on the search button using xpath
        click(driver.findElement(By.xpath("//button[@id='btn_basicSearch']")));

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));

        //check if more than one record was returned, if less than 0, Assert will come back false
        int offNum = Integer.parseInt(driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getText());
        offNumTotal = driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getAttribute("value");
        Assert.assertTrue(offNum > expectednumber,"No driver records were returned whit the name: "+driverName);

        //This will bring back the first record from column 3
        fistDriverFromGridList =driver.findElement(By.xpath("//tr[2][@class='ui-widget-content jqgrow ui-row-ltr']/td[3]")).getAttribute("value");

        int firstRecordFoundOne = Integer.parseInt(driver.findElement(By.xpath("//tr[2][@class='ui-widget-content jqgrow ui-row-ltr']/td[3]")).getText());
        String firstRecordFoundOne_int = String.valueOf(firstRecordFoundOne);
    }

    @Test(dependsOnMethods = "SearchWhseLevelTest",priority = 2 )
    public void SearchFirstDriverReturedFromWhseLevelTest() throws InterruptedException {
        click(PageHelper.chooseValueFromStandardDropDownByTextMatch(driver, "sel_accountType_chosen","Driver #"));

        //Send driver value to the driver basic search box, this value is caching on the tests above and when it sends it sends null
        driver.findElement(By.id("txt_accountType")).sendKeys(String.valueOf(fistDriverFromGridList));
        driver.findElement(By.id("txt_accountType")).sendKeys(String.valueOf(firstRecordFoundOne));
        driver.findElement(By.id("txt_accountType")).sendKeys(String.valueOf(firstRecordFoundOne_int));
        //Click on the search button using xpath
        click(driver.findElement(By.xpath("//button[@id='btn_basicSearch']")));

        final String recordSearched = driver.findElement(By.id("txt_accountType")).getAttribute("value");
        int recordSearched_int = Integer.parseInt(recordSearched);

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));


//        click(driver.findElement(By.id("driverOnsiteYes")));
//
//        //Click on the search button using xpath
//        click(driver.findElement(By.xpath("//button[@id='btn_basicSearch']")));
//
//        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));

        //check if more than one record was returned, if less than 0, Assert will come back false
         //int firstRecordFound = Integer.parseInt(driver.findElement(By.xpath("//tr[2][@class='ui-widget-content jqgrow ui-row-ltr']/td[3]")).getText());

//        final String firstRecordFound =driver.findElement(By.xpath("//tr[2][@class='ui-widget-content jqgrow ui-row-ltr']/td[3]")).getAttribute("value");

        int firstRecordFoundTwo = Integer.parseInt(driver.findElement(By.xpath("//tr[2][@class='ui-widget-content jqgrow ui-row-ltr']/td[3]")).getText());
         //final String rOne = firstRecordFound;
//        final static String WHSE = "800 - BETHLEHEM";
//        int WHSE_int = Integer.parseInt(WHSE);

        //firstDriverReturnedFromSearch =driver.findElement(By.xpath("//tr[2][@class='ui-widget-content jqgrow ui-row-ltr']/td[3]")).getAttribute("value");

       // Assert.assertTrue(rOne=fistDriverFromGridList,"The driver searched with the drive number was not returned");

        // Assertion type 1
        //Assert.assertEquals(String.valueOf(firstRecordFoundTwo),String.valueOf(firstRecordFoundOne));

        System.out.print("Record returned from driver number search: "+firstRecordFoundTwo);
        System.out.print("||||||| Record cache from the whse test"+recordSearched_int);
        Assert.assertTrue(String.valueOf(firstRecordFoundTwo).equals(String.valueOf(recordSearched_int)));
    }

//    @Test(priority = 3)
//    public void SearchOnsiteDriversWhseLevelTest() throws InterruptedException {
//    click(PageHelper.chooseValueFromStandardDropDownByTextMatch(driver, "sel_accountType_chosen","Onsite"));
//
//    click(driver.findElement(By.id("driverOnsiteYes")));
//
//    //Click on the search button using xpath
//    click(driver.findElement(By.xpath("//button[@id='btn_basicSearch']")));
//
//        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));
//
//    //check if more than one record was returned, if less than 0, Assert will come back false
//    int offNumDos = Integer.parseInt(driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getText());
//    offNumOnsiteTotal =driver.findElement(By.xpath("//span[@id='sp_1_pager']")).
//
//    getAttribute("value");
//    Assert.assertTrue(offNumDos>expectednumber);
//
//}
//    @Test(priority = 4)
//    public void SearchEntLevelTest() throws InterruptedException {
//        PageHelper.chooseModule(driver, "Driver Maintenance");
//        PageHelper.chooseWarehouse(driver, entNum);
//
//        //Send driver value to the driver basic search box
//        driver.findElement(By.id("txt_accountType")).sendKeys(driverName);
//        //Click on the search button using xpath
//        click(driver.findElement(By.xpath("//button[@id='btn_basicSearch']")));
//
//        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));
//
//        //check if more than one record was returned, if less than 0, Assert will come back false
//        int offNum = Integer.parseInt(driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getText());
//        offNumTotal = driver.findElement(By.xpath("//span[@id='sp_1_pager']")).getAttribute("value");
//        Assert.assertTrue (offNum>expectednumber);
//
//
//    }
}
