/* CNT 4504 COMPUTER NETWORKS AND DISTRIBUTED PROCESSING
 * Name:  Server.java
 * Due Date:  Thursday, June 5, 2014
 * Author:  Group 5
 *
 * Server.java accepts client connections, waits for a request, runs the requested
 * process, and sends the process's response back to the requesting client.
 */

import java.io.*;
import java.net.*;

public class Server {
    private static final String         // server diagnostic messages
            WAITING = "Waiting for a new connection...",
            ACCEPTING = "Accepting new connection: ",
            RECEIVED_REQUEST = "Received request: ",
            RECEIVED_LINE = "Received one line from shell process: ",
            OUTPUT_SENT = "Output sent to client ",
            END_OF_SESSION = "Session Closed: ",
            PROCESS_DESTROYED = "Process destroyed: ",
            ERROR_IN_REQUEST = "ERROR in client request",
            ERROR_IN_CONNECTION = "ERROR handling client connection",
            SERVER_SOCKET_ERROR = "ERROR establishing server socket";
    private static int connectionNumber = 0;    // Track number of connections

    /**
     * The main method instantiates a ServerSocket, waits for a connection to be accepted,
     * and then passes control of the connection to the handleConnection() method.
     *
     * @param argv no command line arguments are expected
     */
    public static void main(String argv[]) {
        Socket connectionSocket = null;
        ServerSocket serverSocket = null;

        try {
            // Instantiate ServerSocket
            serverSocket = new ServerSocket(6789);

            while(true)
            {
                // Wait for new connection
                System.out.println(WAITING);
                connectionSocket = serverSocket.accept();
                System.out.println(ACCEPTING + ++connectionNumber);

                // Handle new connection
                handleConnection(connectionNumber, connectionSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {                             // Clean up
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connectionSocket != null) {
                try {
                    connectionSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Takes a Socket from main() and waits for it to make a request.  Decides if the request is
     * valid, and either reports ERROR or translates and passes to runProcess().
     *
     * @param thisConnectionNumber current connection number display purposes
     * @param thisConnectionSocket the Socket passed in from main()
     * @throws IOException
     */
    private static void handleConnection(int thisConnectionNumber, Socket thisConnectionSocket) throws IOException {
        String commands[] = {   // Array of executables with most verbose arguments
                "date",
                "uptime",
                "free -l",
                "netstat -v",
                "who -a",
                "ps aux"
        };
        String outputToClient, clientRequest;
        BufferedReader inFromClient;
        DataOutputStream outToClient;
        int clientRequestValue;

        // establish input/output streams
        inFromClient = new BufferedReader(
                new InputStreamReader(thisConnectionSocket.getInputStream()));
        outToClient = new DataOutputStream(thisConnectionSocket.getOutputStream());

        // get command from the client
        clientRequest = inFromClient.readLine();
        System.out.println(RECEIVED_REQUEST + clientRequest);

        try {       // First make sure the request is an integer
            clientRequestValue = Integer.parseInt(clientRequest);
        } catch (NumberFormatException e) {
            clientRequestValue = -1;
        }

        // get output from the host process if request is between (0 and 7)
        if (clientRequestValue > 0 && clientRequestValue < 7) {
            outputToClient = Server.runProcess(commands[clientRequestValue - 1]);

            // return output from process to the client
            outToClient.writeBytes(outputToClient + '\n');
            System.out.println(OUTPUT_SENT);
            System.out.println(END_OF_SESSION + thisConnectionNumber + "\n");
        } else {
            // return ERROR to the client
            outToClient.writeBytes(ERROR_IN_REQUEST + '\n');
            System.out.println(ERROR_IN_REQUEST);
            System.out.println(OUTPUT_SENT);
            System.out.println(END_OF_SESSION + thisConnectionNumber + "\n");
        }
    }

    /**
     * Runs the Linux command passed in and returns a single string with the output
     * it provides via the getInputStream() method.
     *
     * @param cmd Shell command to be executed
     * @return String, return from the command
     */
    private static String runProcess(String cmd) {
        Process process = null;
        BufferedReader stdoutBR = null;
        String stdoutNextLine = null, outputToClient = "";

        try {
            // execute the command
            process = Runtime.getRuntime().exec(cmd);
            // buffer to catch the stdout from the process
            stdoutBR = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // read in each line of the process's stdout
            while ((stdoutNextLine = stdoutBR.readLine()) != null) {
                // append each line to final output for client
                outputToClient += stdoutNextLine + "\n";
                // remove commenting here for local display of process's stdout; adds overhead
                // System.out.println(RECEIVED_LINE + stdoutNextLine);
            }
	    outputToClient += "\0\n";

        } catch (IOException e) {
            e.printStackTrace();
        } finally {                     // clean up
            if (stdoutBR != null)
                try {
                    stdoutBR.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (process != null) {
                process.destroy();
                System.out.println(PROCESS_DESTROYED + cmd);
            }
        }
        return outputToClient;
    }
}

