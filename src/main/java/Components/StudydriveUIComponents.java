package Components;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import resources.base;

public class StudydriveUIComponents extends base{
	public WebDriver driver;
	public static Logger log=LogManager.getLogger(base.class.getName());	
	public StudydriveUIComponents(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
	}
	String workingDir = System.getProperty("user.dir");
	String uploadfilename=prop.getProperty("uploadfilename");
	
	public void Login() throws IOException, InterruptedException  {
	//	String email="rjsmsc765@gmail.com";
	//	String Password = "Rahulselenium#1234";
		
		String email=prop.getProperty("username");
		System.out.println("Username is "+ email);
		String Password=prop.getProperty("Password");
		System.out.println("Password is "+ Password);
		
		//Capturing screenshot of Home page
		getScreenshot("Homepage");
		//Accept cookie
		click("btnAcceptcookie");
		//perform login
		click("btnLogin");
		SetText("eltusername", email);
		SetText("eltPassword", Password);
		click("btnInnerLogin");
	}
	
	public void UploadFile() throws IOException, InterruptedException, AWTException {
		getScreenshot("HomepageafterLogin");
		
		//Click on selected course
		click("btnselectedcourse");
		
		getScreenshot("selectedcourse");
		
		click("btnAdddocument");
		getScreenshot("Adddocument");
		
		click("btnuploaddocument");
		
		getScreenshot("uploaddoc");
		
		click("btninnerupload");
		getScreenshot("innerupload");
		Thread.sleep(3000);
		//Attach file
		
		System.out.println("Upload file name is "+ uploadfilename);
		String uploadfilpath=workingDir+"\\images\\"+uploadfilename;
		System.out.println("Upload file path is "+ uploadfilpath);
		AttachFile(uploadfilpath);
		Thread.sleep(3000);
		
		//Enter professor name
		String Professorname=prop.getProperty("pofessorname");
		System.out.println("Professor name is "+ Professorname);
		String description=prop.getProperty("description");
		System.out.println("Description to be added is "+ description);
		String selectsemester=prop.getProperty("selectsemester");
		System.out.println("Semester to be selected is "+ selectsemester);
		String doctype=prop.getProperty("documenttype");
		System.out.println("Document type to be selected is "+ selectsemester);
		
		SetText("eltAddProfessorname", Professorname);
		//Add description
		SetText("eltAdddescription", description);
		//Select semester
		SelectValuebyVisibleText("selectsemester",selectsemester);

		/*
		String semesterselection=GetXpath("selectsemester");
		Select dropdown = new Select(driver.findElement(By.xpath(semesterselection)));
		dropdown.selectByVisibleText(selectsemester);
		System.out.println("Selected value "+selectsemester); */
		
		//Select document type
		SelectValuebyVisibleText("selectdocumenttype",doctype);
		
		//Click on complete upload button
		click("btnCompleteupload");
		Thread.sleep(5000);
		getScreenshot("afterupload");
	}

	public void VerifyUploadSuccessful() throws IOException, InterruptedException {
	
		click("btnselectedcourse");
		
		getScreenshot("selectedcoursepageafterupload");
		
		//verify if navgated to our offers page correctly or not
		String Actualpreviewtitle = getelementtext("Actualpreviewtitle");
		System.out.println("Actual preview title is "+Actualpreviewtitle);
		click("Actualpreviewtitle");
		
		String ActualFiletitle = getelementtext("ActualFiletitle");
		System.out.println("Actual uploaded file title is "+ActualFiletitle);
		
		try {
			Assert.assertEquals(uploadfilename, ActualFiletitle);
			log.info("Uploaded correct file in the selected course Successfully");
			System.out.println("Uploaded correct file in the selected course Successfully");
			getScreenshot("Correctlyuploadedfile");
		} catch (Exception e) {

			log.error("Failed to uploaded file in the selected course "+e);
			System.out.println("Failed to uploaded file in the selected course "+e);
			getScreenshot("Wronglyuploadedfile");
		}
}
	


}
