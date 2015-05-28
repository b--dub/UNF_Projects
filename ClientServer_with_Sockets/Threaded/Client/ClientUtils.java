/* CNT 4504  COMPUTER NETWORKS AND DISTRIBUTED PROCESSING    
 * Name:   ClientUtils.java
 * Demo Date Thursday, June 5  2014
 * Author: Group 5
 * ClientUtils.java uses "business" and utilities methods for the user interface and validation
 * use abstracted methods
 */

import java.io.*;

public class ClientUtils {

	//Declarations and Variables
	private String commandArray[] = new String[0];
	private String hostName = "";
	private String request = "";
	private int threadCount = 1;
	boolean needThreadCount = false;

	public boolean validateInputValue(String inputValue) {
		boolean validEntry = false;
		needThreadCount = false;

		if (inputValue.equals("1") || inputValue.equals("2")
				|| inputValue.equals("3") || inputValue.equals("4")
				|| inputValue.equals("5") || inputValue.equals("6")) {

			request = inputValue;

			if (inputValue.equals("1") || inputValue.equals("4")) {
				needThreadCount = true;
			}

			validEntry = true;

		} else if (!inputValue.equals("7")) {
			System.out
			.println("Please, let's use the number representing the command.");
		}

		return validEntry;
	}

	public Boolean validateHostName(String[] args) {
		boolean validHostName = true;


		if (args.length < 1) {
			System.out
			.println("You must enter the server host name as a command line argument, good bye!");
			validHostName = false;
		} else {
			this.hostName = args[0];
			System.out.println("The server host for this is: "+ hostName+ "\n");
			System.out.println("Welcome to Group 5\'s Project One Client Interface!\n");
		}

		return validHostName;
	}

	// Displays the Menu
	public void displayMenu() {
		if (commandArray.length == 0) {
			commandArray = new String[7];
			commandArray[0] = "Host current Date and Time";
			commandArray[1] = "Host upTime";
			commandArray[2] = "Host Memory use";
			commandArray[3] = "Host NetStat";
			commandArray[4] = "Host current Users";
			commandArray[5] = "Host running processes";
			commandArray[6] = "Quit";
		}

		for (int i = 0; i < commandArray.length; ++i) {
			System.out.println((i + 1) + ". " + commandArray[i]);
		}
	}

	// Get and set methods
	public String getBatchName() {
		return commandArray[Integer.parseInt(request) - 1] + "-" + threadCount; 
	}
	public String getCommand() {
		return commandArray[Integer.parseInt(request) - 1] ; 
	}
	public String getHostName() {
		return hostName;
	}

	public String getRequest() {
		return request;
	}

	public int getThreadCount() {

		return threadCount;
	}

	public void setThreadCount(String inputValue) {

		threadCount = getNumber(inputValue);

		if (threadCount == 0) {
			System.out
			.println("Please try again, nummber of threads must be greater than zero.\n");
		}

	}

	//Prints out a StatusLog
	public void writeStatsLog(String fileName) {
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;

		try {
			//Declarations 
			fileWriter = new FileWriter(fileName + ".csv",true);
			printWriter = new PrintWriter(fileWriter);

			//Print statistics out

			for (int i = 0; i < Client.logData.length; i++) {
				printWriter.println(Client.logData[i]);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally { // Clean up
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (printWriter != null) {
				printWriter.close();
			}
		}
	}

	//private methods  
	private int getNumber(String stringToCheck) {
		int testInt = 0;

		try {
			testInt = Integer.parseInt(stringToCheck);

			if (testInt < 0) {
				throw (new NumberFormatException());
			}

		} catch (NumberFormatException exception) {

		}
		return testInt;
	}

}
