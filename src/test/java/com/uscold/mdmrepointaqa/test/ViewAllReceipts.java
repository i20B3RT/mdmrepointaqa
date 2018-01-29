package com.uscold.mdmrepointaqa.test;

import com.uscold.mdmrepointaqa.test.utility.Assist;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.List;


public class ViewAllReceipts extends AbstractTestClass {

    @Test
    public void viewAllReceipts() throws InterruptedException {
        Assist.chooseModule(driver, "Receipt Maintenance");
        Assist.chooseWarehouse(driver, 160);
        Assist.chooseCustomer(driver, 100950);
        click(driver.findElement(By.id("basicSrch")));

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("load_list"))));

        WebElement tableEl = driver.findElement(By.id("list"));
        List<WebElement> trs = tableEl.findElements(By.tagName("tr"));
        Assert.assertTrue("There are no receipts in the table", trs.size() > 1);
    }

}
