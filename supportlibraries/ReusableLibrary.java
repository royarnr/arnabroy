package supportlibraries;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cognizant.framework.CraftDataTable;
import com.cognizant.framework.DetailedReport;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Settings;
import com.cognizant.framework.selenium.CraftDriver;

/**
 * Abstract base class for reusable libraries created by the user
 * @author Cognizant
 */
public abstract class ReusableLibrary {
	
	private static volatile String reportPathWithTimeStamp;
	protected static int fail = 0;
	protected static int counter = 0;
	private static String body = "";
	/**
	 * The {@link CraftDataTable} object (passed from the test script)
	 */
	protected CraftDataTable dataTable;
	/**
	 * The {@link CraftDriver} object
	 */
	protected CraftDriver driver;

	/**
	 * The {@link ScriptHelper} object (required for calling one reusable library from another)
	 */
	protected ScriptHelper scriptHelper;
	
	/**
	 * The {@link Properties} object with settings loaded from the framework properties file
	 */
	protected Properties properties;
	/**
	 * The {@link FrameworkParameters} object
	 */
	protected FrameworkParameters frameworkParameters;
	 
	/**
	 * Constructor to initialize the {@link ScriptHelper} object and in turn the objects wrapped by it
	 * @param scriptHelper The {@link ScriptHelper} object
	 */
	
	public DetailedReport report;
	
	
	public ReusableLibrary(ScriptHelper scriptHelper) {
		this.scriptHelper = scriptHelper;
		this.dataTable = scriptHelper.getDataTable();
		this.driver = scriptHelper.getcraftDriver();
		this.report = scriptHelper.getReport();
		
		properties = Settings.getInstance();
		frameworkParameters = FrameworkParameters.getInstance();
	}
	
	public void sleep (int time) {
		
		try {
			Thread.sleep(time * 1000);
		} catch (Exception e) {
			System.err.println("Exception caught while explictly sleep the Browser. Exception Message :  " +e.getMessage());
			throw new FrameworkException("Sleep", e.getMessage());
		}
	}
	
//	public static String getReportPath() {
//		
//		if(reportPathWithTimeStamp == null) {
//			FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
//			
//			if(frameworkParameters.getRelativePath() == null) {
//				throw new FrameworkException("FrameworkParameters.relativePath is not set!");
//			}
//			if(frameworkParameters.getRunConfiguration() == null) {
//				throw new FrameworkException("FrameworkParameters.runConfiguration is not set!");
//			}
//			
//			Properties properties = Settings.getInstance();
//			String timeStamp =
//					"Run_" +
//					getCurrentFormattedTime(properties.getProperty("DateFormatString"))
//					.replace(" ", "_").replace(":", "-");
//			
//			reportPathWithTimeStamp =
//					frameworkParameters.getRelativePath() +
//					"\\" + "Results" +
//					"\\" + frameworkParameters.getRunConfiguration() + 
//					"\\" + timeStamp;
//	        
//	        new File(reportPathWithTimeStamp).mkdirs();
//		}
//		
//		return reportPathWithTimeStamp;
//	}
	
//	public static String getCurrentFormattedTime(String dateFormatString) {
//		DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
//		Calendar calendar = Calendar.getInstance();
//		return dateFormat.format(calendar.getTime());
//	}
	

	public String getAbsoluteXPath(WebElement element)
    
	{
       
       return (String) ((org.openqa.selenium.JavascriptExecutor) driver.getWebDriver()).executeScript(
                "function absoluteXPath(element) {"+
                        "var comp, comps = [];"+
                        "var parent = null;"+
                        "var xpath = '';"+
                        "var getPos = function(element) {"+
                        "var position = 1, curNode;"+
                        "if (element.nodeType == Node.ATTRIBUTE_NODE) {"+
                        "return null;"+
                        "}"+
                        "for (curNode = element.previousSibling; curNode; curNode = curNode.previousSibling){"+
                        "if (curNode.nodeName == element.nodeName) {"+
                        "++position;"+
                        "}"+
                        "}"+
                        "return position;"+
                        "};"+

		    "if (element instanceof Document) {"+
		    "return '/';"+
		    "}"+
		
		    "for (; element && !(element instanceof Document); element = element.nodeType ==Node.ATTRIBUTE_NODE ? element.ownerElement : element.parentNode) {"+
		    "comp = comps[comps.length] = {};"+
		    "switch (element.nodeType) {"+
		    "case Node.TEXT_NODE:"+
		    "comp.name = 'text()';"+
		    "break;"+
		    "case Node.ATTRIBUTE_NODE:"+
		    "comp.name = '@' + element.nodeName;"+
		    "break;"+
		    "case Node.PROCESSING_INSTRUCTION_NODE:"+
		    "comp.name = 'processing-instruction()';"+
		    "break;"+
		    "case Node.COMMENT_NODE:"+
		    "comp.name = 'comment()';"+
		    "break;"+
		    "case Node.ELEMENT_NODE:"+
		    "comp.name = element.nodeName;"+
		    "break;"+
		    "}"+
		    "comp.position = getPos(element);"+
		    "}"+
		
		    "for (var i = comps.length - 1; i >= 0; i--) {"+
		    "comp = comps[i];"+
		    "xpath += '/' + comp.name.toLowerCase();"+
		    "if (comp.position !== null) {"+
		    "xpath += '[' + comp.position + ']';"+
		    "}"+
		    "}"+
		
		    "return xpath;"+
		
		"} return absoluteXPath(arguments[0]);", element);
	}
	

	 public void createExcelFile (String excelName, List<String> list1, List<String> list2) {
		 
		 try {
			 //Create Blank workbook
			 XSSFWorkbook workbook = new XSSFWorkbook(); 
		     XSSFSheet spreadsheet = workbook.createSheet("Sequence");
		     XSSFRow row;
		     Cell cell;
		     row = spreadsheet.createRow(0);
		     cell = row.createCell(0);
		     cell.setCellValue("HTML Attribute");
		     cell = row.createCell(1);
		     cell.setCellValue("Value");
		     for (int i=0; i<list1.size(); i++) {
		    	 row = spreadsheet.createRow(i+1);
			     cell = row.createCell(0);
			     cell.setCellValue(list1.get(i));
			     cell = row.createCell(1);
			     cell.setCellValue(list2.get(i));
		     }
		     //Create file system using specific name
		     FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		     FileOutputStream out = new FileOutputStream(new File(frameworkParameters.getReportPath() + "\\" +excelName + ".xlsx"));
		     workbook.write(out);
		     workbook.close();
		     out.close();
		 } catch (Exception e) {
			 System.err.println("Exception Message : " + e.getMessage());
			 e.printStackTrace(System.err);
		 }
		 
	
	 }
	 public String getOuterHTML(WebElement element) {
		
		 //return element.getAttribute("outerHTML").split(">")[0].substring(1, (element.getAttribute("outerHTML").split(">")[0].length()-1));
//		 return element.getAttribute("outerHTML").replaceAll("[<>]", "");
		 //return element.getAttribute("outerHTML");
		 return element.getAttribute("outerHTML").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	 
	 public void highlightElement(WebElement element)
	 {
		 try{
			 ((JavascriptExecutor)driver.getWebDriver()).executeScript("arguments[0].setAttribute('style','border: solid 4px red');", element);
			 }
			 catch (Exception e)
			 {
				 e.printStackTrace();
				 System.out.println("Unable to highlight element");
			 }
	 }
	 
	 public void createExcelFileouterHTML (String excelName, List<String> list1, List<String> list2, List<String> list3) {
		 
		 try {
			 //Create Blank workbook
			 XSSFWorkbook workbook = new XSSFWorkbook(); 
		     XSSFSheet spreadsheet = workbook.createSheet("Labels of Input Fields");
		     XSSFRow row;
		     Cell cell;
		     row = spreadsheet.createRow(0);
		     cell = row.createCell(0);
		     cell.setCellValue("Label Text");
		     cell = row.createCell(1);
		     cell.setCellValue("Attribute");
		     cell = row.createCell(2);
		     cell.setCellValue("Outer HTML");
		     
		     for (int i=0; i<list1.size(); i++) {
		    	 row = spreadsheet.createRow(i+1);
			     cell = row.createCell(0);
			     cell.setCellValue(list1.get(i));
			     cell = row.createCell(1);
			     cell.setCellValue(list2.get(i));
			     cell = row.createCell(2);
			     cell.setCellValue(list3.get(i));
		     }
		     //Create file system using specific name
		     FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		     FileOutputStream out = new FileOutputStream(new File(frameworkParameters.getReportPath() + "\\" +excelName + ".xlsx"));
		     workbook.write(out);
		     workbook.close();
		     out.close();
		 } catch (Exception e) {
			 System.err.println("Exception Message : " + e.getMessage());
			 e.printStackTrace(System.err);
		 }
		 
	
	 }

	/*
	 * Return plain text of outerHTML
	 */
	public String getOuterHTMLplaintext(WebElement element)
	{
		String outerHTML = "";
		
		outerHTML = "<"+element.getAttribute("outerHTML").split("<")[1].trim();
		
		return outerHTML;
	}
	
	/*
	 * Write Header data
	 */

	public void writeheaderhirerachy(String excelName, List<Integer> level, List<String> tagname, List<String> xpath, List<String> text)
	 {
	        String filename = frameworkParameters.getReportPath() + "\\" +excelName + ".xlsx";
	        
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        
	        XSSFSheet sheet = workbook.createSheet("Hierarchy");
	        
	        XSSFRow headerrow = sheet.createRow(0);
	        headerrow.createCell(0).setCellValue("Hierarchy of Elements");
	        
	        
	        for (int i=0; i<level.size();i++)
	        {
	               XSSFRow row = sheet.createRow(i+1);
	               row.createCell(level.get(i)).setCellValue(tagname.get(i));
	               row.createCell(level.get(i)+1).setCellValue(text.get(i));
	        }
	        
	        try {
	               workbook.write(new FileOutputStream(new File(filename)));
	               workbook.close();
	        } catch (java.io.IOException e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	        }
	        
	        
	 }



	

	
	/*
	 * Excel File for 1.3.3 - Sensory Characteristics
	 */

	public void createExcelFilesensorycharacters (String excelName, List<String> list1, List<String> list2, List<String> list3) {
		 
		 try {
			 //Create Blank workbook
			 XSSFWorkbook workbook = new XSSFWorkbook(); 
		     XSSFSheet spreadsheet = workbook.createSheet("Sensory Characters for Input Fields");
		     XSSFRow row;
		     Cell cell;
		     row = spreadsheet.createRow(0);
		     cell = row.createCell(0);
		     cell.setCellValue("Input Field Label");
		     cell = row.createCell(1);
		     cell.setCellValue("Name or Role Value");
		     cell = row.createCell(2);
		     cell.setCellValue("Outer HTML");
		     
		     for (int i=0; i<list1.size(); i++) {
		    	 row = spreadsheet.createRow(i+1);
			     cell = row.createCell(0);
			     cell.setCellValue(list1.get(i));
			     cell = row.createCell(1);
			     cell.setCellValue(list2.get(i));
			     cell = row.createCell(2);
			     cell.setCellValue(list3.get(i));
		     }
		     //Create file system using specific name
		     FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		     FileOutputStream out = new FileOutputStream(new File(frameworkParameters.getReportPath() + "\\" +excelName + ".xlsx"));
		     workbook.write(out);
		     workbook.close();
		     out.close();
		 } catch (Exception e) {
			 System.err.println("Exception Message : " + e.getMessage());
			 e.printStackTrace(System.err);
		 }
		 
	
	 }
	
	public void createExcelFileforuseofcolor (String excelName, List<String> list1, List<String> list2, List<String> list3) {
		 
		 try {
			 //Create Blank workbook
			 XSSFWorkbook workbook = new XSSFWorkbook(); 
		     XSSFSheet spreadsheet = workbook.createSheet("Use of Color");
		     XSSFRow row;
		     Cell cell;
		     row = spreadsheet.createRow(0);
		     cell = row.createCell(0);
		     cell.setCellValue("Use of Color");
		     cell = row.createCell(1);
		     cell.setCellValue("Text of Control");
		     cell = row.createCell(2);
		     cell.setCellValue("Type");
		     
		     for (int i=0; i<list1.size(); i++) {
		    	 row = spreadsheet.createRow(i+1);
			     cell = row.createCell(0);
			     cell.setCellValue(list1.get(i));
			     cell = row.createCell(1);
			     cell.setCellValue(list2.get(i));
			     cell = row.createCell(2);
			     cell.setCellValue(list3.get(i));
		     }
		     //Create file system using specific name
		     FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		     FileOutputStream out = new FileOutputStream(new File(frameworkParameters.getReportPath() + "\\" +excelName + ".xlsx"));
		     workbook.write(out);
		     workbook.close();
		     out.close();
		 } catch (Exception e) {
			 System.err.println("Exception Message : " + e.getMessage());
			 e.printStackTrace(System.err);
		 }
		 
	
	 }

	public void createExcelFileformeaningfulseuqnce (String excelName, List<String> list1, List<String> list2, List<String> list3, List<String> list4) {
		 
		 try {
			 //Create Blank workbook
			 XSSFWorkbook workbook = new XSSFWorkbook(); 
		     XSSFSheet spreadsheet = workbook.createSheet("Meaningful Sequence");
		     XSSFRow row;
		     Cell cell;
		     row = spreadsheet.createRow(0);
		     cell = row.createCell(0);
		     cell.setCellValue("HTML Tage Name");
		     cell = row.createCell(1);
		     cell.setCellValue("Text of Control");
		     cell = row.createCell(2);
		     cell.setCellValue("Type of Control");
		     cell = row.createCell(3);
		     cell.setCellValue("Outer HTML");
		     
		     for (int i=0; i<list1.size(); i++) {
		    	 row = spreadsheet.createRow(i+1);
			     cell = row.createCell(0);
			     cell.setCellValue(list1.get(i));
			     cell = row.createCell(1);
			     cell.setCellValue(list2.get(i));
			     cell = row.createCell(2);
			     cell.setCellValue(list3.get(i));
			     cell = row.createCell(3);
			     cell.setCellValue(list4.get(i));
		     }
		     //Create file system using specific name
		     FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		     FileOutputStream out = new FileOutputStream(new File(frameworkParameters.getReportPath() + "\\" +excelName + ".xlsx"));
		     workbook.write(out);
		     workbook.close();
		     out.close();
		 } catch (Exception e) {
			 System.err.println("Exception Message : " + e.getMessage());
			 e.printStackTrace(System.err);
		 }
		 
	
	 }
	
	/*
	 * Create excel file for 2.1.1 - keyboard operable
	 */
	
	public void createExcelFileforkeyboardoperable (String excelName, List<String> list1, List<String> list2, List<String> list3) {
		 
		 try {
			 //Create Blank workbook
			 XSSFWorkbook workbook = new XSSFWorkbook(); 
		     XSSFSheet spreadsheet = workbook.createSheet("Keyboard Operable");
		     XSSFRow row;
		     Cell cell;
		     row = spreadsheet.createRow(0);
		     cell = row.createCell(0);
		     cell.setCellValue("HTML Tage Name");
		     cell = row.createCell(1);
		     cell.setCellValue("Type of Control");
		     cell = row.createCell(2);
		     cell.setCellValue("Text of Control");
		     
		     for (int i=0; i<list1.size(); i++) {
		    	 row = spreadsheet.createRow(i+1);
			     cell = row.createCell(0);
			     cell.setCellValue(list1.get(i));
			     cell = row.createCell(1);
			     cell.setCellValue(list2.get(i));
			     cell = row.createCell(2);
			     cell.setCellValue(list3.get(i));
		     }
		     //Create file system using specific name
		     FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		     FileOutputStream out = new FileOutputStream(new File(frameworkParameters.getReportPath() + "\\" +excelName + ".xlsx"));
		     workbook.write(out);
		     workbook.close();
		     out.close();
		 } catch (Exception e) {
			 System.err.println("Exception Message : " + e.getMessage());
			 e.printStackTrace(System.err);
		 }
		 
	
	 }
	/*
	 * WCAG 2.4.3
	 */
	public void createExcelFileforfocusorder (String excelName, List<String> list1, List<String> list2, List<String> list3) {
		 
		 try {
			 //Create Blank workbook
			 XSSFWorkbook workbook = new XSSFWorkbook(); 
		     XSSFSheet spreadsheet = workbook.createSheet("Focus Order");
		     XSSFRow row;
		     Cell cell;
		     row = spreadsheet.createRow(0);
		     cell = row.createCell(0);
		     cell.setCellValue("HTML Tage Name");
		     cell = row.createCell(1);
		     cell.setCellValue("Type of Control");
		     cell = row.createCell(2);
		     cell.setCellValue("Text of Control");
		     
		     for (int i=0; i<list1.size(); i++) {
		    	 row = spreadsheet.createRow(i+1);
			     cell = row.createCell(0);
			     cell.setCellValue(list1.get(i));
			     cell = row.createCell(1);
			     cell.setCellValue(list2.get(i));
			     cell = row.createCell(2);
			     cell.setCellValue(list3.get(i));
		     }
		     //Create file system using specific name
		     FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		     FileOutputStream out = new FileOutputStream(new File(frameworkParameters.getReportPath() + "\\" +excelName + ".xlsx"));
		     workbook.write(out);
		     workbook.close();
		     out.close();
		 } catch (Exception e) {
			 System.err.println("Exception Message : " + e.getMessage());
			 e.printStackTrace(System.err);
		 }
	}
	
	/*
	 * WCAG 2.4.4
	 */

	public void createExcelFileforlinktext (String excelName, List<String> list1, List<String> list2) {
		 
		 try {
			 //Create Blank workbook
			 XSSFWorkbook workbook = new XSSFWorkbook(); 
		     XSSFSheet spreadsheet = workbook.createSheet("Link Text");
		     XSSFRow row;
		     Cell cell;
		     row = spreadsheet.createRow(0);
		     cell = row.createCell(0);
		     cell.setCellValue("Link Text");
		     cell = row.createCell(1);
		     cell.setCellValue("Outer HTML");
		     
		     for (int i=0; i<list1.size(); i++) {
		    	 row = spreadsheet.createRow(i+1);
			     cell = row.createCell(0);
			     cell.setCellValue(list1.get(i));
			     cell = row.createCell(1);
			     cell.setCellValue(list2.get(i));
		     }
		     //Create file system using specific name
		     FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		     FileOutputStream out = new FileOutputStream(new File(frameworkParameters.getReportPath() + "\\" +excelName + ".xlsx"));
		     workbook.write(out);
		     workbook.close();
		     out.close();
		 } catch (Exception e) {
			 System.err.println("Exception Message : " + e.getMessage());
			 e.printStackTrace(System.err);
		 }
	}
	
	public void createPageHeadinginReport()
	{
		report.createRow();
		report.PageTitleheadings("Page Title : ");
		report.PageTitleheadings(driver.getTitle());
		report.endRow();
	}
	
	public void createExcelFileforDocumentLanguage (String excelName, List<String> list1, List<String> list2, List<String> list3) {
		 
		 try {
			 //Create Blank workbook
			 XSSFWorkbook workbook = new XSSFWorkbook(); 
		     XSSFSheet spreadsheet = workbook.createSheet("Document Language");
		     XSSFRow row;
		     Cell cell;
		     row = spreadsheet.createRow(0);
		     cell = row.createCell(0);
		     cell.setCellValue("Text");
		     cell = row.createCell(1);
		     cell.setCellValue("Language");
		     cell = row.createCell(2);
		     cell.setCellValue("Outer HTML");
		     
		     for (int i=0; i<list1.size(); i++) {
		    	 row = spreadsheet.createRow(i+1);
			     cell = row.createCell(0);
			     cell.setCellValue(list1.get(i));
			     cell = row.createCell(1);
			     cell.setCellValue(list2.get(i));
			     cell = row.createCell(2);
			     cell.setCellValue(list3.get(i));
		     }
		     //Create file system using specific name
		     FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		     FileOutputStream out = new FileOutputStream(new File(frameworkParameters.getReportPath() + "\\" +excelName + ".xlsx"));
		     workbook.write(out);
		     workbook.close();
		     out.close();
		 } catch (Exception e) {
			 System.err.println("Exception Message : " + e.getMessage());
			 e.printStackTrace(System.err);
		 }
	}

	/*
	 * WCAG 1.4.3
	 */

	public void createExcelFileforcolorcontrast (String excelName, List<String> list1, List<String> list2, List<String> list3, List<String> list4) {
		 
		 try {
			 //Create Blank workbook
			 XSSFWorkbook workbook = new XSSFWorkbook(); 
		     XSSFSheet spreadsheet = workbook.createSheet("Color Contrast");
		     XSSFRow row;
		     Cell cell;
		     row = spreadsheet.createRow(0);
		     cell = row.createCell(0);
		     cell.setCellValue("Text of Element");
		     cell = row.createCell(1);
		     cell.setCellValue("Font Color");
		     cell = row.createCell(2);
		     cell.setCellValue("Background Color");
		     cell = row.createCell(3);
		     
		     cell.setCellValue("Contrast Ratio");
		     
		     for (int i=0; i<list1.size(); i++) {
		    	 row = spreadsheet.createRow(i+1);
			     cell = row.createCell(0);
			     cell.setCellValue(list1.get(i));
			     cell = row.createCell(1);
			     cell.setCellValue(list2.get(i));
			     cell = row.createCell(2);
			     cell.setCellValue(list3.get(i));
			     cell = row.createCell(3);
			     cell.setCellValue(list4.get(i));
		     }
		     //Create file system using specific name
		     FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		     FileOutputStream out = new FileOutputStream(new File(frameworkParameters.getReportPath() + "\\" +excelName + ".xlsx"));
		     workbook.write(out);
		     workbook.close();
		     out.close();
		 } catch (Exception e) {
			 System.err.println("Exception Message : " + e.getMessage());
			 e.printStackTrace(System.err);
		 }
		 
	
	 }
	
	public void createExcelFileforError (String excelName, List<String> list1, List<String> list2) {
		 
		 try {
			 //Create Blank workbook
			 XSSFWorkbook workbook = new XSSFWorkbook(); 
		     XSSFSheet spreadsheet = workbook.createSheet("Error Message");
		     XSSFRow row;
		     Cell cell;
		     row = spreadsheet.createRow(0);
		     cell = row.createCell(0);
		     cell.setCellValue("Error Message");
		     cell = row.createCell(1);
		     cell.setCellValue("Outer HTML");
		     
		     for (int i=0; i<list1.size(); i++) {
		    	 row = spreadsheet.createRow(i+1);
			     cell = row.createCell(0);
			     cell.setCellValue(list1.get(i));
			     cell = row.createCell(1);
			     cell.setCellValue(list2.get(i));
		     }
		     //Create file system using specific name
		     FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		     FileOutputStream out = new FileOutputStream(new File(frameworkParameters.getReportPath() + "\\" +excelName + ".xlsx"));
		     workbook.write(out);
		     workbook.close();
		     out.close();
		 } catch (Exception e) {
			 System.err.println("Exception Message : " + e.getMessage());
			 e.printStackTrace(System.err);
		 }
	}
	
 
}