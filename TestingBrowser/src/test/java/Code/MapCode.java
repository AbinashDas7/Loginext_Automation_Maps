package Code;

import java.util.Properties;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.time.Duration;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class MapCode {
	static WebDriver driver;
	String browserDetails;
	String residentAddress;
	String chromeeDriverPath;
	String msEdgeDriverPath;
	String destinationPath;
	

    
	public void propertiesFileRead() {
		System.out.println("------------------------Given User details read from propertiesfile--------------------");
		System.out.println("entering into propertiesFileRead");

		Properties prop = new Properties();
		try {
			//load a properties file from class path, inside static method
			prop.load(new FileInputStream("src\\test\\java\\Code\\userDetails.properties"));
			
			//get the property value and print it out
			//printing browser details
			browserDetails = prop.getProperty("browser");

			
			//printing edge driver path
			msEdgeDriverPath = prop.getProperty("msEdgeDriverPath");

			
			//printing chrome driver path
			chromeeDriverPath = prop.getProperty("chromeDriverPath");

			
			//printing user resident address..
			residentAddress = prop.getProperty("UserResidentialAddress");

			
			//printing Destination address..
			destinationPath = prop.getProperty("Destination");

		} 
		catch (IOException ex) {
			System.err.print("failing inside propertiesFileRead");
			ex.printStackTrace();
		}
		System.out.println("exiting into propertiesFileRead");
	}

	public void startDriver() {
		System.out.println("--------------Given Verify user is able to Go to maps.google.com------------");
		System.out.println("entering startDriver()...");
		try {
		if(browserDetails.equals("chrome")) {
			System.getProperty("webdriver.chrome.driver",chromeeDriverPath);
			 driver = new ChromeDriver();
			ChromeOptions co = new ChromeOptions();
			co.addArguments("disable-infobars");
		}
		else if(browserDetails.equals("edge")) {
		System.out.println(browserDetails + destinationPath +msEdgeDriverPath + residentAddress);
		System.getProperty("webdriver.edge.driver",msEdgeDriverPath);
		driver = new EdgeDriver();
		EdgeOptions edge = new EdgeOptions();
		edge.addArguments("disable-infobars");
		}
		driver.manage().window().maximize();
		driver.get("https://www.google.com/maps");
		System.out.println("driver is in "+driver.getCurrentUrl());
		//verifying the url link
		if(driver.getCurrentUrl().contains("https://www.google.com/maps")) {
		System.out.println("Verified user is in right url i.e. "+driver.getCurrentUrl());
		}
		else {
			System.err.println("failed on validating map url");
		}
		System.out.println("exiting startDriver()...");
		
		}catch(Exception e) {
			System.err.print("failing inside startDriver()");
			e.printStackTrace();
		}
	}
    public static void takeScreenshot(String filename) throws InterruptedException {
    	Thread.sleep(2000);
        TakesScreenshot screenshot = (TakesScreenshot) driver;

        // Capture the screenshot as a file
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

        // Define the destination file path
        String destFilePath = "./src/test/java/Screenshots/" + filename;

        try {
            // Copy the captured screenshot file to the destination path
            FileHandler.copy(srcFile, new File(destFilePath));
            System.out.println("Screenshot captured: " + destFilePath);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
    public static void writeToExcel(String value1, String value2, String value3, String filePath) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Sheet sheet = workbook.getSheetAt(0); // Get the first sheet

        int lastRowIndex = sheet.getLastRowNum();
        int newRowIndex = lastRowIndex + 1;

        Row newRow = sheet.createRow(newRowIndex);

        Cell cell1 = newRow.createCell(0);
        cell1.setCellValue(value1);

        Cell cell2 = newRow.createCell(1);
        cell2.setCellValue(value2);

        int lastColumnIndex = getLastColumnIndex(sheet, newRowIndex);
        int newColumnIndex = lastColumnIndex + 1;

        Cell newCell = newRow.createCell(newColumnIndex);
        newCell.setCellValue(value3);

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static int getLastColumnIndex(Sheet sheet, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row != null) {
            return row.getLastCellNum();
        }
        return 0;
    }
	public void clickDirection() {
		System.out.println("---------------And Check user is able to Click on Directions button--------------");
		System.out.println("entering clickDirection()..");
		try {
			//clicking direction button
			driver.findElement(By.xpath("//button[@id='hArJGc']")).click();

			System.out.println("clicked on direction button...");
			takeScreenshot("screenshot_clcikOnDirection.png");
		}catch(Exception e) {
			System.err.print("failing inside clickDirection()");
			e.printStackTrace();
		}
		System.out.println("exiting clickDirection()..");
	}
	public void enterUserAddress() {
		System.out.println("----------------And Check user is able to Click on Directions button--------------");
		System.out.println();
		System.out.println("entering enterUserAddress()...");
		try {
			driver.findElement(By.xpath("//*[@id='sb_ifc50']/input")).sendKeys(residentAddress);
			driver.findElement(By.xpath("//*[@id='sb_ifc50']/input")).sendKeys(Keys.ENTER);
			System.out.println("user address successfully added as: "+residentAddress);
			takeScreenshot("SC_userAddress.png");
		}catch(Exception e) {
			System.err.print("failing inside enterUserAddress()");
			e.printStackTrace();
		}
		System.out.println("exiting enterUserAddress()...");
	}
	
	public void destinationAddress() {
		System.out.println("----------------And Check User adding Destination from properties file--------------");
		System.out.println();
		System.out.println("entering destinationAddress()...");
		try {
			driver.findElement(By.xpath("//*[@id='sb_ifc51']/input")).sendKeys(destinationPath);
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[@id='sb_ifc51']/input")).sendKeys(Keys.ENTER);
			System.out.println("destination address successfully added as: "+destinationPath);
			

			takeScreenshot("SC_destinationAddress.png");
		}catch(Exception e) {
			System.err.print("failing inside destinationAddress()");
			e.printStackTrace();
		}
		System.out.println("exiting destinationAddress()...");
	}
	
	public void detailsOfTravelling() {
		System.out.println();
		System.out.println("----------------Then Check whether user able to download or copy all the driving instructions in excel sheet--------------");
		System.out.println("entering detailsOfTravelling()...");
		try {
			Thread.sleep(4000);

			WebElement tt = driver.findElement(By.xpath("//*[@id='section-directions-trip-2']/div[1]/div/div/h1"));
			Actions act = new Actions(driver);
			act.moveToElement(tt);
			act.perform();
			takeScreenshot("travelMediums.png");
			//waiting for all travel medium to be loaded
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(tt));
			List<WebElement> allRows = driver.findElements(By.xpath("//*[contains(@class,'VuCHmb fontHeadlineSmall')]"));
			System.out.println(allRows.size());
			int countHrs=0;
			int countDistance=2;
			//getting all the values of travel mediums
			if(allRows.size()>0) {
				for(WebElement travelMedium : allRows) {
					if(countHrs == 2) {
						String hrs = driver.findElement(By.xpath("//*[@id='section-directions-trip-"+countHrs+"']/div[1]/div/div[1]/div[1]")).getText();
						System.out.println(travelMedium.getText() + " will take "+hrs+" to reach");
						writeToExcel(travelMedium.getText(),"Take: "+hrs,null,"D:\\34\\TestingBrowser\\src\\test\\java\\TravelDetailsUpdated.xlsx");
					break;
					}
					//getting respective hours
					String hrs = driver.findElement(By.xpath("//*[@id='section-directions-trip-"+countHrs+"']/div[1]/div/div[1]/div[1]")).getText();
					//getting respective distance
					String distance = driver.findElement(By.xpath("//*[@id='section-directions-trip-"+countHrs+"']/div[1]/div/div[1]/div["+countDistance+"]")).getText();
					System.out.println(travelMedium.getText() + " will take "+hrs+" to reach and travel "+distance+" kms");
					writeToExcel(travelMedium.getText(),"Take: "+hrs,"Cover: "+distance,"D:\\34\\TestingBrowser\\src\\test\\java\\TravelDetailsUpdated.xlsx");
					countHrs++;
				}
			}

		}catch(Exception e) {
			System.err.print("failing inside detailsOfTravelling()");
			e.printStackTrace();
		}
		System.out.println("exiting detailsOfTravelling()...");
	}
	public void firstRoute() {
		System.out.println();
		System.out.println("----------------And Verify User able to select the first route--------------");
		System.out.println("entering firstRoute()...");
		try {
			driver.findElement(By.xpath("//*[@id='section-directions-trip-title-0']")).click();
			System.out.println("Successfully clicked on First route...");
			takeScreenshot("firstRoute.png");
		}catch(Exception e) {
			System.err.print("failing inside firstRoute()");
			e.printStackTrace();
		}
		System.out.println("exiting firstRoute()...");
	}
	
	public void closeBrowser() {
		System.out.println("closing browser");
		driver.quit();
	}
	
	
	public static void main(String[] args) {
		MapCode mp = new MapCode();
		mp.propertiesFileRead();
		mp.startDriver();
		mp.clickDirection();
		mp.enterUserAddress();
		mp.destinationAddress();
		mp.detailsOfTravelling();
		mp.firstRoute();
		mp.closeBrowser();
	}


}
