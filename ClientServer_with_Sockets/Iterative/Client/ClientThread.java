/* CNT 4504 COMPUTER NETWORKS AND DISTRIBUTED PROCESSING    
 * Name:   ClientThread.java
 * Due Date Thursday, June 5  2014 
 * Author: Group 5
 * ClientThread.java  inherits the Runnable class.The Runnable class offers the 
 * use abstracted methods
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread implements Runnable {
	//Declaration and variables
	private String hostName, requestType, request;
	private int threadNumber;

	//Constructor
	public ClientThread(String hostName, String requestType, String request,
			int thread) {
		this.hostName = hostName;
		this.requestType = requestType;
		this.request = request;
		this.threadNumber = thread;
	}

	// Run method handles the actual socket connection
	public void run() {
		String responseLine = "", responseBlock = "";
		ProcessStats latencyTimer = new ProcessStats(threadNumber, requestType);
		Socket clientSocket = null;

		try {
			clientSocket = new Socket(hostName, 6789); // modified for local debugging

			// Init Input/output streams
			DataOutputStream outToServer = new DataOutputStream(clientSocket
					.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));

			// Start timer, send request to server, receive response, stop timer
			latencyTimer.start();
			outToServer.writeBytes(request + '\n');

			while (!(responseLine = inFromServer.readLine()).equals("\0")) {
				responseBlock += responseLine + "\n";
			}

			latencyTimer.getDuration(); // stop timer
			Client.logData[threadNumber-1] = latencyTimer.getStat(); // Log duration
			// Display results
			System.out.println(responseBlock);

			//To display latency 
			//System.out.println("FROM SERVER to Thread " + threadNumber + ": "
			//		+ latencyTimer.getStat() + "\n");

		} catch (IOException e) {
			e.printStackTrace();
		} finally { // Clean up
			if (clientSocket != null) {
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
