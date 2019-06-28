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
	
	public void CloseRegionPopup() throws IOException, InterruptedException {
		
		//Capturing screenshot before closing popup
		getScreenshot("RegionSelectionPopup");
		//String closeregionpopup="//div[@class='modal-header']/button[@class='close']";
		String closeregionpopup=GetXpath("closeregionpopup");
		//Handle Select region pop
		
		//int popupscount=driver.findElements(By.xpath(closeregionpopup)).size();
		
		int popupscount=driver.findElements(By.xpath(GetXpath("closeregionpopup"))).size();
		System.out.println("POpups count = "+popupscount);
		
		if(popupscount!=0)
		{
			if(driver.findElement(By.xpath(closeregionpopup)).isDisplayed())
			{
				//click(closeregionpopup);
				click("closeregionpopup");
				//popupscount=popupscount-1;
				System.out.println("Closed the region popup");
				log.info("Closed the region popup");
			
			}
		}
		
		Thread.sleep(5000);
			
				
	}
	
	public void VerifyOurOfferslink() throws IOException, InterruptedException {
		
		//Click our offers link
		//String ouroffers="//a[@class='nav-main-link'][contains(text(),'Our Offers')]";
		//String ouroffers=GetXpath("ouroffers");
		
		click("ourofferslink");
		//String verifyourofferspageresult="//ng-pluralize";
		String verifyourofferspageresult=GetXpath("verifyourofferspageresult");
		String Expectedtext="offers match your search";
		System.out.println("Expected text is "+Expectedtext);
		Thread.sleep(5000);
		
		//Capture screenshot of result
		getScreenshot("Ourofferslinkpage");
		//verify if navgated to our offers page correctly or not
		
		String Actualtext = driver.findElement(By.xpath(verifyourofferspageresult)).getText();
		System.out.println("Actual text is"+Actualtext);
		
		if(Actualtext.contains(Expectedtext))
		{
			
			log.info("Navigated to the correct page successfully");
		}
		else
		{
			log.error("Navigation to correct page failed ");
			System.out.println("Navigation to correct page failed ");
		}	
		
	}
	
public void verifyHeaderElements() throws SQLException, IOException {
	String[] ExpectedHeaderelemnts=null;
	String datbasename="Raisindb";
	String querytoexecute="Select * from HeaderElements";
	
	ExpectedHeaderelemnts=GetdbRecords(datbasename, querytoexecute);
	
	for(int i=0;i<5;i++)
	{
	System.out.println("Expected Header element"+i+"is "+ExpectedHeaderelemnts[i]);	
	}
	
	String ActualHeaderelements[]={"abc","abc","abc","abc","abc"};
	//String Headerelement1=GetXpath("Headerelement1");
	//ActualHeaderelements[0]=driver.findElement(By.xpath(Headerelement1)).getText();
	ActualHeaderelements[0]=getelementtext("Headerelement1");
	ActualHeaderelements[1]=getelementtext("Headerelement2");
	ActualHeaderelements[2]=getelementtext("Headerelement3");
	ActualHeaderelements[3]=getelementtext("Headerelement4");
	ActualHeaderelements[4]=getelementtext("Headerelement5");
	
//	System.out.println("Actual value in element is"+ActualHeaderelements[0]);
	
	//verify if the text of each header element is displayed correctly or not
	for(int i=0;i<5;i++)
	{
		try {
			Assert.assertEquals(ExpectedHeaderelemnts[i], ActualHeaderelements[i]);
			log.info("Text is displayed correctly for the the element "+ExpectedHeaderelemnts[i]);
			System.out.println("Text is displayed correctly for the the element "+ExpectedHeaderelemnts[i]);
		} catch (Exception e) {

			log.error("Text is displayed wrongly for the the element "+ExpectedHeaderelemnts[i]+e);
			System.out.println("Text is displayed wrongly correctly for the the element "+ExpectedHeaderelemnts[i]);
		}

		
	}
	
	}
	
public void VerifyBankslink() throws IOException, InterruptedException {
	
	//Click  Banks link
	click("Bankslink");
	//String verifyBankspageresult=GetXpath("verifyourofferspageresult");
	String Expectedtext="Learn more about our partner banks";
	System.out.println("Expected Banks text is "+Expectedtext);
	Thread.sleep(8000);
	
	//Capture screenshot of result
	getScreenshot("Bankslinkpage");
	
	//verify if navgated to our offers page correctly or not
	String Actualtext = getelementtext("Bankpagetitle");
	System.out.println("Actual text is "+Actualtext);
	
	try {
		Assert.assertEquals(Expectedtext, Actualtext);
		log.info("Text is displayed correctly for the the Banks link page.");
		System.out.println("Text is displayed correctly for the the Banks link page.");
	} catch (Exception e) {

		log.error("Text is displayed wrongly for the the Banks link page."+e);
		System.out.println("Text is displayed wrongly for the the Banks link page."+e);
	}

}	

public void VerifyBecomeAPartnerlink() throws IOException, InterruptedException {
		
		//Click BecomeAPartner link
		click("BecomeAPartnerlink");
		
		String Expectedtext="The first and only pan-European deposit marketplace";
		System.out.println("Expected VerifyBecome a Partner text is "+Expectedtext);
		Thread.sleep(8000);
		
		//Capture screenshot of result
		getScreenshot("BecomeAPartnerlinkpage");
		
		//verify if navgated to our offers page correctly or not
		String Actualtext = getelementtext("BecomeAPartnerpagetitle");
		System.out.println("Actual text is "+Actualtext);
		
		try {
			Assert.assertEquals(Expectedtext, Actualtext);
			log.info("Text is displayed correctly for the the BecomeAPartner link page.");
			System.out.println("Text is displayed correctly for the the BecomeAPartner link page.");
		} catch (Exception e) {

			log.error("Text is displayed wrongly for the the BecomeAPartner link page."+e);
			System.out.println("Text is displayed wrongly for the the BecomeAPartner link page."+e);
		}

	
}

public void VerifyBloglink() throws IOException, InterruptedException {
	
	//Click Blog link
	click("Bloglink");
	
	String Expectedtext="Blog.";
	System.out.println("Expected Blog link text is "+Expectedtext);
	Thread.sleep(8000);
	
	//Capture screenshot of result
	getScreenshot("Bloglinkpage");
	
	//verify if navigated to our offers page correctly or not
	String Actualtext = getelementtext("Blogpagetitle");
	System.out.println("Actual text is "+Actualtext);
	
	try {
		Assert.assertEquals(Expectedtext, Actualtext);
		log.info("Text is displayed correctly for the the BecomeAPartner link page.");
		System.out.println("Text is displayed correctly for the the BecomeAPartner link page.");
	} catch (Exception e) {

		log.error("Text is displayed wrongly for the the BecomeAPartner link page."+e);
		System.out.println("Text is displayed wrongly for the the BecomeAPartner link page."+e);
	}


}

public void VerifyAboutSublink1() throws IOException, InterruptedException {
	
	//Click About link
	click("Aboutlink");
	//Click About - Sublink1
	click("AboutSublinkHowitworks");
	
	String Expectedtext="How Raisin works";
	System.out.println("Expected About-Sublink1 text is "+Expectedtext);
	Thread.sleep(8000);
	
	//Capture screenshot of result
	getScreenshot("AboutSublink1page");
	
	//verify if navgated to our offers page correctly or not
	String Actualtext = getelementtext("AboutSublink1pagetitle");
	System.out.println("Actual text is "+Actualtext);
	
	try {
		Assert.assertEquals(Expectedtext, Actualtext);
		log.info("Text is displayed correctly for the the About-Sublink1 page.");
		System.out.println("Text is displayed correctly for the the About-Sublink1 page.");
	} catch (Exception e) {

		log.error("Text is displayed wrongly for the the About-Sublink1 page."+e);
		System.out.println("Text is displayed wrongly for the the About-Sublink1 page."+e);
	}


}
/*	public void click(String elementname)
	{
		driver.findElement(By.xpath(elementname)).click();
	}*/

}
