package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import utilities.BrowserUtils;
import utilities.ConfigurationReader;
import utilities.Driver;

import java.io.IOException;

public class TestBase {
    protected Actions actions;
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected ExtentReports report;
    protected ExtentHtmlReporter htmlReporter;
    protected ExtentTest extendLogger;

    @BeforeTest
    public void setUpTest(){
        report = new ExtentReports();

        String filePath= System.getProperty("user.dir")+"/test-output/report.html";
        htmlReporter = new ExtentHtmlReporter(filePath);
        report.attachReporter(htmlReporter);

        htmlReporter.config().setReportName("BriteERP automated test reports");
        report.setSystemInfo("Enviroment", "54.148.96.210");
        report.setSystemInfo("OS", System.getProperty("os.name"));
        report.setSystemInfo("Browser", ConfigurationReader.get("browser"));
        report.setSystemInfo("Test Engineer","Ariuka Munkhbat");

    }

    @AfterTest
    public void tearDownTest(){
        report.flush();

    }
    @BeforeMethod
    public void setUpMethod() {
        //initializes the webdriver object in test base clss using  the
        //driver utility
        driver = Driver.get();

        //setting implicit wait--> when elements nt found, it will
        //keep trying to find it
       // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //setup the explicit wait object.
        wait = new WebDriverWait(driver, 10);

        //actions class enable advanced interactions like double click and drag and drop
        actions = new Actions(driver);

    }

    @AfterMethod
    public void tearDownMethod(ITestResult result) throws InterruptedException, IOException {
       //if the test failed
        if(result.getStatus()== ITestResult.FAILURE){
           //record the failed test
           // extendLogger.fail(result.getName());

            //take screenshot and add it to report
            String screenshotLocation = BrowserUtils.getScreenshot(result.getName());
            extendLogger.addScreenCaptureFromPath(screenshotLocation);

            //capture the exception
            extendLogger.fail(result.getThrowable());
        }else if(result.getStatus()== ITestResult.SKIP){
            extendLogger.skip("Test case skipper: "+result.getName());
        }

        Thread.sleep(4000);
        Driver.closeDriver();
    }




}
