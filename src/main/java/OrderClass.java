import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

    public class OrderClass {

        static WebDriver driver;

        public static void main(String[] args) throws Exception{

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

            driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
            WebElement userNameBox = driver.findElement(By.xpath("//input[@id='ctl00_MainContent_username']"));
            userNameBox.sendKeys("Tester");

            WebElement passwordBox = driver.findElement(By.xpath("//input[@id='ctl00_MainContent_password']"));
            passwordBox.sendKeys("test");

            WebElement loginButton = driver.findElement(By.id("ctl00_MainContent_login_button"));
            loginButton.click();

            WebElement orderButton = driver.findElement(By.linkText("Order"));
            orderButton.click();

            WebElement quantityBox = driver.findElement(By.xpath("//input[@id='ctl00_MainContent_fmwOrder_txtQuantity']"));

            Random random = new Random();
            int n = random.nextInt(100);
            n+=1;
            quantityBox.sendKeys(Keys.BACK_SPACE);
            quantityBox.sendKeys(""+n);

            WebElement calculateButton = driver.findElement(By.xpath("//*[@id='ctl00_MainContent_fmwOrder']/tbody/tr/td/ol[1]/li[5]/input[2]"));
            calculateButton.click();


            Faker fake = new Faker();
            String name = fake.name().nameWithMiddle();

            WebElement customerNameBox = driver.findElement(By.xpath("//input[@name='ctl00$MainContent$fmwOrder$txtName']"));
            customerNameBox.sendKeys(name);

            WebElement streetBox = driver.findElement(By.xpath("//*[@id='ctl00_MainContent_fmwOrder_TextBox2']"));
            String street = fake.address().streetName();
            streetBox.sendKeys(street);

            WebElement cityBox = driver.findElement(By.xpath("//*[@id='ctl00_MainContent_fmwOrder_TextBox3']"));
            String city = fake.address().city();
            cityBox.sendKeys(city);


            WebElement zipCodeBox = driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox5"));
            String zipcodeNum = fake.address().zipCode();
            zipCodeBox.sendKeys(zipcodeNum.split("-"));

            WebElement visa = driver.findElement(By.xpath("//input[@id='ctl00_MainContent_fmwOrder_cardList_0']"));
            WebElement masterCard = driver.findElement(By.xpath("//*[@id='ctl00_MainContent_fmwOrder_cardList_1']"));
            WebElement americanExpress = driver.findElement(By.xpath("//*[@id='ctl00_MainContent_fmwOrder_cardList_2']"));

            WebElement cardNumber = driver.findElement(By.xpath("//*[@id='ctl00_MainContent_fmwOrder_TextBox6']"));
            int randNum = random.nextInt(2);
            long first14 = (long) (Math.random() * 1000000000000000L);




            switch (randNum){
                case 0:
                    visa.click();
                    long visaRandCardNumber = 4000000000000000L + first14;
                    cardNumber.sendKeys(visaRandCardNumber+"");
                    break;
                case 1:
                    masterCard.click();
                    long masterRandCardNumber =  5000000000000000L + first14;
                    cardNumber.sendKeys(masterRandCardNumber+"");
                    break;
                case 2:
                    americanExpress.click();
                    long amexRandCardNumber = 300000000000000L + first14;
                    cardNumber.sendKeys(amexRandCardNumber+"");
                    break;
            }

            WebElement expDate = driver.findElement(By.xpath("//*[@id='ctl00_MainContent_fmwOrder_TextBox1']"));
            String pattern = "MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());

            expDate.sendKeys(date);

            WebElement processButton = driver.findElement(By.linkText("Process"));
            processButton.click();

            String actualMessage = driver.findElement(By.xpath("//*[@id='ctl00_MainContent_fmwOrder']/tbody/tr/td/div/strong")).getText();
            String expectedMessage = "New order has been successfully added.";
            if (expectedMessage.equals(actualMessage)){
                System.out.println("PASSED");
            }else{
                System.out.println("FAILED");
            }

            Thread.sleep(5000);
            driver.quit();

        }
    }


