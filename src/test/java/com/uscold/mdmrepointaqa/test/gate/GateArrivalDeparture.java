package com.uscold.mdmrepointaqa.test.gate;

import com.uscold.mdmrepointaqa.test.AbstractTestClass;
import com.uscold.mdmrepointaqa.test.utility.AssistPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.uscold.mdmrepointaqa.test.utility.AssistPage.sendId;

public class GateArrivalDeparture  extends AbstractTestClass {


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
    int expectednumber = 1;
    private String offNumTotal = "";
    private String offNumOnsiteTotal = "";
    public String fistDriverFromGridList = "";
    private String firstRecordFoundOne = "";
    private String firstRecordFoundOne_int ="";
//    private String recordSearched = "";


    @Test(priority = 1,description = "TC: Search drivers at the whse level")
    public void GateArrivalTest() throws InterruptedException  {

        AssistPage.chooseModule(driver, "Gate Arrival Departure");
        AssistPage.chooseWarehouse(driver, WHSE);

        //Click on the arrival tile box using xpath
//        click(driver.findElement(By.xpath("//div[@id='arrival']/div[contains(@class, 'boxAriDepImg')]/img[contains(@class, 'marginTop15')]/@src")));
        click(driver.findElement(By.xpath("//div[@id='arrival']/div[contains(@class, 'boxAriDepImg')]")));

        WebElement spiner = driver.findElement(By.xpath("//div[contains(@class, 'pageLoadingThrobber')]"));
        wait.until(ExpectedConditions.invisibilityOf(spiner));

        //Send driver value to the driver id and send id
        driver.findElement(By.id("txt_driverID")).clear();
        sendId(driver, "txt_driverID", driverID);

        //Send driver value - first name
        driver.findElement(By.id("txt_driverFirstName")).clear();
        sendId(driver, "txt_driverFirstName", driverFirstName);

        //Send driver value - last name
        driver.findElement(By.id("txt_driverLastName")).clear();
        sendId(driver, "txt_driverLastName", driverLastName);

        //Send driver value - driver contact
        driver.findElement(By.id("txt_driverContactNumber")).clear();
        sendId(driver, "txt_driverContactNumber", driverContactNumber);


        //Fetch today's date and convert to a string plus_100.
        Calendar dateTwo = Calendar.getInstance();
        Date dateOne = dateTwo.getTime();
        DateFormat dateForm = new SimpleDateFormat("YYMMddhhmm");
        String tDay = dateForm.format(dateOne);

//        //Send driver value to the driver basic search box
//        driver.findElement(By.id("txt_tractor")).clear();
//        sendId(driver, "txt_tractor", driverTractorNumber);


        //Clear and enter tractor number
        driver.findElement(By.id("txt_tractor")).clear();
        driver.findElement(By.id("txt_tractor")).sendKeys(driverTractorNumber + tDay);

        //Click on appt check boz
        click(driver.findElement(By.id("appointmentcheckbox")));

        //Clear and enter trailer number
        driver.findElement(By.id("txt_trailer")).clear();
        driver.findElement(By.id("txt_trailer")).sendKeys(trailerNumber + tDay);


        //Clear and send carrier value
        driver.findElement(By.id("txt_carrier_Arr")).clear();
        sendId(driver, "txt_carrier_Arr", carrierNumber);

        click(driver.findElement(By.id("txt_trailer")));

        //Select length from dropdown
        click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "txt_trailerLengthArr", lengthVal));
        click(AssistPage.chooseValueFromStandardDropDownBySubstring(driver, "txt_trailerLengthArr", lengthVal));
//        click(AssistPage.clickOnDropDownLabel(driver,"txt_trailerLengthArr",lengthVal));

        //Select type from dropdown
        click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "txt_trailerTypeArr", typeVal));
//        click(AssistPage.chooseValueFromStandardDropDownBySubstring(driver, "txt_trailerTypeArr", typeVal));
//        click(AssistPage.clickOnDropDownLabel(driver,"txt_trailerTypeArr",lengthVal));

        //Send driver set temp
        sendId(driver, "txt_setTemp", tempVal);

        //Send driver actual temp
        sendId(driver, "txt_actualTemp", tempVal);

        //Click fuel 1/4
        click(driver.findElement(By.xpath("//a[contains(@class, 'ui-slider-handle ui-state-default ui-corner-all ui-state-focus ui-state-active ui-state-hover')]")));

        //Click on no issues check box
        click(driver.findElement(By.id("chk_noIssues")));

//        WebElement spiner = driver.findElement(By.id("load_list"));
//        wait.until(ExpectedConditions.invisibilityOf(spiner));

        //Click on arrive button
        click(driver.findElement(By.id("id=btn_submit")));

        WebElement statusMsg = driver.findElement(By.xpath("//span/div[contains(@class, 'successMsg ng-binding')]"));
        if (!statusMsg.isDisplayed() && !statusMsg.getText().toLowerCase().contains(trailerNumber)) {
            throw new RuntimeException("Failed to create bill to customer at the enterprise level");
        }else if (!statusMsg.isDisplayed() && !statusMsg.getText().contains(driverFirstName)){
//            if (!statusMsg.isDisplayed() && !statusMsg.getText().contains(driverFirstName))
                throw new RuntimeException("Failed to create bill to customer at the enterprise level");
        }else if (!statusMsg.isDisplayed() && !statusMsg.getText().contains(driverLastName)){
 //           if (!statusMsg.isDisplayed() && !statusMsg.getText().contains(driverLastName))
                throw new RuntimeException("Failed to create bill to customer at the enterprise level");
        }
    }
}
