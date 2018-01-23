package com.cognizant.framework;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Singleton class that encapsulates Framework level global parameters
 * @author Cognizant
 */
public class FrameworkParameters {
	private String runConfiguration;
	private boolean stopExecution = false;
	private String reportpath;
	private String starttime;
	private String endtime;
	private static Date starttimedate;
	private static Date endtimedate;
	
	private static final FrameworkParameters FRAMEWORK_PARAMETERS =
													new FrameworkParameters();
	
	private FrameworkParameters() {
		// To prevent external instantiation of this class
		starttimedate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh_mm_ss aa");
		starttime = sdf.format(starttimedate);
	}
	
	/**
	 * Function to return the singleton instance of the {@link FrameworkParameters} object
	 * @return Instance of the {@link FrameworkParameters} object
	 */
	public static FrameworkParameters getInstance() {
		return FRAMEWORK_PARAMETERS;
	}
	
	/**
	 * Function to get the absolute path of the framework (to be used as a relative path)
	 * @return The absolute path of the framework
	 */
	public String getRelativePath() {
		return new File(System.getProperty("user.dir")).getAbsolutePath();
		
	}
	
	/*
	 * Set the report path in Results Summary
	 */
	public void setReportPath(){
		
		Properties properties = Settings.getInstance();
		String timeStamp =
				"Run_" +
						getCurrentFormattedTime(properties.getProperty("DateFormatString"))
				.replace(" ", "_").replace(":", "-");
		String path = new File(System.getProperty("user.dir")).getAbsolutePath();
		path =  path + "\\"+"Results"+"\\"+runConfiguration+"\\"+timeStamp+"\\HTML";
		
		this.reportpath = path;
		
		new File(reportpath).mkdirs();
		
		
	}
	
	public String getReportPath(){
		
		return reportpath;
	}
	
	public String getCurrentFormattedTime(String dateFormatString) {
		DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		Calendar calendar = Calendar.getInstance();
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * Function to get the run configuration to be executed
	 * @return The run configuration
	 */
	public String getRunConfiguration() {
		return runConfiguration;
	}
	/**
	 * Function to set the run configuration to be executed
	 * @param runConfiguration The run configuration
	 */
	public void setRunConfiguration(String runConfiguration) {
		this.runConfiguration = runConfiguration;
	}
	
	/**
	 * Function to get a boolean value indicating whether to stop the overall test batch execution
	 * @return The stopExecution boolean value
	 */
	public boolean getStopExecution() {
		return stopExecution;
	}
	/**
	 * Function to set a boolean value indicating whether to stop the overall test batch execution
	 * @param stopExecution Boolean value indicating whether to stop the overall test batch execution
	 */
	public void setStopExecution(boolean stopExecution) {
		this.stopExecution = stopExecution;
	}
	
	public String getStartTime(){
		return starttime;
	}
	
	public String getExecutionEndTime(){
		endtimedate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh_mm_ss aa");
		endtime = sdf.format(endtimedate);
		return endtime;
		
	}
	
	public long getelapsedtimeinseconds()
	{
		return (endtimedate.getTime() - starttimedate.getTime())/1000;
	} 
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}