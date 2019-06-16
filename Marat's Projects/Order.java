

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.opera.OperaDriver;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Order {

    static WebDriver driver;
    public static void main(String[] args) {


        WebDriverManager.operadriver().setup();

        driver = new OperaDriver();

        driver.manage().window().maximize();

        String url = "http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx";

        driver.get(url);

        WebElement login = driver.findElement(By.xpath("//input[@id='ctl00_MainContent_username']"));
        login.sendKeys("Tester");


        WebElement password = driver.findElement(By.xpath("//input[@id='ctl00_MainContent_password']"));
        password.sendKeys("test");


        WebElement buttonLogin = driver.findElement(By.id("ctl00_MainContent_login_button"));
        buttonLogin.click();

        WebElement pushOrder = driver.findElement(By.linkText("Order"));
        pushOrder.click();


        Faker faker = new Faker();

        String randomNumUntil_100 = faker.random().nextInt(1, 100).toString();


        WebElement quantityNum = driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity"));
        quantityNum.sendKeys(randomNumUntil_100);


        String fakerFirstName = faker.name().firstName();
        String fakerMiddleName = faker.name().firstName();
        String fakerlastName = faker.name().lastName();

        String fullName = String.format("%s %s %s", fakerFirstName, fakerMiddleName, fakerlastName);


        String streetAddress = faker.address().streetAddress();
        String cityName = faker.address().cityName();
        String stateName = faker.address().state();
        String zipcode = faker.address().zipCode();
        String croppedZip = zipcode.substring(0, 5);


        String GenerateCardNumber = faker.finance().creditCard();
        String cardNumStartsWith5 = "5" + GenerateCardNumber.substring(1);

        String splitting[] = cardNumStartsWith5.split("-");
        StringBuilder editedCardNumStartsWith5 = new StringBuilder();

        for (int i = 0; i < splitting.length; i++) {
            editedCardNumStartsWith5.append(splitting[i]);

        }
        String masterCardNum = editedCardNumStartsWith5.toString();

        WebElement enterName = driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtName"));
        enterName.sendKeys(fullName);

        WebElement enterAddress = driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox2"));
        enterAddress.sendKeys(streetAddress);

        WebElement enterCity = driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox3"));
        enterCity.sendKeys(cityName);

        WebElement enterState = driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox4"));
        enterState.sendKeys(stateName);

        WebElement enterZipCode = driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox5"));
        enterZipCode.sendKeys(croppedZip);


        WebElement cardType = driver.findElement(By.xpath("//input[@id='ctl00_MainContent_fmwOrder_cardList_1']"));
        cardType.click();

        WebElement enterCardNum = driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6"));
        enterCardNum.sendKeys(masterCardNum);


        String expirationInput = "";
        int expMonth = faker.random().nextInt(1, 12);
        int expYear = faker.random().nextInt(10, 30);

        if (expMonth < 10) {
            expirationInput = "0" + expMonth + "/" + expYear;

        } else if (expMonth >= 10 || expMonth <= 12) {
            expirationInput = expMonth + "/" + expYear;
        }


        WebElement enterExpDate = driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox1"));
        enterExpDate.sendKeys(expirationInput);

        WebElement processButton = driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton"));
        processButton.click();


        String expectedvalidateText= "New order has been successfully added.";
        String actualvalidateText = driver.findElement(By.xpath("//input[@type='reset']/following-sibling::br/following-sibling::br/following-sibling::strong")).getText(); ////input[@type='reset']/following-sibling::br/following-sibling::br/following-sibling::strong


//        System.out.println("The Page contains: " + actualvalidateText.getText());

        // String expectedUrl=("http://secure.smartbearsoftware.com/samples/testcomplete12/weborders/Process.aspx");

        // String actualUrl=

        Assert.assertEquals(actualvalidateText, expectedvalidateText);



    }
}
