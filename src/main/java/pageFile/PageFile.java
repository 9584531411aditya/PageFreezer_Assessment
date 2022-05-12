package pageFile;

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;


public class PageFile {
	
	WebDriver driver;
	WebDriverWait wait;
	
	By SearchInput = By.xpath("//input[@aria-label='Search input']");
	By SearchButton = By.xpath("(//div[@class='input-group-btn'])[2]");
	By SocialMedia = By.xpath("(//a[contains(text(),'Social Media')])[1]");
	By SocialMediaTabs = By.xpath("//section[@ng-if='!new_search_enabled']/div");
	By Website = By.xpath("(//a[text()='Websites'])[1]");
	By WebsiteTabs = By.xpath("//span[text()='No results found']");
	By advanceSearch = By.xpath("//div[@class='advanced-search-link']");
	By fbPage = By.xpath("//a[contains(text(),'https://pagefreezerqa.facebook.com/groups/13091164')]");
	By fromDate = By.xpath("//input[@placeholder='From']");
	By toDate = By.xpath("//input[@placeholder='To']");
	By checkboxInput = By.xpath("//input[@type='checkbox']/parent::label");
	By selectFilters = By.xpath("//select[@aria-label='filter name']");
	By twitter = By.xpath("//a[text()='https://twitter.com/qatest45']");
	By twitterelement = By.xpath("//div[@aria-label='Timeline: qatest4’s Tweets']");
	By errorMsg = By.xpath("//span[@jsselect='heading']");
	public void openBrowser() throws IOException {
		String browserName = propfile("Browser");

		if (browserName.equals("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver,30);

	}
	
	public void openPublicPortal() throws IOException {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(propfile("URL"));
		
		

	}
	
	public void publicPortalAccess(String expectedTitle) {
		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(expectedTitle, title);
		System.out.println("Public Website is accessible");
		
		
	}
	
	public void search(String value) {
		driver.findElement(SearchInput).sendKeys(value);
		driver.findElement(SearchButton).click();
	}
	
	public void advanceSearch(String value, String from, String to, String checkValue, String filterValue) {
		driver.findElement(advanceSearch).click();
		driver.findElement(SearchInput).sendKeys(value);
		driver.findElement(fromDate).sendKeys("06/15/2020");
		driver.findElement(toDate).sendKeys("05/10/2022");
		checkBox(checkboxInput, checkValue);
		Select filters = new Select(driver.findElement(selectFilters));
		filters.selectByVisibleText(filterValue);
		driver.findElement(SearchButton).click();
		
	}
	
	public void verifySocialTabs() throws InterruptedException  {
		Thread.sleep(20000);
		driver.findElement(SocialMedia).click();
		boolean verify = driver.findElement(SocialMediaTabs).isDisplayed();
		if(verify) {
			System.out.println("Social Media Tabs are present");
		}else {
			System.out.println("Social Media Tabs are not present");
		}
		
		
	}
	
	public void verifyWebsiteTabs() throws InterruptedException  {
		driver.findElement(Website).click();
		boolean verify = driver.findElement(WebsiteTabs).isDisplayed();
		if(verify) {
			System.out.println("Website Tabs are not present");
		}else {
			System.out.println("Website Tabs are present");
		}
		
		
	}
	public void fbGenPage() throws InterruptedException {
		driver.findElement(fbPage).click();
		Thread.sleep(10000);
		windowSwitch();
		System.out.println(driver.findElement(errorMsg).getText());
	}
	
	public void twitterPage() throws InterruptedException {
		driver.findElement(twitter).click();
		windowSwitch();
		Thread.sleep(10000);
		String actualTitle = driver.getTitle();
		System.out.println(actualTitle);
		if (driver.findElement(twitterelement).isDisplayed()) {
			System.out.println("Web page available");
		}else {
			System.out.println("Web page cannot be reached");
		}
	}
	
	
	
	

	public void closePage() {
		driver.quit();
		

	}
	
	public void checkBox(By locator, String valueName) {
		List<WebElement> element = driver.findElements(locator);
		for (int i = 0; i < element.size(); i++) {
			String value = (element.get(i).getText());
			if (value.equals(valueName)) {
				element.get(i).click();
			}
		}

	}
	
	public void windowSwitch() {
		String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();

        // Here we will check if child window has other child windows and will fetch the heading of the child window
        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
                if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                
                }
        }
	}
	
	public static String propfile(String s) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(".\\src\\main\\java\\dataFile\\data.properties"));
		Properties prop = new Properties();
		prop.load(reader);
		return prop.getProperty(s);
	}

	

}
