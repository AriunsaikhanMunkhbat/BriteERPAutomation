package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NavigationBar;
import utilities.BrowserUtils;
import utilities.ConfigurationReader;
import utilities.TestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExpectedRevenueMatch extends TestBase {

    @BeforeMethod
    public void setUpMethod2(){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(ConfigurationReader.get("url"));
        String username = ConfigurationReader.get("crmmanager_username");
        String password = ConfigurationReader.get("crmmanager_password");
        LoginPage loginPage= new LoginPage();
        loginPage.login(username, password);
    }

    @Test(description = "Verify that second opportunity’ Expected Revenue value on the " +
            "Pivot board should be the sameas the Expected revenue column value on the list board. ")
    public void expectedRevenueMatch(){
        //give name of the test
        extendLogger = report.createTest("Checking expected revenue matches on list and pivot table view");
        extendLogger.info("Clicking on CRM tab");
        NavigationBar navigationBar = new NavigationBar();
        navigationBar.getTab("CRM").click();
        extendLogger.info("Clicking on Pivot table view");
        navigationBar.pivotTableView.click();
        extendLogger.info("Clicking on Total and selecting Opportunity");
        WebElement total= driver.findElement(By.xpath("//td[1]"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", total);
        driver.findElement(By.xpath("//td[1]")).click();
        driver.findElement(By.xpath("//li[@data-field='name']")).click();
        extendLogger.info("Finding the price of the 2nd item from the table");
        WebElement price1= driver.findElement(By.xpath("//tr[3]/td[2]"));
        String revOnTable= price1.getText();
        extendLogger.info("Clicking on list view");
        navigationBar.listView.click();
        extendLogger.info("Locating the 2nd item's price on the list view");
        WebElement price2= driver.findElement(By.xpath("//tr[2]/td[9]"));
        String revOnList= price2.getText();
        extendLogger.info("Verifying two values matches");
        Assert.assertEquals(revOnList, revOnTable);
        extendLogger.pass("Values Match");

    }
    @Test(description = "Verify that on the pivot table, the total expected revenue should be " +
            "the sumof all opportunities’ expected revenue.")
    public void sumOfAllRevenue(){
        extendLogger = report.createTest("Checking expected revenue matches on list and pivot table view");
        extendLogger.info("Clicking on CRM tab");
        NavigationBar navigationBar = new NavigationBar();
        navigationBar.getTab("CRM").click();
        extendLogger.info("Clicking on Pivot table view");
        navigationBar.pivotTableView.click();
        extendLogger.info("Clicking on Total and selecting Opportunity");
        WebElement total= driver.findElement(By.xpath("//td[1]"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", total);
        driver.findElement(By.xpath("//td[1]")).click();
        driver.findElement(By.xpath("//li[@data-field='name']")).click();

        double expectedTotal =navigationBar.totalFromPivot();
        double actualTotal =navigationBar.sum();

        Assert.assertEquals(actualTotal, expectedTotal);


    }


}
