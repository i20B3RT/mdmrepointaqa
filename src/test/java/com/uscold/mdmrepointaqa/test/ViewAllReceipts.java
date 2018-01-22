package com.uscold.mdmrepointaqa.test;

import com.uscold.mdmrepointaqa.test.util.PageHelper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.List;


public class ViewAllReceipts extends AbstractTestClass {

    @Test
    public void viewAllReceipts() throws InterruptedException {
        PageHelper.chooseModule(driver, "Receipt Maintenance");
        PageHelper.chooseWarehouse(driver, 160);
        PageHelper.chooseCustomer(driver, 100950);
        click(driver.findElement(By.id("basicSrch")));

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));

        WebElement tableEl = driver.findElement(By.id("list"));
        List<WebElement> trs = tableEl.findElements(By.tagName("tr"));
        Assert.assertTrue("There are no receipts in the table", trs.size() > 1);
    }

}
