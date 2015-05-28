/* CNT 4504 COMPUTER NETWORKS AND DISTRIBUTED PROCESSING    
 * Name:   Client.java
 * Demo Date Thursday, June 5  2014 
 * Author: Group 5
 * Client.java is the main driver for the Client, Uses CleintUtils.java and ClientThread.java
 *  and write out  to a file statistics client requests.
 */

import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Exception;
import java.lang.String;
import java.lang.System;
import java.lang.Thread;

public class Client {
    // All threads can access this variable to track how many are still open
    public static volatile int threadsLeft = 0;
    public static volatile String[] logData = new String[1];

    // Entry point of the Client with UI interaction
    public static void main(String args[]) {

        ClientUtils clientUtils = new ClientUtils();
        BufferedReader clientReader = null;
        String inputValue = "";

        // Uncomment here to bypass command line entry of host address (for debugging)
        //args = new String[]{"localhost"};

        try {
            clientReader = new BufferedReader(new InputStreamReader(System.in));

            //Get input values and validate
            if (clientUtils.validateHostName(args)) {

                //Continue until told to quit
                while (!inputValue.equals("7")) {

                    clientUtils.displayMenu();

                    //Get User Input
                    inputValue = clientReader.readLine();

                    //If nothing validated, do nothing
                    if (clientUtils.validateInputValue(inputValue)) {

                        if (clientUtils.needThreadCount) {
                            System.out.print("How many threads? ");
                            inputValue = clientReader.readLine();
                            clientUtils.setThreadCount(inputValue);
                        }
			else {
			    clientUtils.setThreadCount("1");
			}

                        //Gets thread count and validates
                        if ((clientUtils.getThreadCount()) > 0) {

                            //intialize logData
                            logData = new String[clientUtils.getThreadCount()+1];

                            // Create threads
                            Thread[] clientThreads = new Thread[clientUtils.getThreadCount()];

                            // Start timer for all threads (total time)
                            long startTime = System.currentTimeMillis();

                            for (int threadNumber = 0; threadNumber < clientUtils
                                    .getThreadCount(); ++threadNumber) {

                                clientThreads[threadNumber] =
                                        new Thread(new ClientThread(clientUtils
                                        .getHostName(), (5000+threadNumber), clientUtils
                                        .getBatchName(), clientUtils
                                        .getRequest(), threadNumber+1));
                                clientThreads[threadNumber].start();
				//clientThreads[threadNumber].join();
                            }

                            for (int threadNumber = 0; threadNumber < clientUtils
                                    .getThreadCount(); ++threadNumber) {
                                clientThreads[threadNumber].join();
                            }

                            // stop total timer add to log
                            long stopTime = System.currentTimeMillis();
                            logData[clientUtils.getThreadCount()] =
                                    "Total Time:  " + (stopTime - startTime);

                            // write outstats
                            clientUtils.writeStatsLog(clientUtils
                            		.getCommand());

                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally { // Clean up
            if (clientReader != null) {
                try {
                    clientReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
