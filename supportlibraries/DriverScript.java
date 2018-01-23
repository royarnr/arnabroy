package supportlibraries;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.server.browserlaunchers.Sleeper;

import com.cognizant.framework.CraftDataTable;
import com.cognizant.framework.DetailedReport;
import com.cognizant.framework.ExcelDataAccess;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.OnError;
import com.cognizant.framework.Settings;
import com.cognizant.framework.SummaryReport;
import com.cognizant.framework.selenium.CraftDriver;
import com.cognizant.framework.selenium.PerfectoDashboard;
import com.cognizant.framework.selenium.PerfectoDriverFactory;
import com.cognizant.framework.selenium.SeleniumTestParameters;
import com.cognizant.framework.selenium.WebDriverFactory;

import allocator.Allocator;

/**
 * Driver script class which encapsulates the core logic of the framework
 * 
 * @author Cognizant
 */
public class DriverScript {
	private List<String> businessFlowData;
	private int currentIteration, currentSubIteration;

	private CraftDataTable dataTable;
	private DetailedReport report;

	private CraftDriver driver;

	private ScriptHelper scriptHelper;

	private Properties properties;
	private final FrameworkParameters frameworkParameters = FrameworkParameters
			.getInstance();

	private final SeleniumTestParameters testParameters;

	private CRAFTLiteTestCase testCase;
	private PerfectoDashboard perfectoDashboard = PerfectoDashboard.getInstance();
	private String status = null;

	/**
	 * DriverScript constructor
	 * 
	 * @param testParameters
	 *            A {@link SeleniumTestParameters} object
	 */
	public DriverScript(SeleniumTestParameters testParameters) {
		this.testParameters = testParameters;
	}


	/**
	 * Function to execute the given test case
	 */
	public void driveTestExecution() {
		startUp();
		
		if (testParameters.getExecutionMode().name().equals("PERFECTO"))
		{
			if (perfectoDashboard.getDashboard()==null)
			{
				perfectoDashboard.launchDashboard();
			}
		}
		initializetestreport();
		
		initializeWebDriver();
		initializeDatatable();
		executeCraftOrCraftLite();
		wrapup();
		quitWebDriver();
		
		/*
		 * Code to upload test results to HP ALM
		 */
		if (properties.getProperty("ALMUploadAutomatic").equalsIgnoreCase("On")){

			sleep(3);
			updatetestresultstatusinALM();
		}
		/*
		 * End of code add for ALM Upload
		 */
	}

	private void startUp() {

		properties = Settings.getInstance();
	}
	
	private void initializetestreport(){
		
		report = new DetailedReport();
	}
	
	private void wrapup(){
		
		
		report.create_HTML(testParameters.getCurrentTestcase());
		
		SummaryReport.createRow();
		SummaryReport.testcaseLink(frameworkParameters.getReportPath()+"\\"+testParameters.getCurrentTestcase()+".html", testParameters.getCurrentTestcase());
		SummaryReport.infoStatements(testParameters.getCurrentTestDescription());
		if (report.getErrorcount()>0)
		{
			SummaryReport.failStatements("FAILED");
			status = "Failed";
		}
		else{
			SummaryReport.passStatements("PASSED");
			status = "Passed";
		}
		
		sleep(2);
		
	}


	private void quitWebDriver() {
		//driver.quit();
		driver.close();
		
		if (perfectoDashboard.getDashboard()!=null)
		{
			perfectoDashboard.closeDashboard();
		}
	}
	
	

	private void executeCraftOrCraftLite() {
		if (properties.getProperty("Approach")
				.equalsIgnoreCase("KeywordDriven")) {
			initializeTestScript();
			executeCRAFTTestIterations();
		} else {
			initializeTestCase();

			try {
				testCase.setUp();
				executeCRAFTLiteTestIterations();
			} catch (FrameworkException fx) {
				exceptionHandler(fx, fx.getErrorName());
			} catch (Exception ex) {
				exceptionHandler(ex, "Error");
			} finally {
				testCase.tearDown(); // tearDown will ALWAYS be called
			}
		}

	}

	private void initializeWebDriver() {

		switch (testParameters.getExecutionMode()) {
		case LOCAL:
			WebDriver webDriver = WebDriverFactory.getWebDriver(testParameters.getBrowser());
			driver = new CraftDriver(webDriver);
			driver.setTestParameters(testParameters);
			WaitPageLoad();

			implicitWaitForDriver();
			break;
			
		case PERFECTO:

				WebDriver appiumPerfectoDriver = PerfectoDriverFactory
						.getPerfectoAppiumDriver(
								testParameters.getMobileExecutionPlatform(),
								testParameters.getDeviceName(),
								properties.getProperty("PerfectoHost"));
				driver = new CraftDriver(appiumPerfectoDriver);
				driver.setTestParameters(testParameters);


			break;

		default:
			break;
		}
		
		

	}

	private void implicitWaitForDriver() {
		long objectSyncTimeout = Long.parseLong(properties.get(
				"ObjectSyncTimeout").toString());
		driver.manage().timeouts()
				.implicitlyWait(objectSyncTimeout, TimeUnit.SECONDS);
	}

	private void WaitPageLoad() {
		long pageLoadTimeout = Long.parseLong(properties.get("PageLoadTimeout")
				.toString());
		driver.manage().timeouts()
				.pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	private synchronized void initializeDatatable() {
		String datatablePath = frameworkParameters.getRelativePath()
				+ "\\" + "Datatables";

		String runTimeDatatablePath;
		runTimeDatatablePath = datatablePath;

		dataTable = new CraftDataTable(runTimeDatatablePath,
				testParameters.getCurrentScenario());
		dataTable.setDataReferenceIdentifier(properties
				.getProperty("DataReferenceIdentifier"));
		// CRAFTLite Change
		if (properties.getProperty("Approach")
				.equalsIgnoreCase("ModularDriven")) {
			// Initialize the datatable row in case test data is required during
			// the setUp()
			dataTable.setCurrentRow(testParameters.getCurrentTestcase(),
					currentIteration, 0);
		}
	}

	private void initializeTestScript() {
		scriptHelper = new ScriptHelper(dataTable, driver, report);
		initializeBusinessFlow();
	}

	private void initializeBusinessFlow() {
		ExcelDataAccess businessFlowAccess = new ExcelDataAccess(
				frameworkParameters.getRelativePath() + "\\"
						+ "Datatables", testParameters.getCurrentScenario());
		businessFlowAccess.setDatasheetName("Business_Flow");

		int rowNum = businessFlowAccess.getRowNum(
				testParameters.getCurrentTestcase(), 0);
		if (rowNum == -1) {
			throw new FrameworkException("The test case \""
					+ testParameters.getCurrentTestcase()
					+ "\" is not found in the Business Flow sheet!");
		}

		String dataValue;
		businessFlowData = new ArrayList<String>();
		int currentColumnNum = 1;
		while (true) {
			dataValue = businessFlowAccess.getValue(rowNum, currentColumnNum);
			if ("".equals(dataValue)) {
				break;
			}
			businessFlowData.add(dataValue);
			currentColumnNum++;
		}

		if (businessFlowData.isEmpty()) {
			throw new FrameworkException(
					"No business flow found against the test case \""
							+ testParameters.getCurrentTestcase() + "\"");
		}
	}

	private void executeCRAFTTestIterations() {
		while (currentIteration < 1) {
			// Evaluate each test iteration for any errors
			try {
				executeTestcase(businessFlowData);
			} catch (FrameworkException fx) {
				exceptionHandler(fx, fx.getErrorName());
			} catch (InvocationTargetException ix) {
				exceptionHandler((Exception) ix.getCause(), "Error");
			} catch (Exception ex) {
				exceptionHandler(ex, "Error");
			}

			currentIteration++;
		}
	}

	private void executeCRAFTLiteTestIterations() {
		while (currentIteration <= 1) {
			// Evaluate each test iteration for any errors
			try {
				testCase.executeTest();
			} catch (FrameworkException fx) {
				exceptionHandler(fx, fx.getErrorName());
			} catch (Exception ex) {
				exceptionHandler(ex, "Error");
			}

			currentIteration++;
			dataTable.setCurrentRow(testParameters.getCurrentTestcase(),
					currentIteration, 0);
		}
	}

	private void initializeTestCase() {
		scriptHelper = new ScriptHelper(dataTable, driver, report);
		testCase = getTestCaseInstance();
		testCase.initialize(scriptHelper);
	}

	private CRAFTLiteTestCase getTestCaseInstance() {
		Class<?> testScriptClass;
		try {
			testScriptClass = Class.forName("testscripts."
					+ testParameters.getCurrentScenario() + "."
					+ testParameters.getCurrentTestcase());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException(
					"The specified test case is not found!");
		}

		try {
			return (CRAFTLiteTestCase) testScriptClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FrameworkException(
					"Error while instantiating the specified test script");
		}
	}

	private void executeTestcase(List<String> businessFlowData)
			throws IllegalAccessException, InvocationTargetException,
			ClassNotFoundException, InstantiationException {
		Map<String, Integer> keywordDirectory = new HashMap<String, Integer>();

		for (int currentKeywordNum = 0; currentKeywordNum < businessFlowData
				.size(); currentKeywordNum++) {
			String[] currentFlowData = businessFlowData.get(currentKeywordNum)
					.split(",");
			String currentKeyword = currentFlowData[0];

			int nKeywordIterations;
			if (currentFlowData.length > 1) {
				nKeywordIterations = Integer.parseInt(currentFlowData[1]);
			} else {
				nKeywordIterations = 1;
			}

			for (int currentKeywordIteration = 0; currentKeywordIteration < nKeywordIterations; currentKeywordIteration++) {
				if (keywordDirectory.containsKey(currentKeyword)) {
					keywordDirectory.put(currentKeyword,
							keywordDirectory.get(currentKeyword) + 1);
				} else {
					keywordDirectory.put(currentKeyword, 1);
				}
				currentSubIteration = keywordDirectory.get(currentKeyword);

				dataTable.setCurrentRow(testParameters.getCurrentTestcase(),
						currentIteration, currentSubIteration);

				invokeBusinessComponent(currentKeyword);
			}
		}
	}

	private void invokeBusinessComponent(String currentKeyword)
			throws IllegalAccessException, InvocationTargetException,
			ClassNotFoundException, InstantiationException {
		Boolean isMethodFound = false;
		final String CLASS_FILE_EXTENSION = ".class";
		File[] packageDirectories = {
				new File(frameworkParameters.getRelativePath()
						+ "\\" + "businesscomponents"),
				new File(frameworkParameters.getRelativePath()
						+ "\\" + "componentgroups") };

		for (File packageDirectory : packageDirectories) {
			File[] packageFiles = packageDirectory.listFiles();
			String packageName = packageDirectory.getName();

			for (int i = 0; i < packageFiles.length; i++) {
				File packageFile = packageFiles[i];
				String fileName = packageFile.getName();

				// We only want the .class files
				if (fileName.endsWith(CLASS_FILE_EXTENSION)) {
					// Remove the .class extension to get the class name
					String className = fileName.substring(0, fileName.length()
							- CLASS_FILE_EXTENSION.length());

					Class<?> reusableComponents = Class.forName(packageName
							+ "." + className);
					Method executeComponent;

					try {
						// Convert the first letter of the method to lowercase
						// (in line with java naming conventions)
						currentKeyword = currentKeyword.substring(0, 1)
								.toLowerCase() + currentKeyword.substring(1);
						executeComponent = reusableComponents.getMethod(
								currentKeyword, (Class<?>[]) null);
					} catch (NoSuchMethodException ex) {
						// If the method is not found in this class, search the
						// next class
						continue;
					}

					isMethodFound = true;

					Constructor<?> ctor = reusableComponents
							.getDeclaredConstructors()[0];
					Object businessComponent = ctor.newInstance(scriptHelper);

					executeComponent.invoke(businessComponent, (Object[]) null);

					break;
				}
			}
		}

		if (!isMethodFound) {
			throw new FrameworkException("Keyword " + currentKeyword
					+ " not found within any class "
					+ "inside the businesscomponents package");
		}
	}

	private void exceptionHandler(Exception ex, String exceptionName) {
		// Error reporting
		String exceptionDescription = ex.getMessage();
		if (exceptionDescription == null) {
			exceptionDescription = ex.toString();
		}

		// Print stack trace for detailed debug information
		StringWriter stringWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(stringWriter));

		// Error response
		if (frameworkParameters.getStopExecution()) {
			currentIteration = 1;
		} else {
			OnError onError = OnError
					.valueOf(properties.getProperty("OnError"));
			switch (onError) {
			// Stop option is not relevant when run from QC
			case NEXT_ITERATION:
				break;

			case NEXT_TESTCASE:
				currentIteration = 1;
				break;

			case STOP:
				frameworkParameters.setStopExecution(true);
				currentIteration = 1;
				break;

			default:
				throw new FrameworkException("Unhandled OnError option!");
			}
		}
	}
	
	/*
	 * Methods added for the ALM upload piece
	 */

	//Method to set the temp folder
	private String addtempfolderforExcel(String foldername)
	{
		String folderpath = System.getenv("TEMP")+File.separator+foldername;
		File tempfolder = new File(folderpath);
		if (tempfolder.exists())
		{
			try {
				FileUtils.deleteDirectory(tempfolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				System.err.println("Error in deleting existing temp folder");
				e.printStackTrace(System.err);
			}
		}
		tempfolder.mkdir();
		return tempfolder.getAbsolutePath();
	}
	
	
	private String getExcelPath(String testcasename)
	{
		List<File> excelfiles = new ArrayList<File>();
		FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		File exceldirectorypath = new File(frameworkParameters.getReportPath());
		
		try{
			
		if (exceldirectorypath.exists())
		{
			if (exceldirectorypath.listFiles().length>0){
				
				for (File file : exceldirectorypath.listFiles()) {
					
					if(file.getName().contains(testcasename)&&file.getName().endsWith(".xlsx")){
						excelfiles.add(file);
					}
					
				}
			}
		}
		}
		catch (Exception e)
		{
			System.err.println("Exception : "+e.getMessage());
			e.printStackTrace(System.err);
		}
		String tempfolderforexcel = addtempfolderforExcel(testcasename);
		for (int i=0;i<excelfiles.size();i++) {
			try {
				FileUtils.copyFile(excelfiles.get(i), new File(tempfolderforexcel+File.separator+FilenameUtils.removeExtension(excelfiles.get(i).getName())+".xlsx"));
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("Exception : "+e.getMessage());
				e.printStackTrace(System.err);
			}
			
		}
		
		if (excelfiles.size()>0)
		{
		return tempfolderforexcel;
		}
		else{
			return "";
		}
	}
	
	private void updatetestresultstatusinALM()
	{
//		String currenttestcase = testParameters.getCurrentScenario()
//				+"_"+testParameters.getCurrentTestcase()
//				+"_"+testParameters.getCurrentTestInstance();
		FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		String parameterstopassforALMUpload = properties.getProperty("ALMServer")
				+" "
				+properties.getProperty("ALMUsername")
				+" "
				+properties.getProperty("ALMPassword")
				+" "
				+"\""
				+properties.getProperty("ALMDomain")
				+"\""
				+" "
				+"\""
				+properties.getProperty("ALMProject")
				+"\""
				+" "
				+"\""
				+properties.getProperty("ALMTestSetFolderPath")
				+"\""
				+" "
				+"\""
				+properties.getProperty("ALMTestSetName")
				+"\""
				+" "
				+"\""
				+testParameters.getCurrentTestcase()
				+"\""
				+" "
				+status
				+" "
				+"\""
				+frameworkParameters.getReportPath()+File.separator+testParameters.getCurrentTestcase()+".html"
				+"\""
				+" "
				+"\""
				+getExcelPath(testParameters.getCurrentTestcase())
				+"\"";
		String relativePath = new File(System.getProperty("user.dir"))
		.getAbsolutePath();
		if (relativePath.contains("supportlibraries")) {
			relativePath = new File(System.getProperty("user.dir")).getParent();
			}
		String pathofvbscript = relativePath+File.separator+"supportlibraries"
			+File.separator+"ALMConnUpload.vbs";
		
		try {
			
			Runtime.getRuntime().exec("C:\\Windows\\SysWOW64\\cscript "+pathofvbscript+" "+parameterstopassforALMUpload);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			System.err.println("Vb script trigger failed or Temp directory delete has failed");
			e.printStackTrace(System.err);
		}
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
}