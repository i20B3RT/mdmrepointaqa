package com.uscold.mdmrepointaqa.test.inventory;

import com.uscold.mdmrepointaqa.test.AbstractTestClass;
import com.uscold.mdmrepointaqa.test.util.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;


public class InventorySearchTest extends AbstractTestClass {

    final static String WHSE_NUM = "800";
    final static String CUSTOMER_NAME = "1017900 - CUSTOMER FOR AUTOMATION";
    final static String USCS_Lot = "425309";
    final static String PRODUCT_CODE = "777";
    final static String Location = "01B 0014";
    final static String License = "41019001";
//License 41019001 is in WIP status


    @Test
    public void searchWarehouses() throws InterruptedException {
        PageHelper.chooseModule(driver, "Warehouse Management");
        driver.findElement(By.id("txt_searchNumber")).sendKeys(WHSE_NUM);
        click(driver.findElement(By.id("searchOne")));
//        WebElement spiner = driver.findElement(By.id("gbox_list"));
//        wait.until(ExpectedConditions.invisibilityOf(spiner));
        String cst = driver.findElement(By.xpath("//td/a")).getText();
        Assert.assertEquals(cst, WHSE_NUM);
    }

    @Test
    public void searchByCustomer() throws InterruptedException {
        PageHelper.chooseModule(driver, "Inventory Maintenance");
        PageHelper.chooseWarehouse(driver,800);
        PageHelper.chooseCustomer(driver,100500);
        click(driver.findElement(By.xpath("//span[@class='field_val selectCustomer']")));
        driver.findElement(By.xpath("//span[@class='field_val selectCustomer']//input[@type='text']")).sendKeys(CUSTOMER_NAME);
        click(driver.findElement(By.id("basicSrch")));
        WebElement spiner = driver.findElement(By.id("load_productGrid"));
        wait.until(ExpectedConditions.invisibilityOf(spiner));
        String cst = driver.findElement(By.xpath("//td[@title='1017900 - CUSTOMER FOR AUTOMATION']")).getText();
        Assert.assertEquals(cst, CUSTOMER_NAME);
    }



    @Test
    public void searchByProduct() throws InterruptedException {
        PageHelper.chooseModule(driver, "Inventory Maintenance");
        click(PageHelper.chooseValueFromStandardDropDownBySubstring(driver, "pem_cust_chosen", "select"));

        driver.findElement(By.id("txt_SubProduct")).sendKeys(PRODUCT_CODE);
        click(driver.findElement(By.id("basicSrch")));
        WebElement spiner = driver.findElement(By.id("load_productGrid"));
        wait.until(ExpectedConditions.invisibilityOf(spiner));
        String cst = driver.findElement(By.xpath("//td[@title='777']")).getAttribute("innerText");
        Assert.assertEquals(cst, PRODUCT_CODE);
    }


    @Test
    public void searchByLocation() throws InterruptedException {
        PageHelper.chooseModule(driver,"Inventory Maintenance");
        click(PageHelper.chooseValueFromStandardDropDownBySubstring(driver, "pem_cust_chosen", "select"));
        driver.findElement(By.id("location")).sendKeys(Location);
        click(driver.findElement(By.id("basicSrch")));
        WebElement spiner = driver.findElement(By.id("load_licenseGrid"));
        wait.until(ExpectedConditions.invisibilityOf(spiner));
        String cst = driver.findElement(By.xpath("//td[normalize-space() = '01B 0014']")).getText();
        Assert.assertEquals(cst, Location);
    }


    @Test
    public void searchByLicense() throws InterruptedException {
        PageHelper.chooseModule(driver,"Inventory Maintenance");
        click(PageHelper.chooseValueFromStandardDropDownBySubstring(driver, "pem_cust_chosen", "select"));
        driver.findElement(By.id("License")).sendKeys(License);
        click(driver.findElement(By.id("basicSrch")));
        WebElement spiner = driver.findElement(By.id("load_licenseGrid"));
        wait.until(ExpectedConditions.invisibilityOf(spiner));
        String cst = driver.findElement(By.xpath("//td[@title='41019001']")).getText();
        Assert.assertEquals(cst, License);
    }
}
