package com.uscold.mdmrepointaqa.test.customer;

import com.uscold.mdmrepointaqa.test.AbstractTestClass;
import com.uscold.mdmrepointaqa.test.utility.Assist;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.util.List;

/**
 * ********************************************
 * * Created by ${"Robert Morales"} on 1/23/2018.
 * ********************************************
 **/

public class CreateNewCustomerTest extends AbstractTestClass {

    final static String WHSE = "800 - BETHLEHEM";
    final static String entWHSE = "Enterprise";
    final static String WHSETEST = "8080808800 - BETHLEHEM";
    int WHSE_int =Integer.parseInt(WHSE.substring(1,3));
    final static String corporateName = "RE_POINT_TEST999";
    final static String addressLineOne = "1 INFINITE LOOP";
    final static String ciTy = "CUPERTINO";
    final static String staTe = "California";
    final static String zipCode = "95014";

    private String customerNumber = "";
    private String customerNumber1 = "100100";
    private String billTocustomerNumber = "";
    private String receivedFromcustomerNumber = "";


    @Test(priority = 1)
    public void createCustomerTest() throws InterruptedException {
        Assist.chooseModule(driver, "Customer Management");
//        Assist.chooseWarehouse(driver, 800);

        //Search BTs before test
        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "accountType_chosen", "Customer"));
        click(driver.findElement(By.id("searchOne")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));

        click(driver.findElement(By.id("createNewBtn")));

        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "type_chosen", "customer"));

        driver.findElement(By.id("corporateName")).sendKeys(corporateName);
        driver.findElement(By.id("txt_ConsigneeName")).sendKeys(corporateName);
        driver.findElement(By.id("addressLine1")).sendKeys(addressLineOne);
        driver.findElement(By.id("city")).sendKeys(ciTy);
        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "stateList_chosen", staTe));

        driver.findElement(By.id("zip")).clear();
        driver.findElement(By.id("zip")).sendKeys(zipCode);

        driver.findElement(By.id("txt_ConsigneeNumber")).clear();
        driver.findElement(By.id("txt_ConsigneeNumber")).sendKeys("300");

        customerNumber = driver.findElement(By.id("txt_ConsigneeNumber")).getAttribute("value");

        click(driver.findElement(By.id("basicDtlSubmit")));
        if (!driver.getCurrentUrl().toLowerCase().contains("customer/basicdetails/2/Next*//*.do")) {
            //if page wasn`t changed then it probably means we should verify address

            click(driver.findElement(By.id("addverify_enter")));
            driver.findElement(By.id("uspsCommentsDialog")).clear();
            driver.findElement(By.id("uspsCommentsDialog")).sendKeys("test");
            click(driver.findElement(By.id("saveBtn")));
            click(driver.findElement(By.id("basicDtlSubmit")));
        }

        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "customerClassification_chosen", "meats"));

        //Scrolling with Javascript was needed because the browser hides the page.
        JavascriptExecutor jsx = (JavascriptExecutor) driver;
        jsx.executeScript("window.scrollBy(0,450)", "");

        click(driver.findElement(By.id("additionalDtlSubmit")));

        WebElement statusMsg = driver.findElement(By.xpath("//span[@id='message']"));
        if (!statusMsg.isDisplayed() && !statusMsg.getText().toLowerCase().contains("created at the Enterprise"))
            throw new RuntimeException("Failed to create customer");

//        click(driver.findElement(By.id("cancelWhse")));
        //The tare is failing this logic was commented out for now.
//        click(driver.findElement(By.id("addDtlCancelCustomer")));
//
//        Alert alert = driver.switchTo().alert();
//        alert.accept();

        //Decline alert popup
        //Alert alert = driver.switchTo().alert();
        //alert().dismiss();



    }


    @Test(dependsOnMethods = "createCustomerTest",priority = 2 )
    public void pushToWarehouseLevel () {
        Select mySelect = new Select(driver.findElement(By.xpath(".//*[@id='hidebox']")));
        mySelect.selectByVisibleText(WHSE);
        click(driver.findElement(By.xpath("//input[@id='moveToRight']")));
        click(driver.findElement(By.xpath("//button[@id='done']")));

       //check if error isDisplayed if not it will check for success msg and click on the cancel to return to the landing page
        WebElement erroriPresence   = driver.findElement(By.id("openErrorDialog"));
        if (erroriPresence.isDisplayed()) {
//            System.out.println(erroriPresence);
            throw new RuntimeException("Error, The whse push failed"+erroriPresence);
        }else {
//        super.isElementFound("openErrorDialog");
            super.isElementFound("message");
            click(driver.findElement(By.id("cancelWhse")));
        }
//        WebElement statusMsg = driver.findElement(By.xpath("//span[@id='message']"));
//        if (!statusMsg.isDisplayed() && !statusMsg.getText().toLowerCase().contains("created at the Enterprise"))
//            throw new RuntimeException("Failed to create customer");


//        WebElement errMsg = driver.findElement(By.id("openErrorDialog"));
//        if (errMsg.isDisplayed()) {
//            throw new RuntimeException("Error, The whse push failed");
//        }else {
//           WebElement wReturned = driver.findElement(By.xpath(".//*[@id='hidebox']"));
//            if (!wReturned.isEnabled()) {
//                throw new RuntimeException("Failed to push to the whse level");
//
//            click(driver.findElement(By.id("cancelWhse")));
//        }
    }

//    @Test//(dependsOnMethods = "createCustomerTest",priority = 3)
//    public void viewCreatedCustomer() {
////        Assist.chooseModule(driver, "Customer Management");
//        Assist.chooseWarehouse(driver,entWHSE);
//        Assist.chooseModule(driver, "Customer Management");
////        Assist.waitForJSandJQueryToLoad(driver);
//        driver.findElement(By.id("txt_searchNumber")).clear();
//        driver.findElement(By.id("txt_searchNumber")).sendKeys(customerNumber);
//        click(driver.findElement(By.id("searchOne")));
//
//
////        click(driver.findElement(By.id("advSrchLink")));
////        driver.findElement(By.xpath(" //input[@id='accountNumber']")).clear();
////        driver.findElement(By.xpath(" //input[@id='accountNumber']")).sendKeys(customerNumber);
////        click(driver.findElement(By.id("searchTwo")));
//
//
//
//        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));
//
//
////        driver.findElement(By.id("txt_searchNumber")).sendKeys(customerNumber);
////        List<WebElement> lis = driver.findElements(By.xpath("//ul[@class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all']/li"));
////        click(lis.stream().filter(el -> el.getText().toLowerCase().contains(customerNumber)).findFirst().get());
//
//
//        WebElement table = driver.findElement(By.id("list"));
//        List<WebElement> rows = table.findElements(By.xpath(".//tr"));
//        WebElement row = rows.get(1);
//        click(row.findElement(By.xpath("td[@title='" + customerNumber + "']/a")));
//
//        WebElement columnContainer = driver.findElement(By.xpath("//div[@class='leftColumnContainer']"));
//
//        WebElement span = columnContainer.findElement(By.xpath("div[1]/span"));
//        String accountType = span.getText();
//
//        span = columnContainer.findElement(By.xpath("div[2]/span"));
//        String customerNumber = span.getText();
//
//        span = columnContainer.findElement(By.xpath("div[3]/span"));
//        String corporateName = span.getText();
//
//        org.testng.Assert.assertTrue(customerNumber.equals(customerNumber));
//        org.testng.Assert.assertTrue(accountType.equals("Customer"));
//        org.testng.Assert.assertTrue(corporateName.equals(corporateName));
//
//    }

//    @Test( priority = 3)
//    public void createBillToCustomerTest() throws InterruptedException {
//        Assist.chooseModule(driver, "Customer Management");
//        //        Assist.chooseWarehouse(driver, 800);
//
//        //Search BTs before test
//        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "accountType_chosen", "Bill To"));
//        click(driver.findElement(By.id("searchOne")));
//        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));
//
//        click(driver.findElement(By.id("createNewBtn")));
//
//        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "type_chosen", "Bill To"));
//
////        driver.findElement(By.id("txt_ConsigneeNumber")).clear();
////        driver.findElement(By.id("txt_ConsigneeNumber")).sendKeys(customerNumber + 1);
//
//        driver.findElement(By.id("corporateName")).sendKeys(corporateName);
//        driver.findElement(By.id("txt_ConsigneeName")).sendKeys(corporateName);
//        driver.findElement(By.id("addressLine1")).sendKeys(addressLineOne);
//        driver.findElement(By.id("city")).sendKeys(ciTy);
//        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "stateList_chosen", staTe));
//
//        driver.findElement(By.id("zip")).clear();
//        driver.findElement(By.id("zip")).sendKeys(zipCode);
//
//
//        billTocustomerNumber = driver.findElement(By.id("txt_ConsigneeNumber")).getAttribute("value");
//
//        click(driver.findElement(By.id("basicDtlSubmit")));
//        if (!driver.getCurrentUrl().toLowerCase().contains("customer/basicdetails/2/Next*//*.do")) {
//            //if page wasn`t changed then it probably means we should verify address
//            Assist.waitForJSandJQueryToLoad(driver);
//            click(driver.findElement(By.id("addverify_enter")));
//            driver.findElement(By.id("uspsCommentsDialog")).clear();
//            driver.findElement(By.id("uspsCommentsDialog")).sendKeys("test");
//            click(driver.findElement(By.id("saveBtn")));
//            click(driver.findElement(By.id("basicDtlSubmit")));
//        }
//
//        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "customerClassification_chosen", "meats"));
//
//
//        //Scrolling with Javascript was needed because the browser hides the page.
//        JavascriptExecutor jsx = (JavascriptExecutor)driver;
//        jsx.executeScript("window.scrollBy(0,450)", "");
//
//
//        click(driver.findElement(By.id("additionalDtlSubmit")));
//
//        WebElement statusMsg = driver.findElement(By.xpath("//span[@id='message']"));
//        if (!statusMsg.isDisplayed() && !statusMsg.getText().toLowerCase().contains("created at the Enterprise"))
//            throw new RuntimeException("Failed to create bill to customer at the enterprise level");
//
//        click(driver.findElement(By.id("cancelWhse")));
//        //     click(driver.findElement(By.id("addDtlCancelCustomer")));
//
//    }
//
//    @Test(dependsOnMethods = "createBillToCustomerTest", priority = 4)
//    public void viewCreatedBillToCustomer() {
////        Assist.waitForJSandJQueryToLoad(driver);
//        driver.findElement(By.id("txt_searchNumber")).clear();
//        driver.findElement(By.id("txt_searchNumber")).sendKeys(billTocustomerNumber);
//        click(driver.findElement(By.id("searchOne")));
//        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));
//
//        WebElement table = driver.findElement(By.id("list"));
//        List<WebElement> rows = table.findElements(By.xpath(".//tr"));
//        WebElement row = rows.get(1);
//        click(row.findElement(By.xpath("td[@title='" + billTocustomerNumber + "']/a")));
//
//        WebElement columnContainer = driver.findElement(By.xpath("//div[@class='leftColumnContainer']"));
//
//        WebElement span = columnContainer.findElement(By.xpath("div[1]/span"));
//        String accountType = span.getText();
//
//        span = columnContainer.findElement(By.xpath("div[2]/span"));
//        String customerNumber = span.getText();
//
//        span = columnContainer.findElement(By.xpath("div[3]/span"));
//        String corporateName = span.getText();
//
//        org.testng.Assert.assertTrue(customerNumber.equals(customerNumber));
//        org.testng.Assert.assertTrue(accountType.equals("Customer"));
//        org.testng.Assert.assertTrue(corporateName.equals(corporateName));
//
//    }
//
//    @Test( priority = 5)
//    public void createReceivedFromCustomerTest() throws InterruptedException {
//        Assist.chooseModule(driver, "Customer Management");
//        //        Assist.chooseWarehouse(driver, 800);
//
//        //Search BTs before test
//        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "accountType_chosen", "Received From"));
//        click(driver.findElement(By.id("searchOne")));
//        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));
//
//        click(driver.findElement(By.id("createNewBtn")));
//
//        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "type_chosen", "Received From"));
//
////        driver.findElement(By.id("txt_ConsigneeNumber")).clear();
////        driver.findElement(By.id("txt_ConsigneeNumber")).sendKeys(customerNumber + 1);
//
//        driver.findElement(By.id("corporateName")).sendKeys(corporateName);
//        driver.findElement(By.id("txt_ConsigneeName")).sendKeys(corporateName);
//        driver.findElement(By.id("addressLine1")).sendKeys(addressLineOne);
//        driver.findElement(By.id("city")).sendKeys(ciTy);
//        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "stateList_chosen", staTe));
//
//        driver.findElement(By.id("zip")).clear();
//        driver.findElement(By.id("zip")).sendKeys(zipCode);
//
//
//        receivedFromcustomerNumber = driver.findElement(By.id("txt_ConsigneeNumber")).getAttribute("value");
//
//        click(driver.findElement(By.id("basicDtlSubmit")));
//        if (!driver.getCurrentUrl().toLowerCase().contains("customer/basicdetails/2/Next*//*.do")) {
//            //if page wasn`t changed then it probably means we should verify address
//            Assist.waitForJSandJQueryToLoad(driver);
//            click(driver.findElement(By.id("addverify_enter")));
//            driver.findElement(By.id("uspsCommentsDialog")).clear();
//            driver.findElement(By.id("uspsCommentsDialog")).sendKeys("test");
//            click(driver.findElement(By.id("saveBtn")));
//            click(driver.findElement(By.id("basicDtlSubmit")));
//        }
//
////        click(Assist.chooseValueFromStandardDropDownByTextMatch(driver, "customerClassification_chosen", "meats"));
//
//
////        //Scrolling with Javascript was needed because the browser hides the page.
////        JavascriptExecutor jsx = (JavascriptExecutor)driver;
////        jsx.executeScript("window.scrollBy(0,450)", "");
////
////
////        click(driver.findElement(By.id("additionalDtlSubmit")));
//
//        WebElement statusMsg = driver.findElement(By.xpath("//span[@id='message']"));
//        if (!statusMsg.isDisplayed() && !statusMsg.getText().toLowerCase().contains("created at the Enterprise"))
//            throw new RuntimeException("Failed to create bill to customer at the enterprise level");
//
////        click(driver.findElement(By.id("cancelWhse")));
//        //     click(driver.findElement(By.id("addDtlCancelCustomer")));
//
//    }
//
//    @Test(dependsOnMethods = "createReceivedFromCustomerTest", priority = 6)
//    public void viewCreatedReceivedFromCustomer() {
////        Assist.waitForJSandJQueryToLoad(driver);
//        driver.findElement(By.id("txt_searchNumber")).clear();
//        driver.findElement(By.id("txt_searchNumber")).sendKeys(receivedFromcustomerNumber);
//        click(driver.findElement(By.id("searchOne")));
//        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));
//
//        WebElement table = driver.findElement(By.id("list"));
//        List<WebElement> rows = table.findElements(By.xpath(".//tr"));
//        WebElement row = rows.get(1);
//        click(row.findElement(By.xpath("td[@title='" + receivedFromcustomerNumber + "']/a")));
//
//        WebElement columnContainer = driver.findElement(By.xpath("//div[@class='leftColumnContainer']"));
//
//        WebElement span = columnContainer.findElement(By.xpath("div[1]/span"));
//        String accountType = span.getText();
//
//        span = columnContainer.findElement(By.xpath("div[2]/span"));
//        String customerNumber = span.getText();
//
//        span = columnContainer.findElement(By.xpath("div[3]/span"));
//        String corporateName = span.getText();
//
//        org.testng.Assert.assertTrue(customerNumber.equals(customerNumber));
//        org.testng.Assert.assertTrue(accountType.equals("Customer"));
//        org.testng.Assert.assertTrue(corporateName.equals(corporateName));
//
//    }

}
