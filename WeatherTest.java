package wheather;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;
public class WeatherTest {
    WebDriver driver;
    String temperatureText;
    int currentTemperature;
    String totaltext;
    int currentTotal;
    private static ExtentReports extent;
    private static ExtentTest test;


    @Before
    public void before_setup() throws MalformedURLException {
        WebDriverManager.chromedriver().setup();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        driver = new RemoteWebDriver(new URL("http://172.17.176.1:4444"), capabilities);
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("Report.html");
        extent = new ExtentReports();
        // extent.attachReporter((ExtentObserver) htmlReporter);
        test = extent.createTest("Weather Shopper Test", "Test to verify shopping on Weather Shopper site based on temperature");
    }

    @Given("I am on the Weather Shopper homepage")
    public void i_am_on_the_Weather_Shopper_homepage() {
        driver.get("https://weathershopper.pythonanywhere.com/");
        test.pass("given");
    }

    @When("the temperature is checked")
    public void the_temperature_is_checked() {
        WebElement temperatureElement = driver.findElement(By.id("temperature"));
        temperatureText = temperatureElement.getText();
        currentTemperature = Integer.parseInt(temperatureText.replaceAll("[^\\d]", "").trim());
        test.pass("when");
    }

    @Then("I choose the appropriate product category based on the temperature")
    public void i_choose_the_appropriate_product_category_based_on_the_temperature() {
        if (currentTemperature < 19) {
            WebElement moisturizersButton = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/a/button"));
            moisturizersButton.click();
        } else if (currentTemperature > 34) {
            WebElement sunscreensButton = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/a/button"));
            sunscreensButton.click();
        }
        test.pass("then");
    }
    @Then("I add the necessary products to the cart")
    public void i_add_the_necessary_products_to_the_cart() throws InterruptedException {
        List<WebElement> products, products1;

        if (currentTemperature < 19) {
            // Aloe products
            products = driver.findElements(By.xpath("//p[contains(text(), 'Aloe')]/following-sibling::p[contains(text(), 'Price')]/following-sibling::button"));
            PriorityQueue<WebElement> pqAloe = new PriorityQueue<>(Comparator.comparingInt(p -> {
                String priceText = p.findElement(By.xpath("preceding-sibling::p[contains(text(), 'Price')]")).getText();
                return Integer.parseInt(priceText.replaceAll("[^\\d]", "").trim());
            }));
            pqAloe.addAll(products);
            WebElement cheapestAloeProduct = pqAloe.peek(); // This will be the element with the lowest price
            if (cheapestAloeProduct != null) {
                cheapestAloeProduct.click();
                Thread.sleep(2000);
            } else {
                throw new AssertionError("No Aloe products found");
            }

            // Almond products
            products1 = driver.findElements(By.xpath("//p[contains(text(), 'Almond')]/following-sibling::p[contains(text(), 'Price')]/following-sibling::button"));
            PriorityQueue<WebElement> pqAlmond = new PriorityQueue<>(Comparator.comparingInt(p -> {
                String priceText1 = p.findElement(By.xpath("preceding-sibling::p[contains(text(), 'Price')]")).getText();
                return Integer.parseInt(priceText1.replaceAll("[^\\d]", "").trim());
            }));
            pqAlmond.addAll(products1);
            WebElement cheapestAlmondProduct = pqAlmond.peek(); // This will be the element with the lowest price
            if (cheapestAlmondProduct != null) {
                cheapestAlmondProduct.click();
                Thread.sleep(2000);
            } else {
                throw new AssertionError("No Almond products found");
            }

        } else if (currentTemperature > 34) {
            // SPF-30 products
            products = driver.findElements(By.xpath("//p[contains(text(), 'SPF-30')]/following-sibling::p[contains(text(), 'Price')]/following-sibling::button"));
            PriorityQueue<WebElement> pqSPF30 = new PriorityQueue<>(Comparator.comparingInt(p -> {
                String priceText = p.findElement(By.xpath("preceding-sibling::p[contains(text(), 'Price')]")).getText();
                return Integer.parseInt(priceText.replaceAll("[^\\d]", "").trim());
            }));
            pqSPF30.addAll(products);
            WebElement cheapestSPF30Product = pqSPF30.peek(); // This will be the element with the lowest price
            if (cheapestSPF30Product != null) {
                cheapestSPF30Product.click();
                Thread.sleep(2000);
            } else {
                throw new AssertionError("No SPF-30 products found");
            }

            // SPF-50 products
            products1 = driver.findElements(By.xpath("//p[contains(text(), 'SPF-50')]/following-sibling::p[contains(text(), 'Price')]/following-sibling::button"));
            PriorityQueue<WebElement> pqSPF50 = new PriorityQueue<>(Comparator.comparingInt(p -> {
                String priceText1 = p.findElement(By.xpath("preceding-sibling::p[contains(text(), 'Price')]")).getText();
                return Integer.parseInt(priceText1.replaceAll("[^\\d]", "").trim());
            }));
            pqSPF50.addAll(products1);
            WebElement cheapestSPF50Product = pqSPF50.peek(); // This will be the element with the lowest price
            if (cheapestSPF50Product != null) {
                cheapestSPF50Product.click();
                Thread.sleep(2000);
            } else {
                throw new AssertionError("No SPF-50 products found");
            }
        }
        test.pass("then");
    }


    @Then("I click on the cart")
    public void i_click_on_the_cart() {
        WebElement cartButton = driver.findElement(By.xpath("/html/body/nav/ul/button"));
        cartButton.click();
        test.pass("then");
    }

    @Then("I verify that the shopping cart looks correct")
    public void i_verify_that_the_shopping_cart_looks_correct() {
        // Verify cart has the correct items
        if (currentTemperature < 19) {

            WebElement aloeMoisturizerCart_Price = driver.findElement(By.xpath("/html/body/div[1]/div[2]/table/tbody/tr[1]/td[2]"));
            String aloetext =  aloeMoisturizerCart_Price.getText();
            int aloeprice=Integer.parseInt(aloetext);
            WebElement almondMoisturizerCart_Price = driver.findElement(By.xpath("/html/body/div[1]/div[2]/table/tbody/tr[2]/td[2]"));
            String almondtext =  almondMoisturizerCart_Price.getText();
            int almondprice=Integer.parseInt(almondtext);
            WebElement totalElement = driver.findElement(By.xpath("//*[@id=\"total\"]"));
            totaltext = totalElement.getText();
            currentTotal= Integer.parseInt(totaltext.replaceAll("[^0-9]", "").trim());
            int sum=aloeprice+almondprice;
            assertEquals(sum,currentTotal);

            ;




        } else if (currentTemperature > 34) {

            WebElement spf50SunscreenCart_Price = driver.findElement(By.xpath("/html/body/div[1]/div[2]/table/tbody/tr[2]/td[2]"));
            String spf50Text =  spf50SunscreenCart_Price.getText();
            int spf50price=Integer.parseInt(spf50Text);
            WebElement spf30SunscreenCart_Price = driver.findElement(By.xpath("/html/body/div[1]/div[2]/table/tbody/tr[1]/td[2]"));
            String spf30Text =   spf30SunscreenCart_Price.getText();
            int spf30price=Integer.parseInt(spf30Text);
            WebElement totalElement2 = driver.findElement(By.xpath("//*[@id=\"total\"]"));
            String totaltext2 = totalElement2.getText();
            int currentTotal2= Integer.parseInt(totaltext2.replaceAll("[^0-9]", "").trim());
            int sum2=spf30price+spf50price;
            assertEquals(sum2,currentTotal2);

        }
        test.pass("then");
    }

    @Then("I fill out my payment details")
    public void i_fill_out_my_payment_details() throws InterruptedException {
        WebElement CheckoutPage= driver.findElement(By.cssSelector("body > div.container.top-space-50"));
        CheckoutPage.click();
        WebElement payButton=driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/button"));
        payButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        // Switch to the iframe containing the payment form, if applicable
        WebElement iframe = driver.findElement(By.tagName("iframe"));
        driver.switchTo().frame(iframe);
        // Wait for the email input field to be visible and interactable
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        emailField.sendKeys("Waleed.ahmad@emumba.com");
        sleep(600);  // Wait for 500 milliseconds
        WebElement cardNumberField = wait.until(ExpectedConditions.elementToBeClickable(By.id("card_number")));
        String  cardNumber="5555555555554444";
        for (char digit : cardNumber.toCharArray()) {
            cardNumberField.sendKeys(String.valueOf(digit));
            sleep(100);  // Slight pause between each digit
        }
        sleep(700);  // Wait for 500 milliseconds after entering the card number
        WebElement expiryField = wait.until(ExpectedConditions.elementToBeClickable(By.id("cc-exp")));
        String expiry="72026";
        for (char digit : expiry.toCharArray()) {
            expiryField.sendKeys(String.valueOf(digit));
            sleep(100);  // Slight pause between each digit
        }
        sleep(500);  // Wait for 500 milliseconds
        WebElement cvvField = wait.until(ExpectedConditions.elementToBeClickable(By.id("cc-csc")));
        cvvField.sendKeys("1234");
        sleep(600);  // Wait for 500 milliseconds
        WebElement zipCodeField = wait.until(ExpectedConditions.elementToBeClickable(By.id("billing-zip")));
        zipCodeField.sendKeys("2244");
        sleep(500);  // Wait for 500 milliseconds
        //Wait for the submit button or equivalent to be clickable and then click it
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("iconTick")));
        submitButton.click();
        // Switch back to the default content if necessary
        //driver.switchTo().defaultContent();
        test.pass("then");
    }
    @Then("Submit the Form")
    public void submit_the_form() throws InterruptedException {

        // driver.findElement(By.cssSelector("#submitButton > span > span")).click();
        //System.out.println("click being done");
        //Thread.sleep(2000);
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        WebElement thankYou = driver.findElement(By.cssSelector("body > div > div:nth-child(1) > h2"));
        assertEquals("PAYMENT SUCCESS", thankYou.getText());
        test.pass("then");

    }
    // Method to get the product price (assuming it can be extracted from the WebElement)
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }
}