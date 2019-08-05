package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.ArrayList;
import java.util.List;

public class NavigationBar {
    WebDriver driver = Driver.get();

    public NavigationBar() {

        PageFactory.initElements(Driver.get(), this);
    }

    @FindBy(xpath = "//button[@aria-label='pivot']")
    public WebElement pivotTableView;

    @FindBy(xpath = "//button[@aria-label='list']")
    public WebElement listView;


    public WebElement getTab(String tab) {
        String tabXpath = "//span[@class='oe_menu_text' and contains(text(), '" + tab + "')]";
        return Driver.get().findElement(By.xpath(tabXpath));
    }

    public double sum() {
        List<WebElement> price = driver.findElements(By.xpath("//tbody/tr/td[2]"));
        List<String> str = new ArrayList<>();
        for (WebElement element : price) {
            str.add(element.getText().replace(",", ""));
        }
        double total = 0;
        for (String s : str) {
            total += Double.valueOf(s);
        }
        return total;
    }

    public double totalFromPivot() {
        String str = driver.findElement(By.xpath("//tr[1]/td[2]")).getText().replace(",", "");
        double result = Double.valueOf(str);
        return result;
    }
}
