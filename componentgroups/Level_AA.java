package componentgroups;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import allocator.Allocator;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class Level_AA extends ReusableLibrary {

	private JFrame frame = null;
	private JLabel label = null;
	
	public Level_AA(ScriptHelper scriptHelper) {
		super(scriptHelper);
		report.createRow();
		report.PageTitleheadings("Level AA : ");
//		report.PageTitleheadings(" Execution Results");
		report.endRow();
	}
	
	public void checkFocus_wcag_2_4_7 ()  throws Exception {
		
		/*
		 *	WCAG 2.4.7 
		 */
		updateMessageBox("Executing AODA Guideline 2.4.7...");
		driver.navigate().refresh();
		try {
			boolean flag = false;
			report.createRow();
			report.endRow();
			int cnt = 0;
			List<String> outerHTML = new ArrayList <String> ();
			Actions action = new Actions(driver.getWebDriver());
			action.sendKeys(Keys.TAB).build().perform();
//			action.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).keyUp(Keys.CONTROL).build().perform();
			WebElement first = driver.getWebDriver().switchTo().activeElement();
			action.sendKeys(Keys.TAB).build().perform();
			WebElement current = driver.getWebDriver().switchTo().activeElement();
			int windowhandles = driver.getWindowHandles().size();
			String currentURL = driver.getCurrentUrl();
//			int visibleelements = getvisibleelementcount();
			while (!current.equals(first)) 
			{
				current = driver.switchTo().activeElement();
				if (!driver.switchTo().activeElement().equals(current))
				{
					flag = true;
					outerHTML.add(getOuterHTML(current));
					cnt++;
				}
				//added to check no new window opens

				if (driver.getWindowHandles().size()!=windowhandles){
					flag = true;
					outerHTML.add(getOuterHTML(current));
					cnt++;
				}

				//added to check if no URL gets opened
				if (!driver.getCurrentUrl().equals(currentURL))
				{
					flag = true;
					outerHTML.add(getOuterHTML(current));
					cnt++;
				}

				//				added to check the number of visible elements
				/*
				 * Introduces performance lag
				 * commenting out this blick
				 */

				/*
				if (visibleelements!=getvisibleelementcount())
				{
					flag = true;
					outerHTML.add(getOuterHTML(current));
					cnt++;
				}
				 */
				action.sendKeys(Keys.ARROW_DOWN).build().perform();
				if (!driver.getWebDriver().switchTo().activeElement().equals(current))
				{
					current = driver.getWebDriver().switchTo().activeElement();
					if (!driver.switchTo().activeElement().equals(current))
					{
						flag = true;
						outerHTML.add(getOuterHTML(current));
						cnt++;
					}
					//added to check no new window opens

					if (driver.getWindowHandles().size()!=windowhandles){
						flag = true;
						outerHTML.add(getOuterHTML(current));
						cnt++;
					}

					//added to check if no URL gets opened
					if (!driver.getCurrentUrl().equals(currentURL))
					{
						flag = true;
						outerHTML.add(getOuterHTML(current));
						cnt++;
					}
					
					action.sendKeys(Keys.ARROW_DOWN).build().perform();
					while(!driver.switchTo().activeElement().equals(current))
					{
						//added to check no new window opens

						if (driver.getWindowHandles().size()!=windowhandles){
							flag = true;
							outerHTML.add(getOuterHTML(current));
							cnt++;
						}

						//added to check if no URL gets opened
						if (!driver.getCurrentUrl().equals(currentURL))
						{
							flag = true;
							outerHTML.add(getOuterHTML(current));
							cnt++;
						}
						current = driver.getWebDriver().switchTo().activeElement();
						action.sendKeys(Keys.ARROW_DOWN).build().perform();
					}
				}
				current = driver.getWebDriver().switchTo().activeElement();
				action.sendKeys(Keys.TAB).build().perform();
			}
				

		
			report.setErrorCount(cnt);
			report.createRow();
			report.headings("WCAG Guideline 2.4.7 (Level AA)");
			report.headings("Total Number of Errors : " +cnt);
			report.endRow();
			if(!flag) {
				report.createRow();
				report.passStatements("Focus Visible");
				report.passStatements("No elements where keyboard focus indicator is not visible");
				report.endRow();
			} else {
				for (int i=0; i<outerHTML.size(); i++) {
					report.createRow();
					report.failStatements("Focus Visible - focus indicator not visible");
					report.failStatements("<code class=\"input\">"+outerHTML.get(i)+"</code>");
					report.endRow();
				}
			}
		} catch (Exception e) {
			System.err.println("Exception Message : " +e.getMessage());
		}

	}
	
	

	public void getcolorContrast_wcag_1_4_3(List<String> xpathofelement)
	{
		/*
		 * WCAG 1.4.1 - Use of Color
		 */
		updateMessageBox("Executing AODA Guideline 1.4.3...");
		int cnt = 0;
		double threshold = 0.00;
		List<String> outerHTML = new ArrayList<String>();
		List<String> textofelement = new ArrayList<String>();
		List<String> color = new ArrayList<String>();
		List<String> bgcolor = new ArrayList<String>();
		List<String> contrast = new ArrayList<String>();
		
		try
		{
		
		for (String string : xpathofelement) {
		
			WebElement element = driver.getWebDriver().findElement(By.xpath(string));
			if (element.getTagName().equals("input"))
			{
				if (!element.getAttribute("name").equals(""))
				{
					textofelement.add(element.getAttribute("name").trim());
				}
				else if(!element.getAttribute("role").equals(""))
				{
					textofelement.add(element.getAttribute("role").trim());
				}
				else if(!element.getAttribute("value").equals(""))
				{
					textofelement.add(element.getAttribute("value").trim());
				}
				else
				{
					textofelement.add("No text for the input element");
				}
				
			}
			else
			{
				textofelement.add(element.getAttribute("innerText").trim());
			}
			
			String fontcolor = element.getCssValue("color");
			color.add(fontcolor);
			String fontsize = element.getCssValue("font-size");
			String backgroundcolor = getBackgroundcolor(element);
			bgcolor.add(backgroundcolor);
			double ratio = getContrast(getLuminance(fontcolor), getLuminance(backgroundcolor));
			
			contrast.add(String.valueOf(ratio));
			
			if (getIntegeroutofString(fontsize)>18)
			{
				threshold = 3.00;
				if (ratio<threshold)
				{
					cnt++;
					outerHTML.add(getOuterHTML(element));
				}
				
			}
			else
			{
				threshold = 4.50;
				if (ratio<threshold)
				{
					cnt++;
					outerHTML.add(getOuterHTML(element));
				}
			}
		}
		
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		//report header
		createExcelFileforcolorcontrast(Allocator.currentTC.get(counter) + "_ColorContrast_"+driver.getTitle(), textofelement, color, bgcolor, contrast);
		report.setErrorCount(cnt);
		report.createRow();
		report.headings("WCAG Guideline 1.4.3 (Level AA)");
		report.headings("Total Number of Errors :"+cnt);
		report.endRow();
		
		
		if (outerHTML.size()==0)
		{
			report.createRow();
			report.infoStatements("Color Contrast");
			report.passStatements("No elements with improper color contrast");
			report.endRow();
		}
		else{
		for (String string : outerHTML) {
			report.createRow();
			report.infoStatements("Color Contrast");
			report.failStatements("<code class=\"input\">"+string+"</code>");
			report.endRow();
			
		}
		}
		
		report.createRow();
		report.infoStatements("Color Contrast Spreadsheet");
		String link = frameworkParameters.getReportPath() + "\\" +Allocator.currentTC.get(counter)+ "_ColorContrast_"+driver.getTitle()+".xlsx";
		report.infoStatements("<a href = \""+link+"\">Color Contrast</a>");
		report.endRow();
		
	}
	
public void checkHeadings_wcag_2_4_6(){
		
		/*
		 * wcag 2.4.6 - Check header elements and their correspodning hierarchy
		 */
		updateMessageBox("Executing AODA Guideline 2.4.6...");
		try{
		List<WebElement> listofheaders = driver.getWebDriver().findElements(By.xpath("//*[self::label or self::p or self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6 or self::span]"));
        List<Integer> level = new ArrayList<Integer>();
        
        ListIterator<WebElement> iterator = listofheaders.listIterator();
        while(iterator.hasNext())
        {
        	WebElement element = iterator.next();
        	if (!element.isDisplayed())
        	{
        		iterator.remove();
        	}
        }
        
        for (WebElement webElement : listofheaders) 
        {

        		level.add(getIndex(webElement));
        }
        
        Collections.sort(level);
        int min = level.get(0);
        
        List<Integer> index = new ArrayList<Integer>();
        List<String> tagname = new ArrayList<String>();
        List<String> xpath = new ArrayList<String>();
        List<String> text = new ArrayList<String>();
        for (WebElement webElement : listofheaders) {
               
        	if(!webElement.getAttribute("innerText").trim().equals(""))
        	{
        		index.add(getIndex(webElement)-min);
        		tagname.add(webElement.getTagName());
        		xpath.add(getAbsoluteXPath(webElement));
        		text.add(webElement.getAttribute("innerText"));
        	}   
        }
        
        writeheaderhirerachy(Allocator.currentTC.get(counter)+ "_Headers_"+driver.getTitle(),index, tagname, xpath, text);
        report.setErrorCount(0);
        report.createRow();
		report.headings("WCAG Guideline 2.4.6 (Level AA)");
		report.headings("Check link below for errors");
		report.endRow();
		report.createRow();
		report.infoStatements("Headings and Labels in Page");
		String link = frameworkParameters.getReportPath() + "\\" +Allocator.currentTC.get(counter)+ "_Headers_"+driver.getTitle()+".xlsx";
		report.infoStatements("<a href = \""+link+"\">Headers</a>");
		report.endRow();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
	}
	
	
	public void languageofParts_wcag_3_1_2()
	{
		/*
		 * WCAG Guideline 3.1.2
		 */
		updateMessageBox("Executing AODA Guideline 3.1.2...");
		report.createRow();
		report.endRow();
		
		List<WebElement> listofelements = driver.getWebDriver().findElements(By.xpath("//*[self::a or self::label or self::img or self::p or self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6 or self::l1 or self::ul or self::select or self::option or self::span]"));
		List<String> text = new ArrayList<>();
		List<String> outerHTML = new ArrayList<>();
		List<String> language = new ArrayList<>();
		
		try
		{
		for (WebElement webElement : listofelements) {
			
			if (webElement.getTagName().equals("img"))
			{
				if(!webElement.getAttribute("alt").trim().equals(""))
				{
					text.add(webElement.getAttribute("alt").trim());
					outerHTML.add(getOuterHTMLplaintext(webElement));
					language.add(getLanguage(webElement));
				}
			}
			else{
				if(!webElement.getAttribute("innerText").trim().equals(""))
				{
					text.add(webElement.getAttribute("innerText").trim());
					outerHTML.add(getOuterHTMLplaintext(webElement));
					language.add(getLanguage(webElement));
				}
			}
		}
		report.setErrorCount(0);
		createExcelFileforDocumentLanguage(Allocator.currentTC.get(counter)+ "_LanguageOfParts_"+driver.getTitle(), text, language, outerHTML);
		report.headings("WCAG Guideline 3.1.2");
		report.headings("Ckeck the link below for errors");
		report.endRow();
		
		
		report.createRow();
		report.infoStatements("Language of Parts");
		String link = frameworkParameters.getReportPath() + "\\" +Allocator.currentTC.get(counter)+ "_LanguageOfParts"+driver.getTitle()+".xlsx";
		report.infoStatements("<a href = \""+link+"\">Language of Parts</a>");
		report.endRow();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
	}
	
	public void errorsuggestion_wcag_3_3_3(List<String> xpathlocators)
	{
		updateMessageBox("Executing AODA Guideline 3.3.3...");
		
		boolean flag = false;
		int cnt = 0;
		List<String> outerHTML = new ArrayList<>();
		List<String> message = new ArrayList<>();
		List<String> outerHTMLplaintext = new ArrayList<>();
		
		
		for (String string : xpathlocators) {

			if (driver.getWebDriver().findElement(By.xpath(string)).isDisplayed())
			{

				if(driver.getWebDriver().findElement(By.xpath(string)).getAttribute("innerText").toLowerCase().contains("error")||driver.getWebDriver().findElement(By.xpath(string)).getAttribute("innerText").toLowerCase().contains("alert"))
				{
					message.add(driver.getWebDriver().findElement(By.xpath(string)).getAttribute("innerText"));
					outerHTMLplaintext.add(getOuterHTMLplaintext(driver.getWebDriver().findElement(By.xpath(string))));
					continue;
				}
				else{
					flag = true;
					cnt++;
					message.add(driver.getWebDriver().findElement(By.xpath(string)).getAttribute("innerText"));
					outerHTMLplaintext.add(getOuterHTMLplaintext(driver.getWebDriver().findElement(By.xpath(string))));
					outerHTML.add(getOuterHTML(driver.getWebDriver().findElement(By.xpath(string))));
				}
			}
			else{
				flag = true;
				cnt++;
				message.add(driver.getWebDriver().findElement(By.xpath(string)).getAttribute("innerText"));
				outerHTMLplaintext.add(getOuterHTMLplaintext(driver.getWebDriver().findElement(By.xpath(string))));
				outerHTML.add(getOuterHTML(driver.getWebDriver().findElement(By.xpath(string))));

			}

		}
		
		report.setErrorCount(cnt);
		report.createRow();
		report.headings("WCAG Guideline 3.3.3 (Level AA)");
		report.headings("Total Errors :"+cnt);
		report.endRow();
		
		createExcelFileforError(Allocator.currentTC.get(counter)+"_ErrorSuggestion_"+driver.getTitle(), message, outerHTMLplaintext);
		report.createRow();
		report.infoStatements("Error Suggestion - list of error messages");
		String link = frameworkParameters.getReportPath() + "\\"+Allocator.currentTC.get(counter)+"_ErrorSuggestion_"+driver.getTitle()+".xlsx";
		report.infoStatements("<a href = \""+link+"\">Error Messages</a>");
		report.endRow();
		
		
		if(cnt > 0)
		{
			for (int i = 0; i < outerHTML.size(); i++) {
				report.createRow();
				report.infoStatements("Error Suggestion - Error Message not proper");
				report.failStatements("<code class=\"input\">"+outerHTML.get(i)+"</code>");
				report.endRow();
			}
			
		}
		else{
			
			report.createRow();
			report.infoStatements("Error Suggestion");
			report.passStatements("All the required error messages are appearing");
			report.endRow();
			
		}
	}
	
	public void errorPrevention_wcag_3_3_4(List<String> xpathofnavigationbuttons)
	{
		/*
		 * WCAG Guideline 3.3.4
		 */
		updateMessageBox("Executing AODA Guideline 3.3.4...");
		
		int cnt = 0;
		List<String> outerHTML = new ArrayList<>();
		try
		{
		
		for (String string : xpathofnavigationbuttons) 
		{
			if (driver.getWebDriver().findElement(By.xpath(string)).isDisplayed()&&driver.getWebDriver().findElement(By.xpath(string)).isDisplayed())
			{
				continue;
			}
			else{
				
				cnt++;
				outerHTML.add(getOuterHTML(driver.getWebDriver().findElement(By.xpath(string))));
			}
		}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		report.createRow();
		report.headings("WCAG Guideline 3.3.4 (Level AA)");
		report.headings("Total Errors: "+cnt);
		report.endRow();
		
		if (cnt > 0)
		{
			for (String html : outerHTML) 
			{
				report.createRow();
				report.infoStatements("Navigation (Back/Forward) button not present");
				report.failStatements("<code class=\"input\">"+html+"</code>");
				report.endRow();	
			}
		
		}
		else
		{
			report.createRow();
			report.infoStatements("Presence of NavigationButtons (Back/Forward)");
			report.passStatements("Required Navigation buttons present");
			report.endRow();
		}
	}
	
	

	
	public void launchMessageBox()
	{

		frame = new JFrame();

		frame.setTitle("AODA Test Execution");
		frame.setLocationRelativeTo(null);
		label = new JLabel("Starting WCAG Level AA guidelines test execution...");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setVerticalAlignment(SwingConstants.CENTER);

		label.setBounds(10, 20, 300, 30);
		label.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
		frame.add(label);
		frame.setSize(new Dimension(350,100));
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
	}

	private void updateMessageBox(String text)
	{
		label.setText(text);
		sleep(2);

	}

	public void closeMessageBox()
	{
		frame.setVisible(false);

	}
	
	public void sleep(int seconds)
	{
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private double getLuminance(String color)
	{
		
			double luminance = 0.0;
			String rgb = color.substring(color.indexOf("(")+1, color.length()-1);
			
			String[] rgbcolors = rgb.split(",");
			/*
			 * Code added to get the luminance as per w3c standards
			 */
			
			double R = Integer.parseInt(rgbcolors[0].trim())/255;
			if (R<=0.03928)
			{
				R = R/12.92;
			}
			else{
				Math.pow(((R+0.055)/1.055), 2.4);
			}
			
			double G = Integer.parseInt(rgbcolors[1].trim())/255;
			if (G<=0.03928)
			{
				G = G/12.92;
			}
			else{
				Math.pow(((G+0.055)/1.055), 2.4);
			}
			
			double B = Integer.parseInt(rgbcolors[2].trim())/255;
			if (B<=0.03928)
			{
				B = B/12.92;
			}
			else{
				Math.pow(((B+0.055)/1.055), 2.4);
			}
			
			luminance = (0.2126*R + 0.7152*G + 0.0722*B)+0.05;
			/*
			 * End of code to calculate
			 * luminance based on w3c standards
			 */
			
//			luminance = (299*Integer.parseInt(rgbcolors[0].trim())+587*Integer.parseInt(rgbcolors[1].trim())+114*Integer.parseInt(rgbcolors[2].trim()))/1000;
			
			return luminance;
		}

	
	private int getIntegeroutofString(String string){
		
		int i=0;
		Pattern p = Pattern.compile("-?\\d+");
		Matcher m = p.matcher(string);
		while (m.find()) {
		  String number=m.group();
		  i= Integer.parseInt(number);
		}
		
		return i;
	}
	
	private boolean elementvisible(WebDriver driver, WebElement element)
	{
		boolean flag = false;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		
		try {
			
			wait.until(ExpectedConditions.visibilityOf(element));
			flag = true;
		} catch (Exception e) {

			flag = false;
		}
		
		return flag;
		
	}
	
	private String getBackgroundcolor(WebElement element)
	{
		
		String backgroundcolor = "";
		if (element.getCssValue("background-color").isEmpty()||element.getCssValue("background-color").equalsIgnoreCase("rgba(0, 0, 0, 0)"))
		{
			String xpath = getAbsoluteXPath(element);
			WebElement parent = element.findElement(By.xpath(xpath+"/.."));
			try
			{
				while(parent.getCssValue("background-color").equalsIgnoreCase("rgba(0, 0, 0, 0)"))
				{
					xpath = getAbsoluteXPath(parent);
					parent = parent.findElement(By.xpath(xpath+"/.."));
				}
				backgroundcolor = parent.getCssValue("background-color");
			}
			catch (Exception e)
			{
				backgroundcolor = "rgba(255, 255, 255, 1)";
			}
			
		}
		else{
			
			backgroundcolor = element.getCssValue("background-color");
			
		}
		
		
		return backgroundcolor;
		
		
	}
	
	private double getContrast(double a, double b)
	{
		double ratio = 0.00;
		
		
		if (a/b<1.00)
		{
			ratio = b/a;
		}
		else
		{
			ratio = a/b;
		}
		
		return ratio;
	}
	

    private int getIndex(WebElement element)
    {
           String xpath = getAbsoluteXPath(element);
           return xpath.substring(10, xpath.length()).split("/").length-1;
           
    }
    
	private String getLanguage(WebElement element)
	{
		String language = "";
		
		String xpath = getAbsoluteXPath(element);
		xpath = xpath+"/ancestor-or-self::*";
		try
		{

			List<WebElement> lisofparents = driver.getWebDriver().findElements(By.xpath(xpath));

			for (WebElement webElement : lisofparents) {

				if (!webElement.getAttribute("lang").equals(""))
				{
					language = webElement.getAttribute("lang");
				}
			}
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}

		return language;
		
		
	}
	

	
}