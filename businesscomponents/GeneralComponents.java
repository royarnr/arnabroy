package businesscomponents;

import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import componentgroups.Level_A;
import componentgroups.Level_AA;

import supportlibraries.*;
import allocator.Allocator;

/**
 * Class for storing general purpose business components
 * @author Cognizant
 */
public class GeneralComponents extends ReusableLibrary {
	
	/**
	 * Constructor to initialize the component library
	 * @param scriptHelper The {@link ScriptHelper} object DONEed from the {@link DriverScript}
	 */
	public GeneralComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}
	
	public void invokeApplication() {
		
		try {
			driver.get(Allocator.URL.get(counter));
			
			sleep(3);
		} catch (TimeoutException e) {
			sleep(4);
		} 
		createPageHeadinginReport();
		

		
	}
	
	public void runDefaultMethods () {
		
		try{
			Level_A a = new Level_A(scriptHelper);
			a.launchMessageBox();
			a.checkNonTextContent_wcag1_1_1();
//			a.checkInfoAndRelationships_wcag_1_3_1();
//			a.meaningfulseqeunce_wcag_1_3_2();
//			a.checkSensoryCharacteristics_wcag_1_3_3();
//			a.useofColor();
//			a.audioControl_wcag_1_4_2();
//			a.keyboard_sequence();
//			a.checkKeyboardtrap_wcag_2_1_2();
//			a.pausemovingobject_wcag_2_2_2(element);
//			a.checkBypassBlocks_wcag_2_4_1();
//			a.checkPageTitle_wcag_2_4_2();
//			a.checkFocusOrder_wcag_2_4_3();
//			a.checkLinkPurpose_wcag_2_4_4();
//			a.checkDocumentLanguage();
//			a.checkOnFocus_wcag_3_2_1();
//			a.onInput_wcag_3_2_2();
//			a.erroridentification_wcag_3_3_1(xpathlocators);
//			a.label_wcag_3_3_2();
//			a.htmlParsingError_wcag_4_1_1();
//			a.checkNameRoleValue_wcag_4_1_2();
			a.closeMessageBox();
			
//			Level_AA aa = new Level_AA(scriptHelper);
//			
//			aa.launchMessageBox();
//			List<String> elementsforcolorcontrast = dataTable.getDataArray("Color", new String[]{"ElementXPath1"});
//			aa.getcolorContrast_wcag_1_4_3(elementsforcolorcontrast);
//			aa.checkHeadings_wcag_2_4_6();
			
//			aa.checkFocus_wcag_2_4_7();
//			aa.languageofParts_wcag_3_1_2();
//			aa.errorsuggestion_wcag_3_3_3(xpathlocators);
//			List<String> xpathofnavigationbuttons = dataTable.getDataArray("Error", new String[]{"ElementXPath1", "ElementXPath2"});
//			aa.errorPrevention_wcag_3_3_4(xpathofnavigationbuttons);
			
			
//			aa.closeMessageBox();
			counter++;
		}
		catch (Exception exception)
		{
			System.out.println("Error while parsing the source code. Message :" +exception.getMessage());
		}
	}
	
	/*
	 * Go to the next page in application
	 */
	
	
	public void gotoSecondPage() {

		try {
			//TO DO
//			driver.get(Allocator.URL.get(counter));
//			driver.findElement(By.xpath("")).click();
		} catch (TimeoutException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} 
		createPageHeadinginReport();



	}
	
	/*
	 * Run a subset of AODA guidelines
	 */
	public void runAODAguidelinessubset () {
		
		try{
			Level_A a = new Level_A(scriptHelper);
			a.launchMessageBox();
			a.checkNonTextContent_wcag1_1_1();
			a.checkInfoAndRelationships_wcag_1_3_1();
			a.meaningfulseqeunce_wcag_1_3_2();
			a.checkSensoryCharacteristics_wcag_1_3_3();
			a.closeMessageBox();
			
			Level_AA aa = new Level_AA(scriptHelper);
			
			aa.launchMessageBox();
//			List<String> elementsforcolorcontrast = dataTable.getDataArray("Color", new String[]{"ElementXPath1"});
//			aa.getcolorContrast_wcag_1_4_3(elementsforcolorcontrast);
			aa.checkHeadings_wcag_2_4_6();
			
			aa.checkFocus_wcag_2_4_7();
			aa.languageofParts_wcag_3_1_2();
//			aa.errorsuggestion_wcag_3_3_3(xpathlocators);
//			aa.errorPrevention_wcag_3_3_4(xpathofbackbutton);
			
			
			aa.closeMessageBox();
			counter++;
		}
		catch (Exception exception)
		{
			System.out.println("Error while parsing the source code. Message :" +exception.getMessage());
		}
	}
}