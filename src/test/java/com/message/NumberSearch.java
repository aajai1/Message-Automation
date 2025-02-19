package com.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class NumberSearch {
	WebDriver driver;
	WebDriverWait wait;
//
//	@DataProvider(name = "nameData")
//	public static Object[][] sendMe(){
//		return  Data.datas();
//	}



	@BeforeSuite
	public void launch() throws InterruptedException {
		driver= new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.get("https://web.whatsapp.com/");
		driver.manage().window().maximize();
		Thread.sleep(35000);
	}



	@Test
	public void Search () throws IOException {
		
		File file = new File("./Names.xlsx");
		FileInputStream fileBytes = new FileInputStream(file);
		XSSFWorkbook workBook= new XSSFWorkbook(fileBytes);
		XSSFSheet sheet =workBook.getSheetAt(0);
		
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			String names = sheet.getRow(i).getCell(0).getStringCellValue();
			String message= sheet.getRow(i).getCell(1).getStringCellValue();
			 message = message.replaceAll("\r\n", "\n").replaceAll("\r", "\n");

		
	
			//search
			WebElement plus = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[3]/header[1]/header[1]/div[1]/span[1]/div[1]/div[1]/button[1]/span[1]"));
			wait.until(ExpectedConditions.elementToBeClickable(plus));
			plus.click();
			WebElement search = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/span[1]/div[1]/span[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/p[1]"));
			wait.until(ExpectedConditions.elementToBeClickable(search));
			search.sendKeys(names);
			try 
			{
				WebElement searchResult = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[3]/div/div[2]/div[1]/span/div/span/div/div[2]/div/div/div/div[2]"));

				wait.until(ExpectedConditions.elementToBeClickable(searchResult));
				//Actions action = new Actions(driver);
				//action.moveToElement(searchResult).click().perform();
				searchResult.click();
			} catch (StaleElementReferenceException e) 
			{
				WebElement searchResult = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[3]/div/div[2]/div[1]/span/div/span/div/div[2]/div/div/div/div[2]"));
				wait.until(ExpectedConditions.elementToBeClickable(searchResult));
				//Actions action = new Actions(driver);
				//action.moveToElement(searchResult).click().perform();
				searchResult.click();
				WebElement textBox = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[4]/div[1]/footer[1]/div[1]/div[1]/span[1]/div[1]/div[2]/div[1]/div[2]/div[1]/p[1]"));
				wait.until(ExpectedConditions.elementToBeClickable(textBox));
				textBox.sendKeys(message);
				driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span/div/div[2]/div[2]")).click();

			}
			workBook.close();
		}
	}






}
