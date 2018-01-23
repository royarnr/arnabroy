package com.cognizant.framework;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class DetailedReport {

	private static volatile String reportPathWithTimeStamp;
	protected int fail = 0;
	protected int counter = 0;
	private String body = "";
	private int errors = 0;

	protected FrameworkParameters frameworkParameters;
	
	public DetailedReport(){
		
	}

	public  String getReportPath() {

		if(reportPathWithTimeStamp == null) {
			FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();

			if(frameworkParameters.getRelativePath() == null) {
				throw new FrameworkException("FrameworkParameters.relativePath is not set!");
			}
			if(frameworkParameters.getRunConfiguration() == null) {
				throw new FrameworkException("FrameworkParameters.runConfiguration is not set!");
			}

			Properties properties = Settings.getInstance();
			String timeStamp =
					"Run_" +
							getCurrentFormattedTime(properties.getProperty("DateFormatString"))
					.replace(" ", "_").replace(":", "-");

			reportPathWithTimeStamp =
					frameworkParameters.getRelativePath() +
					"\\" + "Results" +
					"\\" + frameworkParameters.getRunConfiguration() + 
					"\\" + timeStamp;

			new File(reportPathWithTimeStamp).mkdirs();
		}

		return reportPathWithTimeStamp;
	}

	public String getCurrentFormattedTime(String dateFormatString) {
		DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		Calendar calendar = Calendar.getInstance();
		return dateFormat.format(calendar.getTime());
	}
	
	public void create_HTML (String reportName) {
        
        try {
        	FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh mm ss");
            String formattedDate = sdf.format(date);
            System.out.println(formattedDate);
           // File file = new File(getReportPath());
            File file = new File(frameworkParameters.getReportPath());
            if (!file.exists())
            	file.mkdir();
            OutputStream htmlfile = new FileOutputStream(new File(file + "\\" +reportName + ".html"));
            PrintStream printhtml = new PrintStream(htmlfile);
	
            String temp = "";
	
            String htmlheader="<html><h1>AODA Accessibility Testing Report</h1><head>";
            htmlheader+="<title>Report HTML</title><style type=\"text/css\">#maintable {  width: 800px;  margin: 0 auto; }</style>";
            htmlheader+="<style>body {background-color:lightgrey}</style>";
            htmlheader+="</head><body>";
            String htmlfooter="</table></body></html>";
            
            temp = htmlheader;
            temp += "<table border=\"1\" style=\"width:100%\" id=\"maintable\">";
//            temp += "<tr><th bgcolor = \"#FCF3CF\" style= \"color:red\">Total Number of Errors : </th><th bgcolor = \"#FCF3CF\" style= \"color:red\">"+String.valueOf(fail/2)+"</th></tr>";
            temp += "<tr><th bgcolor = \"#FCF3CF\" style= \"color:red\">Total Number of Errors : </th><th bgcolor = \"#FCF3CF\" style= \"color:red\">"+getErrorcount()+"</th></tr>";
            temp += body;
            temp += htmlfooter;
            printhtml.println(temp);
	
	        printhtml.close();
	        htmlfile.close();
	        File htmlFile = new File(file + "\\" +reportName + ".html");
	       // Desktop.getDesk   top().browse(htmlFile.toURI());
	        
        } catch (Exception pce) {
        	pce.printStackTrace();
        }
	 }

	 public void headings(String text) {
	        
		 body += "<th bgcolor = \"#00E5EE\">" + text + "</th>";
	 }
	 
	 public void PageTitleheadings(String text) {
	        
		 body += "<th bgcolor = \"#8BC9F2\">" + text + "</th>";
	 }
	
	 public void passStatements(String text) {
	        
		 body += "<td bgcolor = \"#58D68D\">" + text + "</td>";
	 }
	
	 public void infoStatements(String text) {
	        
		 body += "<td bgcolor = \"#85C1E9\">" + text + "</td>";
	 }
	 
	 public void failStatements(String text) {
	      
		 body += "<td bgcolor = \"#F1948A\">" + text + "</td>";
		 fail++;
	 }
	 
	 public void warningStatements(String text) {
		 
		 body += "<td bgcolor = \"#F0E68C\">" + text + "</td>";
	        
	 }
	 public void createRow() {
	        
		 body += "<tr>";
	 }
	
	 public void endRow() {
	        
		 body += "</tr>";
	 }
	 
//	 public int getErrorCount()
//	 {
//		 return fail;
//	 }
	 
	 public int getErrorcount()
	 {
		 return errors;
	 }
	 
	 public void setErrorCount(int cnt){
		 
		 errors +=cnt;
	 }




}
