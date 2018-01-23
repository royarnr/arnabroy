	package allocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cognizant.framework.selenium.*;
import com.cognizant.framework.ExcelDataAccessforxlsm;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Settings;
import com.cognizant.framework.SummaryReport;

/**
 * Class to manage the batch execution of test scripts within the framework
 * 
 * @author Cognizant
 */
public class Allocator {
	private FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
	private Properties properties;
	public static List <String> URL = new ArrayList <String> ();
	public static List <String> currentTC = new ArrayList <String> ();

	/**
	 * The entry point of the test batch execution <br>
	 * Exits with a value of 0 if the test passes and 1 if the test fails
	 * 
	 * @param args
	 *            Command line arguments to the Allocator (Not applicable)
	 */
	public static void main(String[] args) {
		
		Allocator allocator = new Allocator();
		allocator.driveBatchExecution();
	}

	private void driveBatchExecution() {
		properties = Settings.getInstance();
		
		String runConfiguration;
		if (System.getProperty("RunConfiguration") != null) {
			runConfiguration = System.getProperty("RunConfiguration");
		} else {
			runConfiguration = properties.getProperty("RunConfiguration");
		}
		frameworkParameters.setRunConfiguration(runConfiguration);
		frameworkParameters.setReportPath();
		int nThreads = Integer.parseInt(properties
				.getProperty("NumberOfThreads"));

		int testBatchStatus = executeTestBatch(nThreads);
		launchReport();
		System.exit(testBatchStatus);
	}
	
	private void launchReport() {

		SummaryReport.createRowSummary();
		SummaryReport.addSummarydata(frameworkParameters.getStartTime());
		SummaryReport.addSummarydata(frameworkParameters.getExecutionEndTime());
		SummaryReport.addSummarydata(Math.floorDiv(frameworkParameters.getelapsedtimeinseconds(), 60)+ " minutes & " +frameworkParameters.getelapsedtimeinseconds()%60 + " seconds");
		SummaryReport.endRowSummary();
		SummaryReport.createReport();
		
	}

	private int executeTestBatch(int nThreads) {
		List<SeleniumTestParameters> testInstancesToRun = getRunInfo(frameworkParameters
				.getRunConfiguration());
		ExecutorService parallelExecutor = Executors
				.newFixedThreadPool(nThreads);
		ParallelRunner testRunner = null;

		for (int currentTestInstance = 0; currentTestInstance < testInstancesToRun
				.size(); currentTestInstance++) {
			testRunner = new ParallelRunner(
					testInstancesToRun.get(currentTestInstance));
			parallelExecutor.execute(testRunner);

			if (frameworkParameters.getStopExecution()) {
				break;
			}
		}

		parallelExecutor.shutdown();
		while (!parallelExecutor.isTerminated()) {
			/*try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}

		if (testRunner == null) {
			return 0; // All tests flagged as "No" in the Run Manager
		} else {
			return testRunner.getTestBatchStatus();
		}
	}

	private List<SeleniumTestParameters> getRunInfo(String sheetName) {
		ExcelDataAccessforxlsm runManagerAccess = new ExcelDataAccessforxlsm(
				frameworkParameters.getRelativePath(), "Run Manager");
		runManagerAccess.setDatasheetName(sheetName);

		int nTestInstances = runManagerAccess.getLastRowNum();
		List<SeleniumTestParameters> testInstancesToRun = new ArrayList<SeleniumTestParameters>();

		for (int currentTestInstance = 1; currentTestInstance <= nTestInstances; currentTestInstance++) {
			String executeFlag = runManagerAccess.getValue(currentTestInstance,
					"Execute");

			if ("Yes".equalsIgnoreCase(executeFlag)) {
				String currentScenario = runManagerAccess.getValue(currentTestInstance, "TestScenario");
				currentTC.add(runManagerAccess.getValue(currentTestInstance, "TestCase"));
				SeleniumTestParameters testParameters = new SeleniumTestParameters(currentScenario, runManagerAccess.getValue(currentTestInstance, "TestCase"));

				testParameters.setCurrentTestInstance("Instance" + runManagerAccess.getValue(currentTestInstance, "TestInstance"));
				testParameters.setCurrentTestDescription(runManagerAccess.getValue(currentTestInstance, "Description"));
				
				if(!runManagerAccess.getValue(currentTestInstance, "URL").equals(""))
				{
				URL.add(runManagerAccess.getValue(currentTestInstance, "URL"));
				}
				else{
					URL.add("");
				}
				String browser = runManagerAccess.getValue(currentTestInstance,
						"Browser");
				if (!browser.equals(""))
				{
				testParameters.setBrowser(Browser.valueOf(browser));
				}
				//Adding execution mode - LOCAL/PERFECTO
				String executionMode = runManagerAccess.getValue(
						currentTestInstance, "ExecutionMode");
				testParameters.setExecutionMode(ExecutionMode.valueOf(executionMode));
				//Adding MobileExecution Platform - Android/iOS
				String executionPlatform = runManagerAccess.getValue(
						currentTestInstance, "MobileExecutionPlatform");
				if (!executionPlatform.equals(""))
				{
					testParameters.setMobileExecutionPlatform(MobileExecutionPlatform.valueOf(executionPlatform));
				}
				
				//Add Device ID to the test case
				testParameters.setDeviceName(runManagerAccess.getValue(currentTestInstance, "DeviceName"));
				testInstancesToRun.add(testParameters);
			}
		}

		return testInstancesToRun;
	}
}