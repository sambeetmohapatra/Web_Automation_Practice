
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.stream.Collectors;

public class UI_Automation {

    static String url ="https://www.eviltester.com/page/tools/testpages/";

    public static void main(String[] args) {
        ChromeDriver driver= new ChromeDriver();
        driver.get(url);
        List<WebElement> headers = driver.findElements(By.xpath("//li[@class='rootdropdownmenuitem']/a"));
        String expectedVal="Books";
        boolean condition=headers.stream().anyMatch(e->e.getText().contains(expectedVal));
        System.out.println(condition);
        driver.quit();
     }
}
