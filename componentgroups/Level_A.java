	package componentgroups;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import allocator.Allocator;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;


public class Level_A extends ReusableLibrary {

	private JFrame frame = null;
	private JLabel label = null;
	private JTextArea textarea = null;
	private List<WebElement> elementstobehighlighted;
	
	public Level_A(ScriptHelper scriptHelper) {
		super(scriptHelper);
		elementstobehighlighted = new ArrayList<>();
		report.createRow();
		report.PageTitleheadings("Level A : ");
//		report.PageTitleheadings(" Execution Results");
		report.endRow();
		
	}

	public void checkNonTextContent_wcag1_1_1() throws Exception {
		
		/*
		 * WCAG Guideline 1.1.1 (Level A)
		 */
		updateMessageBox("Executing WCAG Guideline 1.1.1...");
		try {
			boolean flag = false;
		
			report.createRow();
			report.endRow();
			List<WebElement> nonTextElements = driver.getWebDriver().findElements(By.tagName("img"));
//			nonTextElements.addAll(driver.getWebDriver().findElements(By.tagName("button")));
//			nonTextElements.addAll(driver.getWebDriver().findElements(By.tagName("input")));
			List<String> xpath = new ArrayList <String> ();
			/*
			 * Added for Reporting changes
			 */
			List<String> outerHTMLimage = new ArrayList <String> ();
			List<String> outerHTMLothers = new ArrayList <String> ();
			List<String> imgsrc = new ArrayList <String> ();
			List<String> imgsrcothers = new ArrayList <String> ();
			List<String> decorativeimages = new ArrayList <String> ();
			List<String> srcdecorativeimages = new ArrayList <String> ();
			
			List<String> decorativeimagesnonblankalttext = new ArrayList <String> ();
			List<String> srcdecorativeimagesnonblankalttext = new ArrayList <String> ();
			/*
			 * End of changes
			 */
			int cnt = 0;
//			for (WebElement element : nonTextElements) {
//				if (element.getTagName().equals("img")) {
//					if (element.getAttribute("alt")!=null) {
//						if (element.getAttribute("alt").isEmpty()) {
//							xpath.add(getAbsoluteXPath(element));
//							outerHTMLimage.add(getOuterHTML(element));
//							imgsrc.add(element.getAttribute("src"));
//							elementstobehighlighted.add(element);
//							flag = true;
//							cnt++;
//						}
//					} else if (element.getAttribute("innerText")!=null) {
//						if (element.getAttribute("innerText").isEmpty()) {
//							xpath.add(getAbsoluteXPath(element));
//							outerHTMLimage.add(getOuterHTML(element));
//							imgsrc.add(element.getAttribute("src"));
//							elementstobehighlighted.add(element);
//							flag = true;
//							cnt++;
//						}
//					} else {
//						xpath.add(getAbsoluteXPath(element));
//						outerHTMLimage.add(getOuterHTML(element));
//						imgsrc.add(element.getAttribute("src"));
//						elementstobehighlighted.add(element);
//						flag = true;
//						cnt++;
//					}
//				} /*else {
//					if (element.getAttribute("name")!=null) {
//						if (element.getAttribute("name").isEmpty()) {
//							xpath.add(getAbsoluteXPath(element));
//							outerHTMLothers.add(getOuterHTML(element));
//							elementstobehighlighted.add(element);
//							flag = true;
//							cnt++;
//						}
//					} else if (element.getAttribute("role")!=null) {
//						if (element.getAttribute("role").isEmpty()) {
//							xpath.add(getAbsoluteXPath(element));
//							outerHTMLothers.add(getOuterHTML(element));
//							elementstobehighlighted.add(element);
//							flag = true;
//							cnt++;
//						}
//					} else if (element.getAttribute("type")!=null) {
//						if (element.getAttribute("type").isEmpty()) {
//							xpath.add(getAbsoluteXPath(element));
//							outerHTMLothers.add(getOuterHTML(element));
//							elementstobehighlighted.add(element);
//							flag = true;
//							cnt++;
//						}
//					} else {
//						xpath.add(getAbsoluteXPath(element));
//						outerHTMLothers.add(getOuterHTML(element));
//						elementstobehighlighted.add(element);
//						flag = true;
//						cnt++;
//					}
//				}*/
//			}
			
			for (WebElement element : nonTextElements) {
				
				if(element.isEnabled()&&element.isDisplayed()){	

					if (checkifparentislink(element))
					{
						if (element.getAttribute("alt")!=null) {


							if (element.getAttribute("alt").isEmpty()) {
								//								xpath.add(getAbsoluteXPath(element));
								outerHTMLimage.add(getOuterHTML(element));
								imgsrc.add(element.getAttribute("src"));
								//								elementstobehighlighted.add(element);
								flag = true;
								cnt++;
							}else{
								outerHTMLothers.add(getOuterHTML(element));
								imgsrcothers.add(element.getAttribute("src"));
							}

						}

					}
					else
					{
						if (element.getAttribute("alt")!=null)
						{
						
						if (element.getAttribute("alt").equals("")) {
							//								xpath.add(getAbsoluteXPath(element));
							decorativeimages.add(getOuterHTML(element));
							srcdecorativeimages.add(element.getAttribute("src"));
							//								elementstobehighlighted.add(element);
//							flag = true;
							cnt++;
						}else{
							decorativeimagesnonblankalttext.add(getOuterHTML(element));
							srcdecorativeimagesnonblankalttext.add(element.getAttribute("src"));
						}
						}
					}


				}



			} 
			
			report.setErrorCount(cnt);
			report.createRow();
			report.headings("WCAG Guideline 1.1.1 (Level A)");
			report.headings("Total Number of Errors : " +cnt);
			report.endRow();
			if(!flag) {
				report.createRow();
				report.passStatements("Missing Non-Text Content");
				report.passStatements("No Images or Buttons with Missing Alt-Text or Inner Text");
				report.endRow();
				

			} else {
				for (int i=0; i<outerHTMLimage.size(); i++) {
					report.createRow();
					report.failStatements("Images missing alternate text");
					//report.failStatements(xpath.get(i)+"<br>"+outerHTML.get(i));
					//report.failStatements("<code class=\"input\">"+"&lt;"+outerHTMLimage.get(i)+"&gt;"+"</code><br><img src=\""+imgsrc.get(i)+"\" height=\"50\" border=\"1\"");
					report.failStatements("<code class=\"input\">"+outerHTMLimage.get(i)+"</code><br><img src=\""+imgsrc.get(i)+"\" height=\"50\" border=\"1\"");
					report.endRow();
				}
				
			}
			
			for (int i=0; i<outerHTMLothers.size(); i++) {
				report.createRow();
				report.infoStatements("Images having alternate text");
				//report.failStatements(xpath.get(i)+"<br>"+outerHTML.get(i));
				//report.failStatements("<code class=\"input\">"+"&lt;"+outerHTMLothers.get(i)+"&gt;"+"</code>");
//				report.passStatements("<code class=\"input\">"+outerHTMLothers.get(i)+"</code>");
				report.infoStatements("<code class=\"input\">"+outerHTMLothers.get(i)+"</code><br><img src=\""+imgsrcothers.get(i)+"\" height=\"50\" border=\"1\"");
				report.endRow();
			}
			
			for (int i = 0; i < decorativeimages.size(); i++) {
				report.createRow();
				report.passStatements("Decorative Images having NO alternate text");
				report.passStatements("<code class=\"input\">"+decorativeimages.get(i)+"</code><br><img src=\""+srcdecorativeimages.get(i)+"\" height=\"50\" border=\"1\"");
				
			}
			for (int i = 0; i < decorativeimagesnonblankalttext.size(); i++) {
				report.createRow();
				report.failStatements("Decorative Images having non blank alternate text");
				report.failStatements("<code class=\"input\">"+decorativeimagesnonblankalttext.get(i)+"</code><br><img src=\""+srcdecorativeimagesnonblankalttext.get(i)+"\" height=\"50\" border=\"1\"");
				
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public void checkInfoAndRelationships_wcag_1_3_1 () throws Exception {
		
		/*
		 * WCAG Guideline 1.3.1 (Level A)
		 */
		updateMessageBox("Executing WCAG Guideline 1.3.1...");
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		report.createRow();
		report.endRow();
		List<WebElement> lists1 = driver.getWebDriver().findElements(By.tagName("dt"));
		lists1.addAll(driver.getWebDriver().findElements(By.tagName("dd")));
		List<WebElement> lists2 = driver.getWebDriver().findElements(By.tagName("li"));
		List<WebElement> lists3 = driver.getWebDriver().findElements(By.tagName("b"));
		lists3.addAll(driver.getWebDriver().findElements(By.tagName("i")));
		List<WebElement> lists4 = driver.getWebDriver().findElements(By.tagName("th"));
		List<WebElement> lists5 = driver.getWebDriver().findElements(By.tagName("td"));
		List<String> xpath1 = new ArrayList <String> ();
		List<String> xpath2 = new ArrayList <String> ();
		List<String> xpath3 = new ArrayList <String> ();
		
		List<String> outerHTML1 = new ArrayList <String> ();
		List<String> outerHTML2 = new ArrayList <String> ();
		List<String> outerHTML3 = new ArrayList <String> ();
		int cnt1 = 0;
		int cnt2 = 0;
		int cnt3 = 0;
		int cnt4 = 0;
		if (lists1.size()!=0) {
			for (WebElement element : lists1) {
				if (!element.findElement(By.xpath("..")).getTagName().equals("dl")) {
					xpath1.add(getAbsoluteXPath(element));
					outerHTML1.add(getOuterHTML(element));
					elementstobehighlighted.add(element);
					flag1 = true;
					cnt1++;
				}
			}
		}
		if (lists2.size()!=0) {
			for (WebElement element : lists2) {
				if (!element.findElement(By.xpath("..")).getTagName().equals("ul")) {
					if (!element.findElement(By.xpath("..")).getTagName().equals("ol")) {
						xpath2.add(getAbsoluteXPath(element));
						outerHTML2.add(getOuterHTML(element));
						elementstobehighlighted.add(element);
						flag2 = true;
						cnt2++;
					}
				}
			}
		}
		if (lists3.size()!=0) {
			for (WebElement element : lists3) {
				xpath3.add(element.getAttribute("innerText"));
				outerHTML3.add(getOuterHTML(element));
				elementstobehighlighted.add(element);
				flag3 = true;
				cnt3++;
			}
		}
		if (lists4.size()!=lists5.size()) {
			flag4 = true;
			cnt4++;
		}
		
		int cnt = cnt1 + cnt2 + cnt3 + cnt4;
		report.setErrorCount(cnt);
		report.createRow();
		report.headings("WCAG Guideline 1.3.1 (Level A)");
		report.headings("Total Number of Errors : " +cnt);
		report.endRow();
		if(!flag1) {
			report.createRow();
			report.passStatements("Missing Info and Relationships");
			report.passStatements("No 'DD' or 'DT' with Appropriate 'DD' Parent");
			report.endRow();
		} else {
			for (int i=0; i<outerHTML1.size(); i++) {
				report.createRow();
				report.failStatements("Missing Info and Relationships");
				//report.failStatements("<code class=\"input\">"+"&lt;"+outerHTML1.get(i)+"&gt;"+"</code>"+ " does not have 'DD' Parent");
				report.failStatements("<code class=\"input\">"+outerHTML1.get(i)+"</code>"+ " does not have 'DD' Parent");
				report.endRow();
			}
		}
		if(!flag2) {
			report.createRow();
			report.passStatements("Missing Info and Relationships");
			report.passStatements("No 'LI' with Appropriate 'UL' or 'OL' Parent");
			report.endRow();
		} else {
			for (int i=0; i<outerHTML2.size(); i++) {
				report.createRow();
				report.failStatements("Missing Info and Relationships");
				//report.failStatements("<code class=\"input\">"+"&lt;"+outerHTML2.get(i)+"&gt;"+"</code>"+ " does not have 'UL' or 'OL' Parent");
				report.failStatements("<code class=\"input\">"+outerHTML2.get(i)+"</code>"+ " does not have 'UL' or 'OL' Parent");
				report.endRow();
			}
		}
		if(!flag3) {
			report.createRow();
			report.passStatements("Missing Info and Relationships");
			report.passStatements("No 'B' or 'I' Tags");
			report.endRow();
		} else {
			for (int i=0; i<outerHTML3.size(); i++) {
				report.createRow();
				report.failStatements("Missing Info and Relationships");
//				report.failStatements("<code class=\"input\">"+"&lt;"+outerHTML3.get(i)+"&gt;"+"</code>"+ " have 'B' or 'I' Tags");
				report.failStatements("<code class=\"input\">"+outerHTML3.get(i)+"</code>"+ " have 'B' or 'I' Tags");
				report.endRow();
			}
		}
		if(!flag4) {
			report.createRow();
			report.passStatements("Missing Info and Relationships");
			report.passStatements("All Table Data have relevant Table Headers");
			report.endRow();
		} else {
			for (int i=0; i<xpath3.size(); i++) {
				report.createRow();
				report.failStatements("Missing Info and Relationships");
				report.failStatements("All Table Data does not have relevant Table Headers");
				report.endRow();
			}
		}
	}
	
	public void meaningfulseqeunce_wcag_1_3_2()
	{
		updateMessageBox("Executing AODA gudeline 1.3.2...");
		String xpathofelements = "//body//*[self::a or self::button or self::input or self::label or self::ul or self::li or self::select or self::option or self::img or self::p or self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6 or self::span]";
		List<WebElement> elements = driver.getWebDriver().findElements(By.xpath(xpathofelements));
		
		List<String> tagname = new ArrayList<>();
		List<String> textofcontrol = new ArrayList<>();
		List<String> typeofcontrol = new ArrayList<>();
		List<String> outerHTML = new ArrayList<>();
		
		try {
			
			for (WebElement element : elements) {
				
//				if(elementvisible(driver.getWebDriver(), element))
				if(element.isDisplayed())
				{
					tagname.add(element.getTagName());
					typeofcontrol.add(getElementType(element));
					if(element.getTagName().equals("input"))
					{
						if (element.getAttribute("type").equals("radio"))
						{
							textofcontrol.add(getLabelforinputelement(element));
						}
						else if(element.getAttribute("type").equals("email")||element.getAttribute("type").equals("password")||element.getAttribute("type").equals("text")||element.getAttribute("type").equals("email")||element.getAttribute("type").equals("username"))
						{
							textofcontrol.add(getLabelforinputelement(element));


						} else if(element.getAttribute("type").equals("combo")){

							textofcontrol.add(getLabelforinputelement(element));
						}
						else if(element.getAttribute("type").equals("check"))
						{
							textofcontrol.add(getLabelforinputelement(element));
						}

						else {

							textofcontrol.add("No Label text got the corresponding input field");
						}


					}
					else if(element.getTagName().equals("img"))
					{
						if (!element.getAttribute("alt").equals(""))
						{
							textofcontrol.add(element.getAttribute("alt"));
						}
						else{
							textofcontrol.add("No Alternate Text for the Image");
						}
					}
					//					else{
					//						if (element.getTagName().equals("div"))
					//						{
					//							String text = getTextofElement(element);
					//							if(!text.equals(""))
					//							{
					//								textofcontrol.add(text);
					//							}
					//							else{
					//								textofcontrol.add("No text for the associated control");
					//							}
					//						}
					else 
					{
						if(!element.getAttribute("innerText").trim().equals(""))
						{
							textofcontrol.add(element.getAttribute("innerText"));
						}
						else{
							textofcontrol.add("No text for the associated control");
						}

						//						}
					}

					outerHTML.add(getOuterHTMLplaintext(element));

				}
					
				
			}
			
			report.setErrorCount(0);
			report.createRow();
			report.headings("WCAG Guideline 1.3.2 (Level A)");
			report.headings("Check for errors in link below");
			report.endRow();
			
			createExcelFileformeaningfulseuqnce(Allocator.currentTC.get(counter)+ "_MeaningfulSequence_"+driver.getTitle(), tagname, textofcontrol, typeofcontrol, outerHTML);
			report.createRow();
			report.infoStatements("Meaningful Seuqnce");
			String link = frameworkParameters.getReportPath() + "\\" +Allocator.currentTC.get(counter)+ "_MeaningfulSequence_"+driver.getTitle()+".xlsx";
			report.infoStatements("<a href = \""+link+"\">Meaningful Sequence</a>");
			report.endRow();
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			
		}
		
	}
	
	
	public void checkSensoryCharacteristics_wcag_1_3_3 () {
		
		/*
		 * WCAG 1.3.3 - Sensory Characteristics
		 */
		updateMessageBox("Executing WCAG Guideline 1.3.3...");
		boolean flag = false;
		report.createRow();
		report.endRow();
		List<WebElement> nonTextElements = driver.getWebDriver().findElements(By.tagName("input"));
//		List<String> xpath = new ArrayList <String> ();
		/*
		 * Added for reporting changes
		 */
		List<String> inputfield = new ArrayList<>();
		List<String> outerHTML = new ArrayList<>();
		List<String> roleortype = new ArrayList<>();
		int cnt = 0;
		for (WebElement element : nonTextElements) {
//			if (elementvisible(driver.getWebDriver(), element))
//			if(element.isDisplayed())
//			{
			if (element.getAttribute("role")!=null) {
				if (!element.getAttribute("role").isEmpty()) {
					String xpath = getAbsoluteXPath(element);
					inputfield.add(getLabelforinputelement(element));
					outerHTML.add(getOuterHTMLplaintext(element));
					roleortype.add(element.getAttribute("role"));
					
				}
			} else if (element.getAttribute("type")!=null) {
				if (!element.getAttribute("type").isEmpty()) {
					String xpath = getAbsoluteXPath(element);
//					WebElement labelelement = driver.findElement(By.xpath("("+xpath+"/preceding::label)[last()]"));
//					String labeltext = labelelement.getAttribute("innerText").trim();
					inputfield.add(getLabelforinputelement(element));
					outerHTML.add(getOuterHTMLplaintext(element));
					roleortype.add(element.getAttribute("type"));
//					flag = true;
					
				}
			} else {
				String xpath = getAbsoluteXPath(element);
//				WebElement labelelement = driver.findElement(By.xpath("("+xpath+"/preceding::label)[last()]"));
//				String labeltext = labelelement.getAttribute("innerText").trim();
				inputfield.add(getLabelforinputelement(element));
				outerHTML.add(getOuterHTMLplaintext(element));
				roleortype.add("No Role or Type Attribute value present");
			}
//		}
		}
		
		report.setErrorCount(0);
		report.createRow();
		report.headings("WCAG Guideline 1.3.3 (Level A)");
		report.headings("Check for errors in link below");
		report.endRow();
		
		createExcelFilesensorycharacters(Allocator.currentTC.get(counter)+ "_InputField_SensoryCharacters_"+driver.getTitle(), inputfield, roleortype, outerHTML);
		report.createRow();
		report.infoStatements("Sensory Characters for Input Fields");
		String link = frameworkParameters.getReportPath() + "\\" +Allocator.currentTC.get(counter)+ "_InputField_SensoryCharacters_"+driver.getTitle()+".xlsx";
		report.infoStatements("<a href = \""+link+"\">Input Field Sensory Characters</a>");
		report.endRow();
		
	}

	public void checkKeyboard_wcag_2_1_1 () throws Exception {
		
		/*
		 * WCAG 2.1.1
		 */
		updateMessageBox("Executing WCAG Guideline 2.1.1...");
		try {
			report.createRow();
			report.endRow();
			
			List <String> list1 = new ArrayList <> ();
			List <String> list2 = new ArrayList <> ();
			List <String> typeofelement = new ArrayList <> ();
			Actions action = new Actions(driver.getWebDriver());
//			action.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).keyUp(Keys.CONTROL).build().perform();
			action.sendKeys(Keys.TAB).build().perform();
//			action.sendKeys(Keys.TAB).build().perform();
			WebElement first = driver.getWebDriver().switchTo().activeElement();
			if (first.getAttribute("name")!=null) {
				if (!first.getAttribute("name").isEmpty()) {
					list1.add(first.getTagName());
					typeofelement.add(getElementType(first));
					list2.add(first.getAttribute("name"));
				}
			} else if (first.getAttribute("role")!=null) {
				if (!first.getAttribute("role").isEmpty()) {
					list1.add(first.getTagName());
					typeofelement.add(getElementType(first));
					list2.add(first.getAttribute("role"));
				}
			} else if (first.getAttribute("type")!=null) {
				if (!first.getAttribute("type").isEmpty()) {
					list1.add(first.getTagName());
					typeofelement.add(getElementType(first));
					list2.add(first.getAttribute("type"));
				}
			} else if (first.getAttribute("alt")!=null) {
				if (!first.getAttribute("alt").isEmpty()) {
					list1.add(first.getTagName());
					typeofelement.add(getElementType(first));
					list2.add(first.getAttribute("alt"));
				}
				else if(!first.getAttribute("innerText").equals(""))
				{
					list1.add(first.getTagName());
					typeofelement.add(getElementType(first));
					list2.add(first.getAttribute("innerText"));
				}
			} else {
				list1.add(first.getTagName());
				typeofelement.add(getElementType(first));
				list2.add("No text associated with the control");
			}
			
			action.sendKeys(Keys.TAB).build().perform();	
			WebElement current = driver.switchTo().activeElement();
			while (!current.equals(first)) {
				if (driver.getWebDriver().switchTo().activeElement().getAttribute("innerText")!=null) {
					if (!driver.getWebDriver().switchTo().activeElement().getAttribute("innerText").isEmpty()) {
						list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
						typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
						list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("innerText"));
					}
				} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("name")!=null) {
					if (!driver.getWebDriver().switchTo().activeElement().getAttribute("name").isEmpty()) {
						list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
						typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
						list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("name"));
					}
				} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("role")!=null) {
					if (!driver.getWebDriver().switchTo().activeElement().getAttribute("role").isEmpty()) {
						list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
						typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
						list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("role"));
					}
				} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("type")!=null) {
					if (!driver.getWebDriver().switchTo().activeElement().getAttribute("type").isEmpty()) {
						list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
						typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
						list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("type"));
					}
				} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("alt")!=null) {
					if (!driver.getWebDriver().switchTo().activeElement().getAttribute("alt").isEmpty()) {
						list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
						typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
						list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("alt"));
					}
				} else {
					list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
					typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
					list2.add("No Text associated with this control");
				}
				
				/*
				 * Code added for menu traversal for dropdown menu(s)
				 */
				action.sendKeys(Keys.ARROW_DOWN).build().perform();
//				sleep(1);
				
				if (!driver.switchTo().activeElement().equals(current))
				{
					if (driver.getWebDriver().switchTo().activeElement().getAttribute("innerText")!=null) {
						if (!driver.getWebDriver().switchTo().activeElement().getAttribute("innerText").isEmpty()) {
							list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
							typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
							list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("innerText"));
						}
					} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("name")!=null) {
						if (!driver.getWebDriver().switchTo().activeElement().getAttribute("name").isEmpty()) {
							list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
							typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
							list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("name"));
						}
					} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("role")!=null) {
						if (!driver.getWebDriver().switchTo().activeElement().getAttribute("role").isEmpty()) {
							list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
							typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
							list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("role"));
						}
					} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("type")!=null) {
						if (!driver.getWebDriver().switchTo().activeElement().getAttribute("type").isEmpty()) {
							list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
							typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
							list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("type"));
						}
					} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("alt")!=null) {
						if (!driver.getWebDriver().switchTo().activeElement().getAttribute("alt").isEmpty()) {
							list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
							typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
							list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("alt"));
						}
					} else {
						list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
						typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
						list2.add("No Text associated with this control");
					}
					
//					current = driver.switchTo().activeElement();
//					action.sendKeys(Keys.TAB).build().perform();
					
					
					
				}
			
				
				/*
				 * end of code added for menu traversal
				 */
				action.sendKeys(Keys.TAB).build().perform();
//				sleep(1);
				current = driver.switchTo().activeElement();
				
				
			}
			createExcelFileforkeyboardoperable(Allocator.currentTC.get(counter) + "_KeyboardOperable_"+driver.getTitle(), list1, typeofelement, list2);
			report.createRow();
			report.headings("WCAG Guideline 2.1.1 (Level A)");
			report.headings("Keyboard Operation");
			report.endRow();
			report.createRow();
			report.infoStatements("Keyboard Operable");
			String link = frameworkParameters.getReportPath() + "\\" +Allocator.currentTC.get(counter)+ "_KeyboardOperable_"+driver.getTitle()+".xlsx";
			report.infoStatements("<a href = \""+link+"\">Keyboard Operation Sequence</a>");
			report.endRow();
		} catch (Exception e) {
			System.err.println("Exception Message : " +e.getMessage());
			e.printStackTrace(System.err);
		}
	}
	
	public void keyboard_sequence(){
		updateMessageBox("Executing WCAG Guideline 2.1.1...");
		try {
			report.createRow();
			report.endRow();
			
			List <String> list1 = new ArrayList <> ();
			List <String> list2 = new ArrayList <> ();
			List <String> typeofelement = new ArrayList <> ();
			Actions action = new Actions(driver.getWebDriver());
//			action.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).keyUp(Keys.CONTROL).build().perform();
			action.sendKeys(Keys.TAB).build().perform();
//			action.sendKeys(Keys.TAB).build().perform();
			WebElement first = driver.getWebDriver().switchTo().activeElement();
			if (first.getAttribute("name")!=null) {
				if (!first.getAttribute("name").isEmpty()) {
					list1.add(first.getTagName());
					typeofelement.add(getElementType(first));
					list2.add(first.getAttribute("name"));
				}
			} else if (first.getAttribute("role")!=null) {
				if (!first.getAttribute("role").isEmpty()) {
					list1.add(first.getTagName());
					typeofelement.add(getElementType(first));
					list2.add(first.getAttribute("role"));
				}
			} else if (first.getAttribute("type")!=null) {
				if (!first.getAttribute("type").isEmpty()) {
					list1.add(first.getTagName());
					typeofelement.add(getElementType(first));
					list2.add(first.getAttribute("type"));
				}
			} else if (first.getAttribute("alt")!=null) {
				if (!first.getAttribute("alt").isEmpty()) {
					list1.add(first.getTagName());
					typeofelement.add(getElementType(first));
					list2.add(first.getAttribute("alt"));
				}
				else if(!first.getAttribute("innerText").equals(""))
				{
					list1.add(first.getTagName());
					typeofelement.add(getElementType(first));
					list2.add(first.getAttribute("innerText"));
				}
			} else {
				list1.add(first.getTagName());
				typeofelement.add(getElementType(first));
				list2.add("No text associated with the control");
			}
			
			action.sendKeys(Keys.TAB).build().perform();	
			WebElement current = driver.switchTo().activeElement();
			while (!current.equals(first)) {
				if (driver.getWebDriver().switchTo().activeElement().getAttribute("innerText")!=null) {
					if (!driver.getWebDriver().switchTo().activeElement().getAttribute("innerText").isEmpty()) {
						list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
						typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
						list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("innerText"));
					}
				} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("name")!=null) {
					if (!driver.getWebDriver().switchTo().activeElement().getAttribute("name").isEmpty()) {
						list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
						typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
						list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("name"));
					}
				} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("role")!=null) {
					if (!driver.getWebDriver().switchTo().activeElement().getAttribute("role").isEmpty()) {
						list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
						typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
						list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("role"));
					}
				} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("type")!=null) {
					if (!driver.getWebDriver().switchTo().activeElement().getAttribute("type").isEmpty()) {
						list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
						typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
						list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("type"));
					}
				} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("alt")!=null) {
					if (!driver.getWebDriver().switchTo().activeElement().getAttribute("alt").isEmpty()) {
						list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
						typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
						list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("alt"));
					}
				} else {
					list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
					typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
					list2.add("No Text associated with this control");
				}
				
				/*
				 * Code added for menu traversal for dropdown menu(s)
				 */
				action.sendKeys(Keys.ARROW_DOWN).build().perform();
//				sleep(1);
				
				if (!driver.switchTo().activeElement().equals(current))
				{
					if (driver.getWebDriver().switchTo().activeElement().getAttribute("innerText")!=null) {
						if (!driver.getWebDriver().switchTo().activeElement().getAttribute("innerText").isEmpty()) {
							list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
							typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
							list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("innerText"));
						}
					} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("name")!=null) {
						if (!driver.getWebDriver().switchTo().activeElement().getAttribute("name").isEmpty()) {
							list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
							typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
							list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("name"));
						}
					} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("role")!=null) {
						if (!driver.getWebDriver().switchTo().activeElement().getAttribute("role").isEmpty()) {
							list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
							typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
							list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("role"));
						}
					} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("type")!=null) {
						if (!driver.getWebDriver().switchTo().activeElement().getAttribute("type").isEmpty()) {
							list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
							typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
							list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("type"));
						}
					} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("alt")!=null) {
						if (!driver.getWebDriver().switchTo().activeElement().getAttribute("alt").isEmpty()) {
							list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
							typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
							list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("alt"));
						}
					} else {
						list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
						typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
						list2.add("No Text associated with this control");
					}
					
					current = driver.switchTo().activeElement();
					action.sendKeys(Keys.ARROW_DOWN).build().perform();
					while(!driver.switchTo().activeElement().equals(current))
					{
						current = driver.switchTo().activeElement();
						if (driver.getWebDriver().switchTo().activeElement().getAttribute("innerText")!=null) {
							if (!driver.getWebDriver().switchTo().activeElement().getAttribute("innerText").isEmpty()) {
								list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
								typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
								list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("innerText"));
							}
						} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("name")!=null) {
							if (!driver.getWebDriver().switchTo().activeElement().getAttribute("name").isEmpty()) {
								list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
								typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
								list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("name"));
							}
						} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("role")!=null) {
							if (!driver.getWebDriver().switchTo().activeElement().getAttribute("role").isEmpty()) {
								list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
								typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
								list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("role"));
							}
						} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("type")!=null) {
							if (!driver.getWebDriver().switchTo().activeElement().getAttribute("type").isEmpty()) {
								list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
								typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
								list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("type"));
							}
						} else if (driver.getWebDriver().switchTo().activeElement().getAttribute("alt")!=null) {
							if (!driver.getWebDriver().switchTo().activeElement().getAttribute("alt").isEmpty()) {
								list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
								typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
								list2.add(driver.getWebDriver().switchTo().activeElement().getAttribute("alt"));
							}
						} else {
							list1.add(driver.getWebDriver().switchTo().activeElement().getTagName());
							typeofelement.add(getElementType(driver.getWebDriver().switchTo().activeElement()));
							list2.add("No Text associated with this control");
						}
						action.sendKeys(Keys.ARROW_DOWN).build().perform();
						
					}
					
				}
			
				action.sendKeys(Keys.TAB).build().perform();
//				sleep(1);
				current = driver.switchTo().activeElement();
			}
			createExcelFileforkeyboardoperable(Allocator.currentTC.get(counter) + "_KeyboardOperable_"+driver.getTitle(), list1, typeofelement, list2);
			report.createRow();
			report.headings("WCAG Guideline 2.1.1 (Level A)");
			report.headings("Keyboard Operation");
			report.endRow();
			report.createRow();
			report.infoStatements("Keyboard Operable");
			String link = frameworkParameters.getReportPath() + "\\" +Allocator.currentTC.get(counter)+ "_KeyboardOperable_"+driver.getTitle()+".xlsx";
			report.infoStatements("<a href = \""+link+"\">Keyboard Operation Sequence</a>");
			report.endRow();
		} catch (Exception e) {
			System.err.println("Exception Message : " +e.getMessage());
			e.printStackTrace(System.err);
		}
	}
	
	public void checkKeyboardtrap_wcag_2_1_2 () throws Exception {
		
		/*
		 * WCAG 2.1.1
		 */
		updateMessageBox("Executing WCAG Guideline 2.1.2...");
		driver.getWebDriver().navigate().refresh();
		try {
			report.createRow();
			report.endRow();
			boolean flag = false;
			int cnt = 0;
			String elementouterHTML = ""; 
			Actions action = new Actions(driver.getWebDriver());
			action.sendKeys(Keys.TAB).build().perform();
			WebElement first = driver.getWebDriver().switchTo().activeElement();
			action.sendKeys(Keys.TAB).build().perform();
			WebElement current = driver.getWebDriver().switchTo().activeElement();
				while (!current.equals(first)) {
					
					action.sendKeys(Keys.ARROW_DOWN).build().perform();
					while(!driver.switchTo().activeElement().equals(current))
					{
						action.sendKeys(Keys.ARROW_DOWN).build().perform();
						current = driver.switchTo().activeElement();
					}
					
					action.sendKeys(Keys.TAB).build().perform();
					
					if (!driver.getWebDriver().switchTo().activeElement().equals(current))
					{
						current = driver.getWebDriver().switchTo().activeElement();
					}
					else{
						flag = true;
						elementouterHTML = getOuterHTML(current);
						cnt++;
						break;
					}
					
					
					
					
			}
			report.setErrorCount(cnt);
			report.createRow();
			report.headings("WCAG Guideline 2.1.2 (Level A)");
			report.headings("Total No of Errors :"+cnt);
			report.endRow();
			report.createRow();
			report.infoStatements("Keyboard Trap");
			if (flag)
			{
				report.failStatements("<code class=\"input\">Trap at this element : &nbsp"+elementouterHTML+"</code>");
			}
			else{
				
				report.passStatements("No keyboard trap present in the page");
			}
			report.endRow();
		} catch (Exception e) {
			System.err.println("Exception Message : " +e.getMessage());
			e.printStackTrace(System.err);
		}
	}


	public void checkBypassBlocks_wcag_2_4_1 () throws Exception {
		
		/*
		 * WCAG 2.4.1
		 */
		updateMessageBox("Executing WCAG Guideline 2.4.1...");
		boolean flag = true;
		report.createRow();
		report.endRow();
		List<WebElement> link = driver.getWebDriver().findElements(By.tagName("a"));
		int cnt = 1;
		for (WebElement element : link) {
			if (element.getAttribute("innerText")!=null) {
				if (element.getAttribute("innerText").equalsIgnoreCase("Skip to content")) {
					flag = false;
					cnt = 0;
					break;
				}
			} else if (element.getAttribute("alt")!=null) {
				if (element.getAttribute("alt").equalsIgnoreCase("Skip to content")) {
					flag = false;
					cnt = 0;
					break;
				}
			} 
		}
		report.setErrorCount(cnt);
		report.createRow();
		report.headings("WCAG Guideline 2.4.1 (Level A)");
		report.headings("Total Number of Errors : "+cnt);
		report.endRow();
		if(!flag) {
			report.createRow();
			report.passStatements("Missing Bypass Blocks");
			report.passStatements("Bypass Block (Skip to main content) is present");
			report.endRow();
		} else {
			report.createRow();
			report.failStatements("Missing Bypass Blocks");
			report.failStatements("Bypass Block (Skip to main content) is not present");
			report.endRow();
		}
	}

	public void checkPageTitle_wcag_2_4_2 () throws Exception {
		
		/*
		 * WCAG 2.4.2
		 */
		updateMessageBox("Executing WCAG Guideline 2.4.2...");
		int cnt = 0;
		boolean flag = false;
		report.createRow();
		report.endRow();
		String title = driver.getTitle();
//		WebElement title = driver.findElement(By.tagName("title"));
		if (title.equals("")) {
			flag = true;
			cnt++;
		}
		report.setErrorCount(cnt);
		report.createRow();
		report.headings("WCAG Guideline 2.4.2 (Level A)");
		report.headings("Total Number of Errors : "+cnt);
		report.endRow();
		if(!flag) {
			report.createRow();
			report.passStatements("Missing Page Title");
			report.passStatements("Page Title is present. Title is : "+title);
			report.endRow();
		} else {
			report.createRow();
			report.failStatements("Missing Page Title");
			report.failStatements("Page Title is not present");
			report.endRow();
		}
	}
	
	public void checkFocusOrder_wcag_2_4_3 () throws Exception {
		
		/*
		 * WCAG 2.4.3
		 */
		updateMessageBox("Executing WCAG Guideline 2.4.3...");
		driver.navigate().refresh();
		try {
			report.createRow();
			report.endRow();
			List <String> list1 = new ArrayList <String> ();
			List <String> typeofelement = new ArrayList <String> ();
			List <String> list2 = new ArrayList <String> ();
			Actions action = new Actions(driver.getWebDriver());
			//			action.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).keyUp(Keys.CONTROL).build().perform();
			action.sendKeys(Keys.TAB).build().perform();
			//sleep(1);
			WebElement first = driver.getWebDriver().switchTo().activeElement();
			if (first.getTagName().equals("input") || first.getTagName().equals("button") || first.getTagName().equals("select") || first.getTagName().equals("ul") || first.getTagName().equals("ol") || first.getTagName().equals("dl")||first.getTagName().equals("a")) 
			{
				if (first.getAttribute("name")!=null) {
					if (!first.getAttribute("name").isEmpty()) {
						list1.add(first.getTagName());
						typeofelement.add(getElementType(first));
						list2.add(first.getAttribute("name"));
					}
				} else if (first.getAttribute("role")!=null) {
					if (!first.getAttribute("role").isEmpty()) {
						list1.add(first.getTagName());
						typeofelement.add(getElementType(first));
						list2.add(first.getAttribute("role"));
					}
				} else if (first.getAttribute("type")!=null) {
					if (!first.getAttribute("type").isEmpty()) {
						list1.add(first.getTagName());
						typeofelement.add(getElementType(first));
						list2.add(first.getAttribute("type"));
					}
				} else if (first.getAttribute("alt")!=null) {
					if (!first.getAttribute("alt").isEmpty()) {
						list1.add(first.getTagName());
						typeofelement.add(getElementType(first));
						list2.add(first.getAttribute("alt"));
					}
				} else {
					list1.add(first.getTagName());
					typeofelement.add(getElementType(first));
					list2.add("No text associated with this control");
				}
			}
			action.sendKeys(Keys.TAB).build().perform();
			WebElement current = driver.getWebDriver().switchTo().activeElement();
			while (!current.equals(first)) 
			{
				if (current.getTagName().equals("input") || current.getTagName().equals("button") || current.getTagName().equals("select") || current.getTagName().equals("ul") || current.getTagName().equals("ol") || current.getTagName().equals("dl")||current.getTagName().equals("a")) 
				{
					if (current.getAttribute("innerText")!=null) {
						if (!current.getAttribute("innerText").isEmpty()) {
							list1.add(current.getTagName());
							typeofelement.add(getElementType(current));
							list2.add(current.getAttribute("innerText"));
						}
					} else if (current.getAttribute("name")!=null) {
						if (!current.getAttribute("name").isEmpty()) {
							list1.add(current.getTagName());
							typeofelement.add(getElementType(current));
							list2.add(current.getAttribute("name"));
							//							outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
						}
					} else if (current.getAttribute("role")!=null) {
						if (!current.getAttribute("role").isEmpty()) {
							list1.add(current.getTagName());
							typeofelement.add(getElementType(current));
							list2.add(current.getAttribute("role"));
							//							outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
						}
					} else if (current.getAttribute("type")!=null) {
						if (!current.getAttribute("type").isEmpty()) {
							list1.add(current.getTagName());
							typeofelement.add(getElementType(current));
							list2.add(current.getAttribute("type"));
							//							outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
						}
					} else if (current.getAttribute("alt")!=null) {
						if (!current.getAttribute("alt").isEmpty()) {
							list1.add(current.getTagName());
							typeofelement.add(getElementType(current));
							list2.add(current.getAttribute("alt"));
							//							outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
						}
					} else {
						list1.add(current.getTagName());
						typeofelement.add(getElementType(current));
						list2.add("No text for this control");
						//						outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
					}
				}

				action.sendKeys(Keys.ARROW_DOWN).build().perform();

				if(!driver.getWebDriver().switchTo().activeElement().equals(current))
				{
					if (driver.switchTo().activeElement().getTagName().equals("input") || driver.switchTo().activeElement().getTagName().equals("button") || driver.switchTo().activeElement().getTagName().equals("select") || driver.switchTo().activeElement().getTagName().equals("ul") || driver.switchTo().activeElement().getTagName().equals("ol") || driver.switchTo().activeElement().getTagName().equals("dl")||driver.switchTo().activeElement().getTagName().equals("a")) {
						if (driver.switchTo().activeElement().getAttribute("innerText")!=null) {
							if (!driver.switchTo().activeElement().getAttribute("innerText").isEmpty()) {
								list1.add(driver.switchTo().activeElement().getTagName());
								typeofelement.add(getElementType(driver.switchTo().activeElement()));
								list2.add(driver.switchTo().activeElement().getAttribute("innerText"));
							}
						} else if (driver.switchTo().activeElement().getAttribute("name")!=null) {
							if (!driver.switchTo().activeElement().getAttribute("name").isEmpty()) {
								list1.add(driver.switchTo().activeElement().getTagName());
								typeofelement.add(getElementType(driver.switchTo().activeElement()));
								list2.add(driver.switchTo().activeElement().getAttribute("name"));
								//									outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
							}
						} else if (driver.switchTo().activeElement().getAttribute("role")!=null) {
							if (!driver.switchTo().activeElement().getAttribute("role").isEmpty()) {
								list1.add(driver.switchTo().activeElement().getTagName());
								typeofelement.add(getElementType(driver.switchTo().activeElement()));
								list2.add(driver.switchTo().activeElement().getAttribute("role"));
								//									outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
							}
						} else if (driver.switchTo().activeElement().getAttribute("type")!=null) {
							if (!driver.switchTo().activeElement().getAttribute("type").isEmpty()) {
								list1.add(driver.switchTo().activeElement().getTagName());
								typeofelement.add(getElementType(driver.switchTo().activeElement()));
								list2.add(driver.switchTo().activeElement().getAttribute("type"));
								//									outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
							}
						} else if (driver.switchTo().activeElement().getAttribute("alt")!=null) {
							if (!driver.switchTo().activeElement().getAttribute("alt").isEmpty()) {
								list1.add(driver.switchTo().activeElement().getTagName());
								typeofelement.add(getElementType(driver.switchTo().activeElement()));
								list2.add(driver.switchTo().activeElement().getAttribute("alt"));
								//									outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
							}
						} else {
							list1.add(driver.switchTo().activeElement().getTagName());
							typeofelement.add(getElementType(driver.switchTo().activeElement()));
							list2.add("No text for this control");
							//								outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
						}
					}
					current = driver.getWebDriver().switchTo().activeElement();
					action.sendKeys(Keys.ARROW_DOWN).build().perform();
					while(!driver.switchTo().activeElement().equals(current))
					{
						if (driver.switchTo().activeElement().getTagName().equals("input") || driver.switchTo().activeElement().getTagName().equals("button") || driver.switchTo().activeElement().getTagName().equals("select") || driver.switchTo().activeElement().getTagName().equals("ul") || driver.switchTo().activeElement().getTagName().equals("ol") || driver.switchTo().activeElement().getTagName().equals("dl")||driver.switchTo().activeElement().getTagName().equals("a")) 
						{
							if (driver.switchTo().activeElement().getAttribute("innerText")!=null) {
								if (!driver.switchTo().activeElement().getAttribute("innerText").isEmpty()) {
									list1.add(driver.switchTo().activeElement().getTagName());
									typeofelement.add(getElementType(driver.switchTo().activeElement()));
									list2.add(driver.switchTo().activeElement().getAttribute("innerText"));
								}
							} else if (driver.switchTo().activeElement().getAttribute("name")!=null) {
								if (!driver.switchTo().activeElement().getAttribute("name").isEmpty()) {
									list1.add(driver.switchTo().activeElement().getTagName());
									typeofelement.add(getElementType(driver.switchTo().activeElement()));
									list2.add(driver.switchTo().activeElement().getAttribute("name"));
									//									outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
								}
							} else if (driver.switchTo().activeElement().getAttribute("role")!=null) {
								if (!driver.switchTo().activeElement().getAttribute("role").isEmpty()) {
									list1.add(driver.switchTo().activeElement().getTagName());
									typeofelement.add(getElementType(driver.switchTo().activeElement()));
									list2.add(driver.switchTo().activeElement().getAttribute("role"));
									//									outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
								}
							} else if (driver.switchTo().activeElement().getAttribute("type")!=null) {
								if (!driver.switchTo().activeElement().getAttribute("type").isEmpty()) {
									list1.add(driver.switchTo().activeElement().getTagName());
									typeofelement.add(getElementType(driver.switchTo().activeElement()));
									list2.add(driver.switchTo().activeElement().getAttribute("type"));
									//									outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
								}
							} else if (driver.switchTo().activeElement().getAttribute("alt")!=null) {
								if (!driver.switchTo().activeElement().getAttribute("alt").isEmpty()) {
									list1.add(driver.switchTo().activeElement().getTagName());
									typeofelement.add(getElementType(driver.switchTo().activeElement()));
									list2.add(driver.switchTo().activeElement().getAttribute("alt"));
									//									outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
								}
							} else {
								list1.add(driver.switchTo().activeElement().getTagName());
								typeofelement.add(getElementType(driver.switchTo().activeElement()));
								list2.add("No text for this control");
								//								outerHTML.add(getOuterHTML(driver.getWebDriver().switchTo().activeElement()));
							}
						}
						current = driver.getWebDriver().switchTo().activeElement();
						action.sendKeys(Keys.ARROW_DOWN).build().perform();

					}
				}

				current = driver.getWebDriver().switchTo().activeElement();
				action.sendKeys(Keys.TAB).build().perform();
			}
			

			if (list1.size()>0)
			{
				createExcelFileforfocusorder(Allocator.currentTC.get(counter) + "_FocusOrderforInputs_"+driver.getTitle(), list1, typeofelement, list2);
			}
			report.createRow();
			report.headings("WCAG Guideline 2.4.3 (Level A)");
			report.headings("Focus Order");
			report.endRow();

			report.createRow();
			report.infoStatements("Focus Order");
			String link = frameworkParameters.getReportPath() + "\\" +Allocator.currentTC.get(counter)+ "_FocusOrderforInputs_"+driver.getTitle()+".xlsx";
			if (list1.size()!=0)
			{
				report.infoStatements("<a href = \""+link+"\">Focus Order</a>");
			}
			else{
				report.infoStatements("No Input, Button, Link or Select Elements");
			}
			report.endRow();


		} catch (Exception e) {
			System.err.println("Exception Message : " +e.getMessage());
		}
	}

	public void checkLinkPurpose_wcag_2_4_4 () throws Exception {
		
		/*
		 * WCAG 2.4.4
		 */
		updateMessageBox("Executing WCAG Guideline 2.4.4...");
		try{
		report.createRow();
		report.endRow();
		List<WebElement> link = driver.getWebDriver().findElements(By.tagName("a"));
//		List<String> xpath = new ArrayList <String> ();
		List<String> outerHTML = new ArrayList <String> ();
		List<String> linktext  = new ArrayList <String> ();
		int cnt = 0;
		for (WebElement element : link) {
//			if (elementvisible(driver.getWebDriver(), element))
//			if(element.isDisplayed())
//			{
				if (!element.getAttribute("innerText").trim().equals("")) {

					linktext.add(element.getAttribute("innerText").trim());
					outerHTML.add(getOuterHTMLplaintext(element));
				} else {
					
					cnt++;
					linktext.add("No Text for the link");
					outerHTML.add(getOuterHTMLplaintext(element));
				} 
//			}
		}
		report.setErrorCount(cnt);
		report.createRow();
		report.headings("WCAG Guideline 2.4.4 (Level A)");
		report.headings("Total Number of Errors : " +cnt);
		report.endRow();

		createExcelFileforlinktext(Allocator.currentTC.get(counter)+ "_LinkText_"+driver.getTitle(), linktext, outerHTML);
		report.createRow();
		report.infoStatements("Text of Link");
		String linktofile = frameworkParameters.getReportPath() + "\\" +Allocator.currentTC.get(counter)+ "_LinkText"+driver.getTitle()+".xlsx";
		
		report.infoStatements("<a href = \""+linktofile+"\">Link Text</a>");
		report.endRow();
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	public void checkDocumentLanguage()
	{
		/*
		 * WCAG 3.1.1
		 * 
		 */
		updateMessageBox("Executing WCAG Guideline 3.1.1...");
		report.createRow();
		report.endRow();
		
		List<WebElement> listofelements = driver.getWebDriver().findElements(By.xpath("//*[self::a or self::label or self::img or self::p or self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6 or self::l1 or self::ul or self::select or self::option or self::span]"));
		List<String> text = new ArrayList<>();
		List<String> outerHTML = new ArrayList<>();
		List<String> language = new ArrayList<>();
		
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
		createExcelFileforDocumentLanguage(Allocator.currentTC.get(counter)+ "_DocumentLanguage_"+driver.getTitle(), text, language, outerHTML);
		report.createRow();
		report.headings("WCAG Guideline 3.1.1");
		report.headings("Ckeck the link below for errors");
		report.endRow();
		
		report.createRow();
		report.infoStatements("Document Language for Text");
		String link = frameworkParameters.getReportPath() + "\\" +Allocator.currentTC.get(counter)+ "_DocumentLanguage_"+driver.getTitle()+".xlsx";
		report.infoStatements("<a href = \""+link+"\">Document Language for Text in Page</a>");
		report.endRow();
		
		
	}
	

	
	public void checkOnFocus_wcag_3_2_1 () throws Exception {
		
		/*
		 * WCAG 3.2.1
		 */
		updateMessageBox("Executing WCAG Guideline 3.2.1...");
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
			report.headings("WCAG Guideline 3.2.1 (Level A)");
			report.headings("Total Number of Errors : " +cnt);
			report.endRow();
			if(!flag) {
				report.createRow();
				report.passStatements("On Focus Change in Context");
				report.passStatements("No elements on having focus initiates a change in the context");
				report.endRow();
			} else {
				for (int i=0; i<outerHTML.size(); i++) {
					report.createRow();
					report.failStatements("On Focus - Initiates a change in context");
					report.failStatements("<code class=\"input\">"+outerHTML.get(i)+"</code>");
					report.endRow();
				}
			}
		} catch (Exception e) {
			System.err.println("Exception Message : " +e.getMessage());
		}
	}
	
	public void htmlParsingError_wcag_4_1_1() {

		/*
		 * WCAG 4.1.1
		 */
		updateMessageBox("Executing WCAG Guideline 4.1.1...");
		report.createRow();
		report.endRow();
		int cnt = 0;
		report.setErrorCount(cnt);
		report.createRow();
		report.headings("WCAG Guideline 4.1.1 (Level A)");
		report.headings("Total Number of Errors : " +cnt);
		report.endRow();
		
		report.createRow();
		report.passStatements("HTML Parsing Error");
		report.passStatements("No parsing error related to the mark up language used");
		report.endRow();

	}

	public void checkNameRoleValue_wcag_4_1_2 () throws Exception {
		
		updateMessageBox("Executing WCAG Guideline 4.1.2...");
		try {
			boolean flag = false;
			report.createRow();
			report.endRow();
			List<WebElement> input = driver.getWebDriver().findElements(By.tagName("input"));
			List<String> xpath = new ArrayList <String> ();
			List<String> outerHTML = new ArrayList <String> ();
			int cnt = 0;
			for (WebElement element : input) {
				if(element.isDisplayed())
				{
				if (!element.getAttribute("name").equals("")) {
					if (element.getAttribute("name").isEmpty()) {
						xpath.add(getAbsoluteXPath(element));
						outerHTML.add(getOuterHTML(element));
						flag = true;
						cnt++;
					}
				} else if (!element.getAttribute("role").equals("")) {
					if (element.getAttribute("role").isEmpty()) {
						xpath.add(getAbsoluteXPath(element));
						outerHTML.add(getOuterHTML(element));
						flag = true;
						cnt++;
					}
				} else if (!element.getAttribute("value").equals("")) {
					if (element.getAttribute("value").isEmpty()) {
						xpath.add(getAbsoluteXPath(element));
						outerHTML.add(getOuterHTML(element));
						flag = true;
						cnt++;
					}
				} else {
					xpath.add(getAbsoluteXPath(element));
					outerHTML.add(getOuterHTML(element));
					flag = true;
					cnt++;
				}
				}
			}
			report.setErrorCount(cnt);
			report.createRow();
			report.headings("WCAG Guideline 4.1.2 (Level A)");
			report.headings("Total Number of Errors : " +cnt);
			report.endRow();
			if(!flag) {
				report.createRow();
				report.passStatements("Name, Role, Value");
				report.passStatements("No Input Element without Name or Role or Value");
				report.endRow();
			} else {
				for (int i=0; i<outerHTML.size(); i++) {
					report.createRow();
					report.failStatements("Name, Role, Value");
//					report.failStatements(xpath.get(i));
					report.failStatements("<code class=\"input\">"+outerHTML.get(i)+"</code>");
					report.endRow();
				}
			}
		} catch (Exception e) {
			System.err.println("Exception Message : " +e.getMessage());
		}
	}
	
		
	public void audioControl_wcag_1_4_2(){
		
		/*
		 * WCAG Guideline 1.4.2 - Audio control
		 */
		updateMessageBox("Executing guideline 1.4.2...");
		List<WebElement> audioelements = driver.getWebDriver().findElements(By.tagName("audio"));
		List<String> outerHTMLaudio = new ArrayList<String>();
		boolean flag = false;
		int cnt = 0;
		if(audioelements.size()>0)
		{
			for (WebElement audioWebElement : audioelements) {
				if ((boolean)((JavascriptExecutor)driver).executeScript("return arguments[0].paused;", audioWebElement))
				{
					try{
						((JavascriptExecutor)driver).executeScript("arguments[0].play();", audioWebElement);
						((JavascriptExecutor)driver).executeScript("arguments[0].volume = 0.5;", audioWebElement);

						flag = true;
					}
					catch (Exception e)
					{
						//e.printStackTrace();
						outerHTMLaudio.add(getOuterHTML(audioWebElement));
						flag = false;
						cnt++;
					}
				}
				else{

					try{
						((JavascriptExecutor)driver).executeScript("arguments[0].pause();", audioWebElement);
						((JavascriptExecutor)driver).executeScript("arguments[0].play();", audioWebElement);
						((JavascriptExecutor)driver).executeScript("arguments[0].volume = 0.5;", audioWebElement);

						flag = true;
					}
					catch (Exception e)
					{
						//e.printStackTrace();
						outerHTMLaudio.add(getOuterHTML(audioWebElement));
						flag = false;
						cnt++;
					}


				}

			}
			
			//reporting for the guideline
			report.setErrorCount(cnt);
			report.createRow();
			report.headings("WCAG Guideline 1.4.2 (Level A)");
			report.headings("Total Number of Errors : "+cnt);
			report.endRow();
			
			if (flag)
			{
			report.createRow();
			report.infoStatements("Audio Control - Play/Pause Audio");
			report.passStatements("Play/Pause Control present");
			report.endRow();
			}
			else{
				
				for (String string : outerHTMLaudio) {
				
					report.createRow();
					report.infoStatements("Audio Control - Play/Pause Control Absent");
					report.failStatements(string);
					report.endRow();
					
				}
				
			}
			
		}
		else{
			
			report.createRow();
			report.headings("WCAG Guideline 1.4.2 (Level A)");
			report.headings("Total Number of Error : 0");
			report.endRow();
			
			report.createRow();
			report.infoStatements("Audio Control - Play/Pause Audio");
			report.passStatements("No audio controls present");
			report.endRow();
			
			
		}
		
		
		
		
	}
	
	public void pausemovingobject_wcag_2_2_2(WebElement element)
	{
		/*
		 * WCAG Guideline 2.2.2 - Pause, Stop moving/blinking
		 * element
		 */
		
		updateMessageBox("Executing AODA Guideline 2.2.2...");
		boolean flag = false;
		int cnt = 0;
		try{
			element.click();
			sleep(3);
			if (element.isDisplayed()&&element.isEnabled())
			{
				flag = true;
			}
			else
			{
				flag = false;
				cnt++;
			}
		}
		
		catch (Exception e)
		{
			flag = false;
		}
		
		report.createRow();
		report.headings("WCAG Guideline 2.2.2 (Level A)");
		report.headings("Total Number of Errors :"+cnt);
		report.endRow();
		
		if (flag)
		{
			report.createRow();
			report.infoStatements("Pause, stop moving element");
			report.passStatements("Can able to pause, stop moving element");
			report.endRow();
		}
		else{
			report.createRow();
			report.infoStatements("Pause, stop moving element");
			report.failStatements(getOuterHTML(element));
			report.endRow();
			
		}
		
	}
	
	
	
	public void useofColor()
	{
		/*
		 * WCAG 1.4.1 - Use of Color
		 */
		updateMessageBox("Executing AODA Guideline 1.4.1...");
		
		List<WebElement> elements = driver.getWebDriver().findElements(By.xpath("//*[self::a or self::input[@type='text' or @type='email' or @type='password']]"));
		List<String> useofcolor = new ArrayList<>();
		List<String> textofcontrol = new ArrayList<>();
		List<String> color = new ArrayList<>();
		int cnt = 0;
		
		for (WebElement webElement : elements) {
//			if(elementvisible(driver.getWebDriver(), webElement))
			if(webElement.isDisplayed())
			{
				if (webElement.getTagName().equals("a"))
				{
					useofcolor.add("Different Color for Links");
					textofcontrol.add(webElement.getAttribute("innerText"));
					if(colordifference(webElement))
					{
						color.add("Color of link different from surrunding");
					}
					else{
						
						color.add("Color of link NOT different from surrunding");
						cnt++;
					}
				} else if(webElement.getTagName().equals("input"))
				{
					useofcolor.add("Required symbol for input field");
					textofcontrol.add(getLabelforinputelement(webElement));
					if(webElement.getAttribute("aria-required")!=null)
					{
						if(webElement.getAttribute("aria-required")=="true")
						{
							color.add("Input field mandatory");
						}
						else{
							
							color.add("Input field NOT mandatory");
						}
					}
					else{
						
						color.add("Input field NOT mandatory");
					}
				}
				
			}
		}
		
		//report header
		report.setErrorCount(cnt);
		report.createRow();
		report.headings("WCAG Guideline 1.4.1 (Level A)");
		report.headings("Total Number of Errors :"+cnt);
		report.endRow();
		
		createExcelFileforuseofcolor(Allocator.currentTC.get(counter)+"_UseofColor_"+driver.getTitle(), useofcolor, textofcontrol, color);
		
		report.createRow();
		report.infoStatements("Use of Color");
		String link = frameworkParameters.getReportPath() + "\\" +Allocator.currentTC.get(counter)+"_UseofColor_"+driver.getTitle()+".xlsx";
		report.infoStatements("<a href = \""+link+"\">Use of Color</a>");
		report.endRow();
		
		
	}
	
	
	/*
	 * WCAG 3.3.2 - Labels of input controls
	 */
	public void label_wcag_3_3_2()
	{
		/*
		 * WCAG 3.3.2 - Labels of input controls
		 */
		
		updateMessageBox("Executing AODA Guideline 3.3.2...");
		List<WebElement> textelements = driver.getWebDriver().findElements(By.xpath("//input"));
		List<WebElement> inputelements = new ArrayList<WebElement>();
		
		for (WebElement webElement : textelements) {
			
//			if(webElement.isDisplayed()&&webElement.isEnabled())
//			{
				if (webElement.getAttribute("type").equals("text")||webElement.getAttribute("type").equals("email")||webElement.getAttribute("type").equals("password"))
				{
					inputelements.add(webElement);
				}
//			}
		}
		
		
		int cnt = 0;
		List<String> outerHTML = new ArrayList<String>();
		List<String> labels = new ArrayList<String>();
		List<String> attributes = new ArrayList<String>();
		
		for (WebElement element : inputelements) {

			String xpath = getAbsoluteXPath(element);
			String labeltext = "";
			try{
			WebElement labelelement = driver.findElement(By.xpath("("+xpath+"/preceding::label)[last()]"));
			labeltext = labelelement.getAttribute("innerText").trim();
			}
			catch(NoSuchElementException e)
			{
				labeltext = "No label for the corresponding input element";
			}
			try{

				if (element.getAttribute("name")!=null)
				{
					if(element.getAttribute("name").toLowerCase().contains(labeltext.toLowerCase()))
					{
						labels.add(labeltext);
						attributes.add("Name");
						outerHTML.add(getOuterHTMLplaintext(element));
					}
					else if(element.getAttribute("aria-label")!=null)
					{
						if(element.getAttribute("aria-label").toLowerCase().contains(labeltext.toLowerCase()))
						{
							labels.add(labeltext);
							attributes.add("Aria-Label");
							outerHTML.add(getOuterHTMLplaintext(element));
						}
					}
					else if (element.getAttribute("class")!=null)
					{
						if (element.getAttribute("class").toLowerCase().contains(labeltext.toLowerCase()))
						{
							labels.add(labeltext);
							attributes.add("Class Name");
							outerHTML.add(getOuterHTMLplaintext(element));
						}
						else{
							
							cnt++;
							labels.add(labeltext);
							attributes.add("NONE");
							outerHTML.add(getOuterHTMLplaintext(element));

						}

					}
				
				}

			}

			catch (NullPointerException e)
			{
				continue;
				
			}
		}
		try{
			
			createExcelFileouterHTML(Allocator.currentTC.get(counter) + "_Labels_of_Input_Elements_"+driver.getTitle(), labels, attributes, outerHTML);
			report.setErrorCount(cnt);
			report.createRow();
			report.headings("WCAG Guideline 3.3.2 (Level A)");
			report.headings("Total Errors :"+cnt);
			report.endRow();
			report.createRow();
			report.infoStatements("Labels of Input Elements");
			String link = frameworkParameters.getReportPath() + "\\" +Allocator.currentTC.get(counter)+ "_Labels_of_Input_Elements_"+driver.getTitle()+".xlsx";
			if (cnt==0)
			{
				report.infoStatements("<a href = \""+link+"\">Labels of Input Elements</a>");
			}
			else
			{
				report.failStatements("<a href = \""+link+"\">Labels of Input Elements</a>");
			}
			report.endRow();
			
			
		}
		catch (Exception e)
		{
			System.err.println("Exception : "+e.getMessage());
			e.printStackTrace(System.err);
			
		}

	}
	
	public void onInput_wcag_3_2_2(){
		
		/*
		 * WCAG 3.2.2 - On input focus
		 */
		
		updateMessageBox("Executing AODA Guideline 3.2.2...");
		
		List<WebElement> textelements = driver.getWebDriver().findElements(By.xpath("//input"));
		List<WebElement> inputelements = new ArrayList<WebElement>();
		List<WebElement> alllinks = driver.getWebDriver().findElements(By.xpath("//a"));
		List<WebElement> linksinnewwindow = new ArrayList<WebElement>();
		List<String> inputouterHTML = new ArrayList<String>();
		List<String> linkouterHTML = new ArrayList<String>();
		
		
		for (WebElement webElement : textelements) {
			
			if(webElement.isDisplayed()&&webElement.isEnabled())
			{
				if (webElement.getAttribute("type").equals("text")||webElement.getAttribute("type").equals("email")||webElement.getAttribute("type").equals("password"))
				{
					inputelements.add(webElement);
				}
			}
		}
		
		for (WebElement webElement : alllinks) {
			
			if(webElement.isDisplayed()&&webElement.isEnabled())
			{
				if(webElement.getAttribute("target")!=null)
				{
					if (webElement.getAttribute("target").equals("_blank"))
					{
						linksinnewwindow.add(webElement);
					}
				}
			}
		}
		
		boolean flag = false;
		int cnt = 0;
		for (WebElement element : inputelements) {
			
			if (newwindowopen(element))
			{
				cnt++;
				flag = false;
				inputouterHTML.add(getOuterHTML(element));
			}
			else{
				
				flag = true;
			}

			if(isAlertPresent())
			{
				cnt++;
				flag = false;
				inputouterHTML.add(getOuterHTML(element));

			}
			else{
				
				flag = true;
			}
		}
			
		for (WebElement webElement : linksinnewwindow) {
			
			if (!webElement.getAttribute("innerText").toLowerCase().contains("new window"))
			{
				flag = false;
				cnt++;
				linkouterHTML.add(getOuterHTML(webElement));
			}
			
		}
		
		/*
		 * Create Header Row
		 */
		report.setErrorCount(cnt);
		report.createRow();
		report.headings("WCAG Guideline 3.2.2 (Level A)");
		report.headings("Total Errors :"+cnt);
		report.endRow();
		
		if(cnt>0)
		{
			for (String string : linkouterHTML) {

				report.createRow();
				report.infoStatements("Link opens in new window missing ");
				report.failStatements("<code class=\"input\">"+string+"</code>");
				report.endRow();
			}

			for (String string : inputouterHTML) {

				report.createRow();
				report.infoStatements("Input causes a change in context ");
				report.failStatements("<code class=\"input\">"+string+"</code>");
				report.endRow();
			}
		}
		else{

			report.createRow();
			report.infoStatements("On Input");
			report.passStatements("No inputs or links cause a change in context unless user has been advised of the behabviour before");
			report.endRow();
		}

	}
	
	public void erroridentification_wcag_3_3_1(List<String> xpathlocators)
	{
		updateMessageBox("Executing AODA Guideline 3.3.1...");
		
		boolean flag = false;
		int cnt = 0;
		List<String> outerHTML = new ArrayList<>();
		List<String> message = new ArrayList<>();
		List<String> outerHTMLplaintext = new ArrayList<>();
		
		try
		{
		
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
					outerHTML.add(getOuterHTML(driver.getWebDriver().findElement(By.xpath(string))));
					message.add(driver.getWebDriver().findElement(By.xpath(string)).getAttribute("innerText"));
					outerHTMLplaintext.add(getOuterHTMLplaintext(driver.getWebDriver().findElement(By.xpath(string))));
				}
			}
			else{
				flag = true;
				cnt++;
				outerHTML.add(getOuterHTML(driver.getWebDriver().findElement(By.xpath(string))));
				message.add(driver.getWebDriver().findElement(By.xpath(string)).getAttribute("innerText"));
				outerHTMLplaintext.add(getOuterHTMLplaintext(driver.getWebDriver().findElement(By.xpath(string))));
			}
		
		}
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		report.setErrorCount(cnt);
		report.createRow();
		report.headings("WCAG Guideline 3.3.1 (Level A)");
		report.headings("Total Errors :"+cnt);
		report.endRow();
		
		createExcelFileforError(Allocator.currentTC.get(counter)+"_ErrorIdentification_"+driver.getTitle(), message, outerHTMLplaintext);
		report.createRow();
		report.infoStatements("Error Suggestion - list of error messages");
		String link = frameworkParameters.getReportPath() + "\\"+Allocator.currentTC.get(counter)+"_ErrorIdentification_"+driver.getTitle()+".xlsx";
		report.infoStatements("<a href = \""+link+"\">Error Messages</a>");
		report.endRow();
		
		if(cnt>0)
		{
			for (int i = 0; i < outerHTML.size(); i++) {
				report.createRow();
				report.infoStatements("Error Identification - Error Message not identified");
				report.failStatements("<code class=\"input\">"+outerHTML.get(i)+"</code>");
				report.endRow();
			}
			
		}
		else{
			
			report.createRow();
			report.infoStatements("Error Identification");
			report.passStatements("All the required error messages are identified properly");
			report.endRow();
			
		}
		
	}

	
	public void launchMessageBox()
	{

		frame = new JFrame();

		frame.setTitle("AODA Test Execution");
		
//		textarea = new JTextArea("Starting WCAG Level A guidelines test execution...ha ha ha ha ha ha ha");
		label = new JLabel("Starting WCAG Level A guidelines test execution...");
		
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setVerticalAlignment(SwingConstants.CENTER);
//		textarea.setLineWrap(true);

		label.setBounds(10, 20, 300, 30);
		label.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
		frame.add(label);
		frame.setSize(new Dimension(350,100));
//		frame.setSize(400, 100);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		sleep(2);
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
	
	
	
	public List<WebElement> gethighlightlist()
	{
		
		return elementstobehighlighted;
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
		
//		luminance = (299*Integer.parseInt(rgbcolors[0].trim())+587*Integer.parseInt(rgbcolors[1].trim())+114*Integer.parseInt(rgbcolors[2].trim()))/1000;
		
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

	/*
	 * To check if any alert is present
	 */
	
	private boolean isAlertPresent()
	{
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 2);
		try{
			wait.until(ExpectedConditions.alertIsPresent());
			driver.getWebDriver().switchTo().alert().accept();
			return true;
		}
		catch(Exception e)
		{
			
			return false;
		}
	}
	
	private boolean newwindowopen(WebElement element)
	{
		int existingwindowhandles = driver.getWebDriver().getWindowHandles().size();
		boolean flag = false;
		try{
		element.click();
		element.sendKeys("1");
		int currentwindowhandles = driver.getWebDriver().getWindowHandles().size();
		
		if (existingwindowhandles==currentwindowhandles)
		{
			
			flag = false;
		}
		else{
			
			flag = true;
		}
		}
		catch (Exception e){
			
			e.printStackTrace(System.err);
		}
		finally {
			element.clear();
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
			while(parent.getCssValue("background-color").equalsIgnoreCase("rgba(0, 0, 0, 0)"))
			{
				xpath = getAbsoluteXPath(parent);
				parent = parent.findElement(By.xpath(xpath+"/.."));
			}
			backgroundcolor = parent.getCssValue("background-color");
			
			
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
	
	private String getcolorofparent(WebElement element)
	{
		String xpath = getAbsoluteXPath(element);
		WebElement parent = element.findElement(By.xpath(xpath+"/.."));
		while(parent.getCssValue("color").equalsIgnoreCase("rgba(0, 0, 0, 0)"))
		{
			xpath = getAbsoluteXPath(parent);
			parent = parent.findElement(By.xpath(xpath+"/.."));
		}
		String parentcolor = parent.getCssValue("color");
		
		return parentcolor;
		
		
	}
	
	private boolean colordifference(WebElement element)
	{
		String parentcolor = getcolorofparent(element);
		String color = element.getCssValue("color");
		
		if (color==parentcolor)
		{
			return false;
		}
		else{
			return true;
			
		}
		
	}
	
//	private String getLabelforinput(WebElement inputelement)
//	{
//		String xpath = getAbsoluteXPath(inputelement);
//		WebElement labelelement = driver.findElement(By.xpath("("+xpath+"/preceding::label)[last()]"));
//		String labeltext = labelelement.getAttribute("innerText").trim();
//		return labeltext;
//	}
	
	private String getElementType(WebElement element)
	{
		String elementtype = element.getTagName();
		String type = "";
		switch (elementtype) {
		case "a":
			type = "Link";
			break;

		case "select":
			type = "Dropdown";
			break;

		case "ul":
			type = "Dropdown";
			break;
			
		case "li":
			type = "List Item";
			break;
			
		case "option":
			type = "List Item";
			break;
		
		case "button":
			type = "Button";
			break;
			
			
		case "input":
			
			switch (element.getAttribute("type")) {
			case "text":
				type = "Input Text Box";
				break;
			case "email":
				type = "Email Text Box";
				break;
			case "password":
				type = "Password Text Box";
				break;
			case "radio":
				type = " Input Radio button";
				break;
			case "checkbox":
				type = "Input Checkbox";
				break;
			case "combo":
				type = "Input Combobox";
				break;
			case "button":
				type = "Button";
				break;
			case "submit":
				type = "Button";
				break;
			default:
				type = "User Interactive/Input field";
				break;
			}
			
			break;
		case "h1":
		case "h2":
		case "h3":
		case "h4":
		case "h5":
		case "h6":
			type = "Header Item";
			break;
		
		case "label":
		case "span" :
			type = "Label";
			break;
		
		case "p":
			type = "Paragraph";
			break;
		
		case "img":
			type = "Image";
			break;
			
		default:
			type = "Type Unknown";
			break;
		}
		return type;
		
	}
	
	private String getLabelforinputelement(WebElement element)
	{
		String xpath = getAbsoluteXPath(element);
		String labeltext = "";
		try{
			WebElement labelelement = driver.findElement(By.xpath("("+xpath+"/preceding::label)[last()]"));
			labeltext = labelelement.getAttribute("innerText").trim();
		}
		catch(NoSuchElementException e)
		{
			labeltext = "No label for the corresponding input element";
		}
		return labeltext;
	}
	
	private int getvisibleelementcount()
	{
		List<WebElement>elements =  driver.getWebDriver().findElements(By.xpath("//*"));
		int cnt = 0;
		for (WebElement webElement : elements) {
			if (webElement.isDisplayed())
			{
				cnt++;
			}
		}
		return cnt;
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
	
	private String getTextofElement(WebElement element)
	{
		String textofelement = element.getAttribute("innerText");
		for (WebElement childelement : element.findElements(By.xpath(".//*"))) {
			
			try{
			String  replacetext = childelement.getAttribute("innerText");
			replacetext = Pattern.quote(replacetext);
			textofelement = textofelement.replaceFirst(replacetext, "").trim();
			}
			catch (Exception e)
			{
				continue;
			}
		}
		
		return textofelement;
		
	}
	
	private boolean checkifparentislink(WebElement element)
	{
		boolean flag = false;
		
		String xpathofelement = getAbsoluteXPath(element);
		String parentlinkxpath = xpathofelement+"/parent::a";
		
		try
		{
			if (driver.findElement(By.xpath(parentlinkxpath)).isDisplayed())
			{
				flag = true;
			}
		}
		catch (NoSuchElementException e)
		{
			flag = false;
		}
		
		
		return flag;
	}


}
