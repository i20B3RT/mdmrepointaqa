package com.uscold.mdmrepointaqa.test.inventory;

import com.uscold.mdmrepointaqa.test.AbstractTestClass;
import com.uscold.mdmrepointaqa.test.utility.Assist;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;


public class InventorySearchTest extends AbstractTestClass {

    final static String WHSE = "800 - BETHLEHEM";
    final static String CU = "100100";
    int CU_int =Integer.parseInt(CU);
    final static String C_NAME ="ACE BAKERY LTD";
//    final static String USCS_Lot = "425309";
    final static String P_CODE = "41208";
//    final static String Location = "01B 0014";
//    final static String License = "41019001";
//License 41019001 is in WIP status


    @Test
    public void searchWarehouses() throws InterruptedException {

        Assist.chooseModule(driver, "Warehouse Management");

        driver.findElement(By.id("txt_searchNumber")).sendKeys(WHSE);

        click(driver.findElement(By.id("searchOne")));

        WebElement spiner = driver.findElement(By.id("load_list"));

        wait.until(ExpectedConditions.invisibilityOf(spiner));
//        driver.findElement(By.id("OEDCREATEPOPUP-1_gCol_pCode")).sendKeys(PRODUCT_CODE.substring(0, 3));

        String cst = driver.findElement(By.xpath("//td[@title='" + WHSE.substring(0,3) + "'][1]")).getText();

        Assert.assertEquals(cst, WHSE.substring(0,3));

    }

    @Test(priority = 1)
    public void searchCustomer() throws InterruptedException {

        Assist.chooseModule(driver, "Customer Management");
        Assist.chooseWarehouse(driver,WHSE);
        driver.findElement(By.id("txt_searchNumber")).sendKeys(CU);
//        Assist.chooseCustomer(driver,cu_num);

        click(driver.findElement(By.id("searchOne")));
        WebElement spiner = driver.findElement(By.id("load_list"));
        wait.until(ExpectedConditions.invisibilityOf(spiner));

        String cst = driver.findElement(By.xpath("//td[@title='" + C_NAME + "'][1]")).getText();
        Assert.assertEquals(cst, C_NAME);

//        click(driver.findElement(By.xpath("//span[@class='field_val selectCustomer']")));
//        driver.findElement(By.xpath("//span[@class='field_val selectCustomer']//input[@type='text']")).sendKeys(CUSTOMER_NAME);
//        click(driver.findElement(By.id("basicSrch")));
//        WebElement spiner = driver.findElement(By.id("load_productGrid"));
//        wait.until(ExpectedConditions.invisibilityOf(spiner));
//        String cst = driver.findElement(By.xpath("//td[@title='1017900 - CUSTOMER FOR AUTOMATION']")).getText();
//        Assert.assertEquals(cst, CUSTOMER_NAME);
    }



    @Test(priority = 2)
    public void searchProduct() throws InterruptedException {
        Assist.chooseModule(driver, "Product Management");
        Assist.chooseWarehouse(driver,WHSE);
//        Assist.chooseCustomer(driver,CU_int);
        driver.findElement(By.id("codeOrDesc")).sendKeys(P_CODE);
        click(driver.findElement(By.id("searchOne")));
        WebElement spiner = driver.findElement(By.id("load_list"));
        wait.until(ExpectedConditions.invisibilityOf(spiner));
        String cst = driver.findElement(By.xpath("//td[@title='" + P_CODE + "'][1]")).getText();
        Assert.assertEquals(cst, P_CODE);


//        Assist.chooseModule(driver, "Inventory Maintenance");
//        click(Assist.chooseValueFromStandardDropDownBySubstring(driver, "pem_cust_chosen", "select"));
//
//        driver.findElement(By.id("txt_SubProduct")).sendKeys(PRODUCT_CODE);
//        click(driver.findElement(By.id("basicSrch")));
//        WebElement spiner = driver.findElement(By.id("load_productGrid"));
//        wait.until(ExpectedConditions.invisibilityOf(spiner));
//        String cst = driver.findElement(By.xpath("//td[@title='777']")).getAttribute("innerText");
//        Assert.assertEquals(cst, PRODUCT_CODE);
    }

}
