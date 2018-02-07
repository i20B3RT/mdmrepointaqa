package com.uscold.mdmrepointaqa.test.gate;

import com.uscold.mdmrepointaqa.test.Abstract;
import com.uscold.mdmrepointaqa.test.utility.AssistPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.uscold.mdmrepointaqa.test.utility.AssistPage.sendInput;

public class GateArrivalDeparture extends Abstract {


    final static String WHSE = "800 - BETHLEHEM";
    int WHSE_int = Integer.parseInt(WHSE.substring(1, 3));
    final static String driverID = "BTH12345";
    final static String driverFirstName = "JOHN";
    final static String driverLastName = "SMITH";
    final static String driverContactNumber = "8888888888";
    final static String driverTractorNumber = "TRK";
    final static String trailerNumber = "TRL";
    final static String carrierNumber = "90090";
    final static String lengthVal = "53";
    final static String typeVal = "REEFER";
    final static String tempVal = "5";

    final static String WHSETEST = "800 - BETHLEHEM";
    final static String entNum = "Enterprise";

    //Validation and cache data
    int expectednumber = 1;
    private String offNumTotal = "";
    private String offNumOnsiteTotal = "";
    public String fistDriverFromGridList = "";
    private String firstRecordFoundOne = "";
    private String firstRecordFoundOne_int = "";
//    private String recordSearched = "";


    @Test(priority = 1, description = "TC: Search drivers at the whse level")
    public void GateArrivalTest() throws InterruptedException {

        extentTest = extent.startTest("TC: Gate Arrival with new driver and trailer at " + WHSETEST + ".");

        AssistPage.chooseModule(driver, "Gate Arrival Departure");
        AssistPage.chooseWarehouse(driver, WHSE);

        //Click on the arrival tile box using xpath
//        click(driver.findElement(By.xpath("//div[@id='arrival']/div[contains(@class, 'boxAriDepImg')]/img[contains(@class, 'marginTop15')]/@src")));
        click(driver.findElement(By.xpath("//div[@id='arrival']/div[contains(@class, 'boxAriDepImg')]")));

        WebElement spiner = driver.findElement(By.xpath("//div[contains(@class, 'pageLoadingThrobber')]"));
        wait.until(ExpectedConditions.invisibilityOf(spiner));

        //Send driver value to the driver id and send id
//        driver.findElement(By.id("txt_driverID")).clear();
        sendInput(driver,"id", "txt_driverID", driverID);

        //Send driver value - first name
        driver.findElement(By.id("txt_driverFirstName")).clear();
        sendInput(driver,"id",  "txt_driverFirstName", driverFirstName);

        //Send driver value - last name
        driver.findElement(By.id("txt_driverLastName")).clear();
        sendInput(driver, "id", "txt_driverLastName", driverLastName);

        //Send driver value - driver contact
        driver.findElement(By.id("txt_driverContactNumber")).clear();
        sendInput(driver,"id",  "txt_driverContactNumber", driverContactNumber);


        //Fetch today's date and convert to a string plus_100.
        Calendar dateTwo = Calendar.getInstance();
        Date dateOne = dateTwo.getTime();
        DateFormat dateForm = new SimpleDateFormat("YYMMddhhmm");
        String tDay = dateForm.format(dateOne);

//        //Send driver value to the driver basic search box
//        driver.findElement(By.id("txt_tractor")).clear();
//        sendInput(driver, "txt_tractor", driverTractorNumber);


        //Clear and enter tractor number
        driver.findElement(By.id("txt_tractor")).clear();
        driver.findElement(By.id("txt_tractor")).sendKeys(driverTractorNumber + tDay);

        //Click on appt check box
        click(driver.findElement(By.id("appointmentcheckbox")));

        //Clear and enter trailer number
        driver.findElement(By.id("txt_trailer")).clear();
        driver.findElement(By.id("txt_trailer")).sendKeys(trailerNumber + tDay);


        //Clear and send carrier value
        driver.findElement(By.id("txt_carrier_Arr")).clear();
        sendInput(driver,"id",  "txt_carrier_Arr", carrierNumber+Keys.TAB);

        driver.findElement(By.id("txt_carrier_Arr")).sendKeys(Keys.TAB);

        click(driver.findElement(By.id("txt_trailer")));

        //Select length from dropdown
        click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "txt_trailerLengthArr_chosen", lengthVal));

        //Select length from dropdown
        click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "txt_trailerTypeArr_chosen", typeVal));

        //Send driver set temp
        sendInput(driver, "id", "txt_setTemp", tempVal);

        //Send driver actual temp
        sendInput(driver, "id", "txt_actualTemp", tempVal);

        //Click adn send tab key to set fuel to 1/4
        driver.findElement(By.xpath("//a[@class='ui-slider-handle ui-state-default ui-corner-all']")).sendKeys(Keys.ARROW_RIGHT);
//        sendInput(driver,"xp","//a[@class='ui-slider-handle ui-state-default ui-corner-all']",Keys.ARROW_RIGHT);

        //Click on no issues check box
        click(driver.findElement(By.xpath("//input[@id='chk_noIssues']")));
//        driver.findElement(By.xpath("//input[@id='chk_noIssues']")).sendKeys();
//        WebElement spiner = driver.findElement(By.id("load_list"));
//        wait.until(ExpectedConditions.invisibilityOf(spiner));

//        WebElement NewDriverIdUno = driver.findElement(By.id("txt_ConsigneeNumber")).getAttribute("value");
//        WebElement NewDriverIdDos = driver.findElement(By.id("txt_ConsigneeNumber")).getAttribute("text");
//        WebElement NewDriverIdTres = driver.findElement(By.id("txt_ConsigneeNumber")).getText();

        //Click on arrive button
        click(driver.findElement(By.id("btn_submit")));

        WebElement statusMsg = driver.findElement(By.xpath("//span/div[contains(@class, 'successMsg ng-binding')]"));
        if (!statusMsg.isDisplayed() && !statusMsg.getText().toLowerCase().contains(trailerNumber)) {
            throw new RuntimeException("Failed to create bill to customer at the enterprise level");
        } else if (!statusMsg.isDisplayed() && !statusMsg.getText().contains(driverFirstName)) {
//            if (!statusMsg.isDisplayed() && !statusMsg.getText().contains(driverFirstName))
            throw new RuntimeException("Failed to create bill to customer at the enterprise level");
        } else if (!statusMsg.isDisplayed() && !statusMsg.getText().contains(driverLastName)) {
            //           if (!statusMsg.isDisplayed() && !statusMsg.getText().contains(driverLastName))
            throw new RuntimeException("Failed to create bill to customer at the enterprise level");
        }


    }

}
